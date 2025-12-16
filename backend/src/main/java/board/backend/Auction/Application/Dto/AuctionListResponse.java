package board.backend.Auction.Application.Dto;

import board.backend.Auction.Domain.Model.Aggregate.Auction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 경매 목록 응답 DTO
 */
@Getter
@AllArgsConstructor
public class AuctionListResponse {
    private List<AuctionSummary> auctions;
    private int totalCount;

    @Getter
    @AllArgsConstructor
    public static class AuctionSummary {
        private String auctionId;
        private String address;
        private String propertyType;
        private double area;
        private BigDecimal startPrice;
        private BigDecimal currentPrice;
        private String status;
        private LocalDateTime endTime;
        private int bidCount;
        private String thumbnailUrl;
    }

    public static AuctionListResponse from(List<Auction> auctions) {
        List<AuctionSummary> summaries = auctions.stream()
                .map(auction -> new AuctionSummary(
                        auction.getId().getValue(),
                        auction.getProperty().getAddress(),
                        auction.getProperty().getType().name(),
                        auction.getProperty().getArea(),
                        auction.getStartPrice().getAmount(),
                        auction.getCurrentPrice().getAmount(),
                        auction.getAuctionStatus().name(),
                        auction.getEndTime(),
                        auction.getBidCount(),
                        auction.getProperty().getImageUrls().isEmpty()
                                ? null
                                : auction.getProperty().getImageUrls().get(0)
                ))
                .collect(Collectors.toList());

        return new AuctionListResponse(summaries, summaries.size());
    }
}
