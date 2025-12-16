package board.backend.Auction.Application.Dto;

import board.backend.Auction.Domain.Model.Enum.PropertyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 경매 생성 요청 DTO
 */
@Getter
@NoArgsConstructor
public class CreateAuctionRequest {
    private String address;
    private PropertyType propertyType;
    private double area;
    private String description;
    private List<String> imageUrls;
    private BigDecimal startPrice;
    private LocalDateTime endTime;
}
