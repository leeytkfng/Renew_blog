package board.backend.Auction.Domain.Model.Value;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * 입찰 식별자
 */
@Getter
@EqualsAndHashCode
public class BidId {
    private final String value;

    private BidId(String  value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("BIDID 는 Null 이면 안됩니다.");
        }

        this.value = value;
    }

    public static BidId generate() {
        return new BidId(UUID.randomUUID().toString());
    }

    public static BidId from(String value) {
        return new BidId(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
