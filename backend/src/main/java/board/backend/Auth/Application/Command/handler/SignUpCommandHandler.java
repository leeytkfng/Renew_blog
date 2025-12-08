package board.backend.Auth.Application.Command.handler;

import board.backend.Auth.Application.Command.SignUpCommand;
import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Value.Email;
import board.backend.Auth.Domain.Model.Value.Password;
import board.backend.Auth.Domain.Model.Value.UserId;
import board.backend.Auth.Domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpCommandHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void handler(SignUpCommand command) {
        log.info("SignUpCommand 처리 시작: {}" , command.getEmail());

        //1. Value Object 생성
        Email email = new Email(command.getEmail());

        //2. 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw  new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        //3.비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(command.getPassword());

        //4. User 엔티티 생성
        User user = User.register(
                email,
                encryptedPassword,
                command.getName(),
                command.getPhone(),
                command.getAddress()

        );

        //5. 저장
        userRepository.save(user);

        log.info("SignUpCommand 처리 완료 : {}", command.getEmail());
    }
}
