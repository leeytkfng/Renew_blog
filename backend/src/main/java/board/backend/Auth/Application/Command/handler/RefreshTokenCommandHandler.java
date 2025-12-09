package board.backend.Auth.Application.Command.handler;

import board.backend.Auth.Application.Command.RefreshTokenCommand;
import board.backend.Auth.Application.Dto.Token.TokenResponse;
import board.backend.Auth.Domain.Model.Vo.UserId;
import board.backend.Auth.Domain.Repository.UserRepository;
import board.backend.Auth.Infrastructure.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenCommandHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TokenResponse handle(RefreshTokenCommand command) {
        log.info("RefreshTokenCommand 처리 시작 ");

        String refreshToken = command.getRefreshToken();

        //1. refreshToken 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token 입니다");
        }

        //2. Refresh Token 인지 확인
        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh 토큰이 아닙니다.");
        }

        //3. 사용자 ID 추출
        String userId = jwtTokenProvider.getUserId(refreshToken);

        //4. 사용자 존재 확인
        userRepository.findById(new UserId(userId))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));

        //5. 새로운 Access Token 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(userId);

        //6. Refresh Token 이 만료되면 새로 발급
        String newRefreshToken = refreshToken;
        if (jwtTokenProvider.isRefreshToken(refreshToken)){
            newRefreshToken = jwtTokenProvider.createRefreshToken(userId);
            log.info("Refresh Token 재발급");
        }

        log.info("RefreshTokenCommand 처리 완료");

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
