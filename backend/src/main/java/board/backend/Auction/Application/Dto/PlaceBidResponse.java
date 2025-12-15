package board.backend.Auction.Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PlaceBidResponse {
    private String auctionId;
    private BigDecimal currentPrice;
    private String message;
}
