package board.backend.Auction.Domain.Model.Aggregate;

import board.backend.Auction.Domain.Model.Entity.Bid;
import board.backend.Auction.Domain.Model.Enum.AuctionStatus;
import board.backend.Auction.Domain.Model.Value.AuctionId;
import board.backend.Auction.Domain.Model.Value.Money;
import board.backend.Auction.Domain.Model.Value.Property;
import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 경매 Aggregate root
 */
@Getter
@Slf4j
public class Auction {
    private final AuctionId id;
    private final Property property;
    private final Money startPrice;
    private Money currentPrice;
    private final UserId sellerId;
    private AuctionStatus auctionStatus;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<Bid> bids;
    private final LocalDateTime createdAt;

    //private 생성자
    private Auction(AuctionId id, Property property,Money startPrice, Money currentPrice,
                    UserId sellerId, AuctionStatus status, LocalDateTime startTime,
                    LocalDateTime endTime, List<Bid> bids, LocalDateTime createdAt) {
        this.id = id;
        this.property = property;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.sellerId = sellerId;
        this.auctionStatus = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bids = bids;
        this.createdAt = createdAt;
    }

    //팩토리 메서드 : 새 경매 생성
    public static Auction create(Property property, Money startPrice,
                                 UserId sellerId, LocalDateTime endTime) {



        LocalDateTime now = LocalDateTime.now();
        AuctionStatus initialStatus = determineInitialStatus(now,now);

        Auction auction = new Auction(
                AuctionId.generate(),
                property,
                startPrice,
                startPrice, // 초기 현재가 == 시작가로 동일
                sellerId,
                initialStatus,
                now,
                endTime,
                new ArrayList<>(),
                now
        );
        log.info(">>> [Auction] 경매 생성: id={}, startPrice={}, endTime={}",
                auction.id.getValue(), startPrice.getAmount(), endTime);

        return auction;
    }


    //팩토리 메서드 -> DB에 저장된 데이터를 도메인 객체로 복원한다.
    public static Auction reconstitute(AuctionId id, Property property, Money startPrice,
                                       Money currentPrice, UserId sellerId, AuctionStatus status,
                                       LocalDateTime startTime, LocalDateTime endTime , List<Bid> bids, LocalDateTime createdAt) {
       return new Auction(id, property, startPrice, currentPrice, sellerId,
               status,startTime,endTime,new ArrayList<>(bids), createdAt);
    }

    //  from() 메서드 추가 (reconstitute의 별칭) -> 일반적인 변환 패턴
    // 주어진 파라미터로부터 객체를 만든다.
    public static Auction from(AuctionId id, Property property, Money startPrice,
                               Money currentPrice, UserId sellerId, AuctionStatus status,
                               LocalDateTime startTime, LocalDateTime endTime,
                               List<Bid> bids, LocalDateTime createdAt) {
        return reconstitute(id, property, startPrice, currentPrice, sellerId,
                status, startTime, endTime, bids, createdAt);
    }

    //비즈니스 로직 :입찰하기
    public void placeBid(UserId userId, Money bidAmount) {
        log.info(">>> [Auction] 입찰 시도 : auctionId= {} ,userId= {} ,amount = {}" ,
                this.id.getValue(), userId.getValue(), bidAmount.getAmount());

        //1. 경매 상태 확인
        validateAuctionActive();

        //2. 종료 시작 확인
        validateNotExpired();

        //3. 판매자 본인 입찰 방지
        validateSellerId(userId);

        //4. 최소 입찰가 확인
        Money minimumBid = calculateMinimumBid();
        validateBidAmount(bidAmount,minimumBid);

        //5. 연속 입찰 방지
        validateNotConsecutiveBid(userId);

        //6. 입찰 추가
        Bid bid = Bid.create(this.id, userId, bidAmount);
        this.bids.add(bid);
        this.currentPrice = bidAmount;

        log.info(">>> [Auction] 입찰 성공: auctionId={}, newPrice={}, totalBids={}",
                this.id.getValue(), bidAmount.getAmount(), this.bids.size());
    }

    //비즈니스 로직 : 경매 종료
    public void end() {
        if (this.auctionStatus != AuctionStatus.ACTIVE) {
            throw new IllegalArgumentException("진행 중인 경매만 종료할 수 있습니다");
        }

        this.auctionStatus = AuctionStatus.ENDED;
        log.info(">>> [Auction] 경매 종료: id={}, finalPrice={}, totalBids={}",
                this.id.getValue(), this.currentPrice.getAmount(), this.bids.size());
    }

