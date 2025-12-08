package board.backend.Auth.Domain.Model.Enum;

public enum UserStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    LOCKED("잠금"),
    DELETED("삭제");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }
}
