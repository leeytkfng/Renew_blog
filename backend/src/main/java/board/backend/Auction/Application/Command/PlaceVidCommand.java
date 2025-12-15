package board.backend.Auction.Application.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * 입찰 커맨드
 */
@Getter
@RequiredArgsConstructor
public class PlaceVidCommand {
    private final String auctionId;
    private final BigDecimal bigDecimal;
}
