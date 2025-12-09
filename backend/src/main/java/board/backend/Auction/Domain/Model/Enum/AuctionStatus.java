package board.backend.Auction.Domain.Model.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuctionStatus {
    SCHEDULED("예정"),    // 시작 전
    ACTIVE("진행중"),     // 진행 중
    ENDED("종료"),        // 종료됨
    CANCELLED("취소");    // 취소됨

    private final String description;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean canBid() {
        return this == ACTIVE;
    }

    public boolean canCancel() {
        return this == SCHEDULED || this == ACTIVE;
    }
}
