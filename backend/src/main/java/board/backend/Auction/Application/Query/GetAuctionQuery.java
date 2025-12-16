package board.backend.Auction.Application.Query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 경매 상세 조회 Query
 */
@Getter
@RequiredArgsConstructor
public class GetAuctionQuery {
    private final String auctionId;
}
