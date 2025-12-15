package board.backend.Auction.Infrastructure.Mapper;

import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Entity.Bid;
import board.backend.Auction.Domain.Model.Enum.AuctionStatus;
import board.backend.Auction.Domain.Model.Enum.PropertyType;
import board.backend.Auction.Domain.Model.Value.*;
import board.backend.Auction.Infrastructure.Entity.AuctionMongoEntity;
import board.backend.Auction.Infrastructure.Entity.Embedded.BidEmbedded;
import board.backend.Auction.Infrastructure.Entity.Embedded.PropertyEmbedded;
import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Auction Domain ↔ MongoDB Entity Mapper
 */
@Component
@Slf4j
public class AuctionMapper {

    // ========== Domain → MongoDB ==========

    /**
     * Domain Auction → MongoDB Entity
     */
    public AuctionMongoEntity toMongoEntity(Auction auction) {
        log.debug(">>> [Mapper] toMongoEntity: auctionId={}", auction.getId().getValue());

        PropertyEmbedded propertyEmbedded = toPropertyEmbedded(auction.getProperty());
        List<BidEmbedded> bidEmbeddedList = toBidEmbeddedList(auction.getBids());

        return new AuctionMongoEntity(
                auction.getId().getValue(),
                propertyEmbedded,
                auction.getStartPrice().getAmount(),
                auction.getCurrentPrice().getAmount(),
                auction.getSellerId().getValue(),
                auction.getAuctionStatus().name(),
                auction.getStartTime(),
                auction.getEndTime(),
                bidEmbeddedList,
                auction.getCreatedAt()
        );
    }

    /**
     * Domain Property → PropertyEmbedded
     */
    private PropertyEmbedded toPropertyEmbedded(Property property) {
        return new PropertyEmbedded(
                property.getAddress(),
                property.getType().name(),
                property.getArea(),
                property.getDescription(),
                new ArrayList<>(property.getImageUrls())
        );
    }

    /**
     * Domain Bid List → BidEmbedded List
     */
    private List<BidEmbedded> toBidEmbeddedList(List<Bid> bids) {
        return bids.stream()
                .map(this::toBidEmbedded)
                .collect(Collectors.toList());
    }

    /**
     * Domain Bid → BidEmbedded
     */
    private BidEmbedded toBidEmbedded(Bid bid) {
        return new BidEmbedded(
                bid.getId().getValue(),
                bid.getUserId().getValue(),
                bid.getMoney().getAmount(),
                bid.getBidTime()
        );
    }

    // ========== MongoDB → Domain ==========

    /**
     * MongoDB Entity → Domain Auction
     */
    public Auction toDomain(AuctionMongoEntity entity) {
        log.debug(">>> [Mapper] toDomain: auctionId={}", entity.getId());

        Property property = toProperty(entity.getProperty());
        List<Bid> bids = toBidList(entity.getId(), entity.getBids());

        return Auction.reconstitute(
                AuctionId.from(entity.getId()),
                property,
                Money.of(entity.getStartPrice()),
                Money.of(entity.getCurrentPrice()),
                UserId.of(entity.getSellerId()),
                AuctionStatus.valueOf(entity.getStatus()),
                entity.getStartTime(),
                entity.getEndTime(),
                bids,
                entity.getCreatedAt()
        );
    }

    /**
     * PropertyEmbedded → Domain Property
     */
    private Property toProperty(PropertyEmbedded embedded) {
        return Property.of(
                embedded.getAddress(),
                PropertyType.valueOf(embedded.getType()),
                embedded.getArea(),
                embedded.getDescription(),
                embedded.getImageUrls()
        );
    }

    /**
     * BidEmbedded List → Domain Bid List
     */
    private List<Bid> toBidList(String auctionId, List<BidEmbedded> embeddedList) {
        return embeddedList.stream()
                .map(embedded -> toBid(auctionId, embedded))
                .collect(Collectors.toList());
    }

    /**
     * BidEmbedded → Domain Bid
     */
    private Bid toBid(String auctionId, BidEmbedded embedded) {
        return Bid.reconstitute(
                BidId.from(embedded.getId()),
                AuctionId.from(auctionId),
                UserId.of(embedded.getUserId()),
                Money.of(embedded.getAmount()),
                embedded.getBidTime()
        );
    }
}