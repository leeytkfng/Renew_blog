package board.backend.Auction.Application.Command;

import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Value.AuctionId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * 입찰 커맨드
 */
@Getter
@RequiredArgsConstructor
public class PlaceBidCommand {
    private final String auctionId;
    private final BigDecimal bidAmount;
}
