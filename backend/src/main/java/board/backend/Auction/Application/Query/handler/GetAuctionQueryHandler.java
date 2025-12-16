package board.backend.Auction.Application.Query.handler;


import board.backend.Auction.Application.Dto.AuctionDetailResponse;
import board.backend.Auction.Application.Query.GetAuctionQuery;
import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Value.AuctionId;
import board.backend.Auction.Domain.Repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 경매 상세 조회 Query Handler
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAuctionQueryHandler {

    private final AuctionRepository auctionRepository;


    @Transactional(readOnly = true)
    public AuctionDetailResponse handle(GetAuctionQuery query) {
        log.info(">>> [Handler] GetAuctionQuery 처리: auctionId={}", query.getAuctionId());

        Auction auction = auctionRepository.findById(AuctionId.from(query.getAuctionId()))
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을수 없습니다."));

        log.debug(">>> [Handler] 경매 조회 완료: status={}, bidCount={}",
                auction.getAuctionStatus(), auction.getBidCount());


        return AuctionDetailResponse.from(auction);
    }
}
