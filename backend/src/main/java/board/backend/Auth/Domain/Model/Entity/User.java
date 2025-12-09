package board.backend.Auth.Domain.Model.Entity;

import board.backend.Auth.Domain.Exception.UserNotActiveException;
import board.backend.Auth.Domain.Model.Vo.Email;
import board.backend.Auth.Domain.Model.Vo.Password;
import board.backend.Auth.Domain.Model.Vo.UserId;
import board.backend.Auth.Domain.Model.Enum.UserStatus;
import lombok.Getter;

import java.time.LocalDateTime;


/**
 * Enitty = 식벽자를 가진 객체
 * 특징 1. 식별자 (ID) 로 구분된다.
 * 특징 2. 생명주지가 있다( 생성 -> 수정 삭제 )
 * 특징 3. 가변(mutalble)이 가능하다.
 * 특징 4. 동일성 (Identity) 비교 -> equals
 */
@Getter
public class User {
    private final UserId id;  //고유 식별자 - entity에 필수!
    private Email email;
    private String encryptedPassword; // 암호화된 비밀번호
    private String name;
    private String phone;
    private String address;
    private UserStatus status;
    private String platform;
    private String ipAddress;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    //생성자 - 파라미터 순서 명확하게
    private User(
            UserId id,
            Email email,
            String encryptedPassword,
            String name,
            String phone,
            String address,
            UserStatus status,
            LocalDateTime createdAt,
            LocalDateTime lastLoginAt
    ) {
        this.id = id;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    // 팩토리 메서드 - 회원가입
    public static User register(
            Email email,
            String encryptedPassword,
            String name,
            String phone,
            String address
    ) {
        validateName(name);

        return new User(
                UserId.generate(),      // id
                email,                  // email
                encryptedPassword,      // encryptedPassword
                name,                   // name
                phone,                  // phone
                address,                // address
                UserStatus.ACTIVE,      // status
                LocalDateTime.now(),    // createdAt
                null                    // lastLoginAt
        );
    }

    public void validateLogin(Password inputPassword) {
        if (this.status != UserStatus.ACTIVE) {
            throw new IllegalArgumentException("비활성화된 계정입니다. 관리자에게 문의하세요.");
        }
    }

    // 팩토리 메서드 - DB에서 재구성
    public static User reconstitute(
            UserId id,
            Email email,
            String encryptedPassword,
            String name,
            String phone,
            String address,
            UserStatus status,
            LocalDateTime createdAt,
            LocalDateTime lastLoginAt
    ) {
        return new User(
                id,
                email,
                encryptedPassword,
                name,
                phone,
                address,
                status,
                createdAt,
                lastLoginAt
        );
    }


    //비즈니스 로직 - 로그인 시드
    public void validateLoginAttempt() {
        if (!this.status.isActive()) {
            throw new UserNotActiveException("사용자 계정이 활성화되지 않았습니다. 상태:" + this.status.getDescription());
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (name.length() < 2 || name.length() > 20) {
            throw new IllegalArgumentException("이름은 2자 이상으로 해야합니다.");
        }
    }

    //비즈니스 로직
    public void recordSuccessfulLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    // 비즈니스 로직 - 이메일 변경
    public void changeEmail(Email newEmail) {
        this.email = newEmail;
    }

    // 비즈니스 로직 - 계정 비활성화
    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    // 비즈니스 로직 - 계정 잠금
    public void lock() {
        this.status = UserStatus.LOCKED;
    }

    // 비즈니스 로직 - 회원 탈퇴
    public void withdraw() {
        this.status = UserStatus.DELETED;
    }

    public void recordLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void changePassword(String newEncryptedPassword) {
        if (newEncryptedPassword == null || newEncryptedPassword.isBlank()) {
            throw new IllegalArgumentException("암호화된 비밀번호는 필수입니다.");
        }
        this.encryptedPassword = newEncryptedPassword;
    }

}
