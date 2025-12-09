package board.backend.Auction.Domain.Model.Value;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;


/**
 * 입찰 금액 (비즈니스 로직 포함)
 */
@Getter
@EqualsAndHashCode
public class Money {
    private final BigDecimal amount;


    private Money(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("금액은 null일수가 없습니다.");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("금액이 비정상적으로 책정되어 있습니다.(음수)");
        }
        this.amount = amount;
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of (long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    // 덧셈
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    // 뺄셈
    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    // 비교 연산
    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }

    public boolean isLessThanOrEqual(Money other) {
        return this.amount.compareTo(other.amount) <= 0;
    }

    @Override
    public String toString() {
        return amount.toString();
    }
 }
