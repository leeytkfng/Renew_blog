package board.backend.Auction.Domain.Model.Value;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * 경매 식별자
 */
@Getter
@EqualsAndHashCode
public class AuctionId {
    private final String value;

    public AuctionId(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("경매 물품이 존재하지 않습니다.");
        }
        this.value = value;
    }

    public static AuctionId from(String value) {
        return new AuctionId(value);
    }

    public static AuctionId generate() {
        return new AuctionId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }
}
