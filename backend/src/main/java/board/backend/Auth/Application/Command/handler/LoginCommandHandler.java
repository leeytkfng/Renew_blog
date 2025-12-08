package board.backend.Auth.Application.Command.handler;

import board.backend.Auth.Application.Command.LoginCommand;
import board.backend.Auth.Application.Dto.Login.LoginResponse;
import board.backend.Auth.Application.Dto.Token.TokenResponse;
import board.backend.Auth.Domain.Event.UserLoginEvent;
import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Value.Email;
import board.backend.Auth.Domain.Model.Value.Password;
import board.backend.Auth.Domain.Repository.UserRepository;
import board.backend.Auth.Infrastructure.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginCommandHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public LoginResponse handle(LoginCommand command) {
        log.info("LoginCommand 처리 시작: {}" ,command.getEmail());

        //1. Value Object 생성
        Email email =  new Email(command.getEmail());
        Password rawPassword = new Password(command.getPassword());

        //2. 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지않는 유저입니다."));

        //3. 비밀번호 검증
        if (!passwordEncoder.matches(rawPassword.getValue(), user.getEncryptedPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //4. 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getId().getValue());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId().getValue());

        applicationEventPublisher.publishEvent(new UserLoginEvent(user.getId(), user.getEmail().getValue()));
        log.debug(">>> UserLoginEvent 발행 - UserId: {} " , user.getId().getValue());

        log.info("LoginCommand 처리 완료: {}" ,command.getEmail());

        //5. 마지막 로그인 시간 업데이트
        user.recordSuccessfulLogin();



        log.info("LoginCommand 처리 완료: {}", command.getEmail());


        return new LoginResponse(
                new TokenResponse(accessToken, refreshToken),
                user.getId().getValue(),
                user.getEmail().getValue(),
                user.getName()
        );

    }
}
