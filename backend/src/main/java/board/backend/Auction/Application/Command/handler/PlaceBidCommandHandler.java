package board.backend.Auction.Application.Command.handler;

import board.backend.Auction.Application.Command.PlaceBidCommand;
import board.backend.Auction.Application.Dto.PlaceBidResponse;
import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Value.AuctionId;
import board.backend.Auction.Domain.Model.Value.Money;
import board.backend.Auction.Domain.Repository.AuctionRepository;
import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 입찰 Command Handler
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceBidCommandHandler {

    private final AuctionRepository auctionRepository;

    @Transactional
    public PlaceBidResponse handle(PlaceBidCommand command, UserId userId) {
        log.info(">>> [Handler] PlaceBidCommand 처리 시작: auctionId={}, userId={}, amount={}",
                command.getAuctionId(), userId.getValue(), command.getBidAmount());

        // 1. 경매 조회
        Auction auction = auctionRepository.findById(AuctionId.from(command.getAuctionId()))
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을수 없습니다."));

        log.debug(">>> [Handler] 경매 찾음: status={}, currentPrice={}",
                auction.getAuctionStatus(), auction.getCurrentPrice().getAmount());

        // 2. 입찰 (도메인 로직 실행)
        Money bidAmount = Money.of(command.getBidAmount());
        auction.placeBid(userId,bidAmount);

        Auction savedAuction = auctionRepository.save(auction);

        log.info(">>> [Handler] 입찰 완료: auctionId={}, newPrice={}, totalBids={}",
                savedAuction.getId().getValue(),
                savedAuction.getCurrentPrice().getAmount(),
                savedAuction.getBidCount());

        return new PlaceBidResponse(
                savedAuction.getId().getValue(),
                savedAuction.getCurrentPrice().getAmount(),
                "입찰이 완료되었습니다."
        );
    }

}
