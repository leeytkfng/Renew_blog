package board.backend.Auction.Application.Command.handler;

import board.backend.Auction.Application.Command.CreateAuctionCommand;
import board.backend.Auction.Application.Dto.CreateAuctionResponse;
import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Value.Money;
import board.backend.Auction.Domain.Model.Value.Property;
import board.backend.Auction.Domain.Repository.AuctionRepository;
import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 경매 생성 Command Hanlder
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAuctionCommandHandler {
    private final AuctionRepository auctionRepository;

    @Transactional
    public CreateAuctionResponse handle(CreateAuctionCommand command, UserId sellerId) {
        log.info(">>> [Handler] CreateAuctionCommand 처리 시작: sellerId={}", sellerId.getValue());

        //1. Property 생성
        Property property = Property.of(
                command.getAddress(),
                command.getPropertyType(),
                command.getArea(),
                command.getDescription(),
                command.getImageUrls()
        );

        // 2. Money 생성
        Money startPrice  = Money.of(command.getStartPrice());

        // 3. Auction Aggregate 생성
        Auction auction = Auction.create(
                property,
                startPrice,
                sellerId,
                command.getEndTime()
        );

        // 4. 저장
        Auction savedAuction = auctionRepository.save(auction);

        log.info(">>> [Handler] 경매 생성 완료: auctionId={}, startPrice={}",
                savedAuction.getId().getValue(), startPrice.getAmount());

        return new CreateAuctionResponse(
                savedAuction.getId().getValue(),
                "경매가 성공적으로 등록되었습니다."
        );
    }
}
