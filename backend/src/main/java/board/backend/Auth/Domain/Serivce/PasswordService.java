package board.backend.Auth.Domain.Serivce;

import board.backend.Auth.Domain.Exception.InvalidPasswordException;
import board.backend.Auth.Domain.Model.Value.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
   private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

   //비밀번호 암호화
    public String encrypt(Password password) {
        return encoder.encode(password.getValue());
    }


    // 비밀번호 검증
    public void validate(Password rawPassword, String encryptedPassword) {
        if (!encoder.matches(rawPassword.getValue(), encryptedPassword)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }

    public boolean matches(Password rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword.getValue(), encryptedPassword);
    }
}
