package board.backend.Auction.Application.Command;

import board.backend.Auction.Domain.Model.Enum.PropertyType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CreateAuctionCommand {
    private final String address;
    private final PropertyType propertyType;
    private final double area;
    private final String description;
    private final List<String> imageUrls;
    private final BigDecimal startPrice;
    private final LocalDateTime endTime;
}