    //비즈니스 로직 : 경매 취소
    public void cancel(UserId requesterId) {

        //1. 판매자 확인
        if (!this.sellerId.equals(requesterId)){
            throw new IllegalArgumentException("경매를 취소할 권한이 없습니다.");
        }

        //2. 취소가능 상태 확인
        if (!this.auctionStatus.canCancel()) {
            throw new IllegalArgumentException("경매를 취소할 수없는 상태입니다.");
        }
        //3. 입찰이 있으면 취소 불가
        if (!this.bids.isEmpty()) {
            throw new IllegalArgumentException("입찰이 있는 경매는 취소할 수없습니다.");
        }

        this.auctionStatus = AuctionStatus.CANCELLED;
        log.info(">>> [Auction] 경매 취소: id={}", this.id.getValue());
    }

    // 비즈니스 로직: 경매 시작 (스케줄러용)
    public void start() {
        if (this.auctionStatus != AuctionStatus.SCHEDULED) {
            throw new IllegalStateException("예정된 경매만 시작할 수 있습니다.");
        }

        this.auctionStatus = AuctionStatus.ACTIVE;
        log.info(">>> [Auction] 경매 시작: id={}", this.id.getValue());
    }

    // 조회 메서드
    public List<Bid> getBids() {
        return Collections.unmodifiableList(bids);
    }

    public UserId getWinningBidder() {
        if (this.auctionStatus != AuctionStatus.ENDED || this.bids.isEmpty()) {
            return null;
        }
        return this.bids.get(this.bids.size() - 1).getUserId();
    }

    public boolean hasWinner() {
        return this.auctionStatus == AuctionStatus.ENDED && !this.bids.isEmpty();
    }

    public int getBidCount() {
        return this.bids.size();
    }

    public boolean isActive() {
        return this.auctionStatus == AuctionStatus.ACTIVE;
    }

    //validation 메서드들
    private void validateAuctionActive() {
        if (!this.auctionStatus.canBid()) {
            throw new IllegalArgumentException(
                    String.format("경매가 진행중입니다. 현제 상태: %s",this.auctionStatus.getDescription())
            );
        }
    }

    private void validateNotExpired() {
        if (LocalDateTime.now().isAfter(this.endTime)) {
            throw new IllegalArgumentException("경매가 종료되었습니다.");
        }
    }

    private void validateNotSeller(UserId id) {
        if (this.sellerId.equals(id)) {
            throw new IllegalArgumentException("판매자는 자신의 경매 입찰할 수없습니다.");
        }
    }
    private void validateBidAmount(Money bidAmount, Money minumumBid) {
        if (bidAmount.isLessThan(minumumBid)){
            throw new IllegalArgumentException(
                    String.format("입찰 금액은 최소 %s 원 이상이어야 합니다.", minumumBid.getAmount())
            );
        }
    }

    private void validateNotConsecutiveBid(UserId userId) {
        if (!this.bids.isEmpty()) {
            Bid lastBid = this.bids.get(this.bids.size()-1);
            if (lastBid.getUserId().equals(userId)){
                throw new IllegalArgumentException("연속으로 입찰 할 수 없습니다.");
            }
        }
    }

    private Money calculateMinimumBid() {
        //현재가 + 10만원
        return this.currentPrice.add(Money.of(100000));
    }

    //Static Validation
    private static void validateProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("부동산 정보는 필수입니다.");
        }
    }

    private static void validateStartPrice(Money startPrice) {
        if (startPrice == null) {
            throw new IllegalArgumentException("시작가는 필수입니다.");
        }
        if (startPrice.isLessThanOrEqual(Money.zero())){
            throw new IllegalArgumentException("시작가는 0보다 커야합니다.");
        }
    }

    private static void validateSellerId(UserId sellerId) {
        if (sellerId == null) {
            throw new IllegalArgumentException("판매자 Id는 필수입니다.");
        }
    }

    private static void validateEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("종료 시간은 필수입니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        if (endTime.isBefore(now)) {
            throw new IllegalArgumentException("종료 시간은 현재 시간 이후여야 합니다.");
        }
        if (endTime.isBefore(now.plusHours(1))) {
            throw new IllegalArgumentException("경매는 최소 1시간 이상 진행되어야 합니다.");
        }
    }

    private static AuctionStatus determineInitialStatus(LocalDateTime startTime, LocalDateTime now) {
        if (startTime.isAfter(now)) {
            return AuctionStatus.SCHEDULED;
        }
        return AuctionStatus.ACTIVE;
    }

    @Override
    public String toString() {
        return String.format("Auction[id=%s, status=%s, currentPrice=%s, bids=%d]",
                id.getValue(), auctionStatus, currentPrice.getAmount(), bids.size());
    }
}
