package board.backend.Auction.Infrastructure.Entity;

import board.backend.Auction.Infrastructure.Entity.Embedded.BidEmbedded;
import board.backend.Auction.Infrastructure.Entity.Embedded.PropertyEmbedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Auction MongoDb Document
 * auctions 컬렉션
 */
@Document("auctions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionMongoEntity {
    @Id
    private String id;

    @Field("property")
    private PropertyEmbedded property;

    @Field("start_price")
    private BigDecimal startPrice;

    @Field("current_price")
    private BigDecimal currentPrice;

    @Field("seller_id")
    @Indexed
    private String sellerId;

    @Field("status")
    @Indexed
    private String status;

    @Field("start_time")
    private LocalDateTime startTime;

    @Field("end_time")
    @Indexed
    private LocalDateTime endTime;

    @Field("bids")
    private List<BidEmbedded> bids = new ArrayList<>();

    @Field("created_at")
    private LocalDateTime createdAt;

    // 명시적 생성자
    public AuctionMongoEntity(String id, PropertyEmbedded property,
                              BigDecimal startPrice, BigDecimal currentPrice,
                              String sellerId, String status,
                              LocalDateTime startTime, LocalDateTime endTime,
                              List<BidEmbedded> bids, LocalDateTime createdAt) {
        this.id = id;
        this.property = property;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.sellerId = sellerId;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bids = bids != null ? new ArrayList<>(bids) : new ArrayList<>();
        this.createdAt = createdAt;
    }
}
