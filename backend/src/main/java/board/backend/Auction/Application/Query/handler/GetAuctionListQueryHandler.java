package board.backend.Auction.Application.Query.handler;

import board.backend.Auction.Application.Dto.AuctionListResponse;
import board.backend.Auction.Application.Query.GetAuctionListQuery;
import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Enum.AuctionStatus;
import board.backend.Auction.Domain.Repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAuctionListQueryHandler {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public AuctionListResponse handle(GetAuctionListQuery query) {
        log.info(">>> [Handler] GetAuctionListQuery 처리: status={}", query.getStatus());

        List<Auction> auctions;

        if (query.getStatus() != null) {
            auctions = auctionRepository.findByStatus(AuctionStatus.valueOf(query.getStatus()));
        } else {
            auctions = auctionRepository.findAll();
        }

        log.info(">>> [Handler] 경매 목록 조회 완료: count={}", auctions.size());

        return AuctionListResponse.from(auctions);
    }
}
