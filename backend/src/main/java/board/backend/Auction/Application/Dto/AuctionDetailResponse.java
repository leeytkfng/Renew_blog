package board.backend.Auction.Application.Dto;

import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Enum.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AuctionDetailResponse {
    private String auctionId;
    private PropertyInfo propertyInfo;
    private BigDecimal startPrice;
    private BigDecimal currentPrice;
    private String sellerId;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<BidInfo> bids;
    private int bidCount;
    private LocalDateTime createdAt;



    @Getter
    @AllArgsConstructor
    public static class PropertyInfo {
        private String address;
        private PropertyType type;
        private double area;
        private String description;
        private List<String> imageUrls;
    }

    @Getter
    @AllArgsConstructor
    public static class BidInfo {
        private String bidId;
        private String userId;
        private BigDecimal amount;
        private LocalDateTime bidTime;
    }

    public static AuctionDetailResponse from(Auction auction) {
        PropertyInfo propertyInfo = new PropertyInfo(
                auction.getProperty().getAddress(),
                auction.getProperty().getType(),
                auction.getProperty().getArea(),
                auction.getProperty().getDescription(),
                auction.getProperty().getImageUrls()
        );

        List<BidInfo> bidInfos = auction.getBids().stream()
                .map(bid -> new BidInfo(
                        bid.getId().getValue(),
                        bid.getUserId().getValue(),
                        bid.getMoney().getAmount(),
                        bid.getBidTime()
                ))
                .toList();

        return new AuctionDetailResponse(
                auction.getId().getValue(),
                propertyInfo,
                auction.getStartPrice().getAmount(),
                auction.getCurrentPrice().getAmount(),
                auction.getSellerId().getValue(),
                auction.getAuctionStatus().name(),
                auction.getStartTime(),
                auction.getEndTime(),
                bidInfos,
                auction.getBidCount(),
                auction.getCreatedAt()
        );
    }
}
