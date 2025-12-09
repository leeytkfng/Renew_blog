package board.backend.Auction.Domain.Model.Entity;

import board.backend.Auction.Domain.Model.Value.AuctionId;
import board.backend.Auction.Domain.Model.Value.BidId;
import board.backend.Auction.Domain.Model.Value.Money;
import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Slf4j
public class Bid {

    private final BidId id;
    private final AuctionId auctionId;
    private final UserId userId;
    private final Money money;
    private final LocalDateTime bidTime;

    //private 생성자
    private Bid(BidId id, AuctionId auctionId , UserId userId,
                Money money, LocalDateTime bidTime) {
        this.id = id;
        this.auctionId = auctionId;
        this.userId = userId;
        this.money = money;
        this.bidTime = bidTime;
    }
    // 새 입찰 생성
    public static Bid create(AuctionId auctionId, UserId userId, Money money) {


        Bid bid = new Bid(
                BidId.generate(),
                auctionId,
                userId,
                money,
                LocalDateTime.now()
        );

        log.debug(">>> [Bid] 입찰 생성: bidId={}, auctionId={}, userId={}, amount={}",
                bid.id.getValue(), auctionId.getValue(), userId.getValue(), money.getAmount());

        return bid;
    }

    public static Bid reconstitute(BidId id, AuctionId auctionId, UserId userId ,
                                   Money amount , LocalDateTime bidTime) {
        return new Bid(id, auctionId, userId, amount, bidTime);
    }

    private static void validateAmount(Money money) {
        if (money ==null) {
            throw new IllegalArgumentException("입찰 금액은 필수 입니다.");
        }


        if (money.isLessThanOrEqual(Money.zero())) {
            throw new IllegalArgumentException("입찰 금액은 0보다 커야합니다.");
        }
    }

    @Override
    public String toString() {
        return String.format("Bid[id=%s, amount=%s, userId=%s, time=%s]",
                id.getValue(), money.getAmount(), userId.getValue(), bidTime);
    }
}

