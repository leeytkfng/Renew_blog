package board.backend.Auction.Application.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 입찰 요청 DTO
 */
@Getter
@NoArgsConstructor
public class PlaceBIdRequest {
    private BigDecimal bidAmount;
}
