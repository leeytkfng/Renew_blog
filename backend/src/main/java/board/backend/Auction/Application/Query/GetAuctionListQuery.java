package board.backend.Auction.Application.Query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetAuctionListQuery {
    private final String status; // nullable
}
