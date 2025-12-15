package board.backend.Auction.Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAuctionResponse {
    private String auctionId;
    private String message;
}
