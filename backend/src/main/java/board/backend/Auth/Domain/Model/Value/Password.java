package board.backend.Auth.Domain.Model.Value;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
public class Password {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    public void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %자 이상 %자 이하이어야합니다." , MIN_LENGTH , MAX_LENGTH)
            );
        }
        //추가 검즘: 영문, 숫자, 특수문자 포함
        // 추가 검증: 영문, 숫자, 특수문자 포함
        if (!value.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$")) {
            throw new IllegalArgumentException(
                    "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."
            );
        }

    }


    @Override
    public String toString() {
        return "*******";
    }


    public boolean matches (Password other) {
        return this.value.equals(other.value);
    }
}
