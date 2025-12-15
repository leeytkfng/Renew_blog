package board.backend.Auction.Infrastructure.Entity.Embedded;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BidEmbedded {

    @Field("id")
    private String id;

    @Field("user_id")
    private String userId;

    @Field("amount")
    private BigDecimal amount;

    @Field("bid_time")
    private LocalDateTime bidTime;
}
