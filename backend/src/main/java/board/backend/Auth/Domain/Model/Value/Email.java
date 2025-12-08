package board.backend.Auth.Domain.Model.Value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Value Object -> 식별자는 없고 값으로만 구분되는 객체
 * 불변이라서 setter로 값 수정하는 부분이없다.
 */
@Getter
@EqualsAndHashCode
public class Email {
    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }


    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (!value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
