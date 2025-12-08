package board.backend.Auth.Presentation.Controller;

import board.backend.Auth.Application.Command.LoginCommand;
import board.backend.Auth.Application.Command.RefreshTokenCommand;
import board.backend.Auth.Application.Command.SignUpCommand;
import board.backend.Auth.Application.Command.handler.LoginCommandHandler;
import board.backend.Auth.Application.Command.handler.RefreshTokenCommandHandler;
import board.backend.Auth.Application.Command.handler.SignUpCommandHandler;
import board.backend.Auth.Application.Dto.Login.LoginRequest;
import board.backend.Auth.Application.Dto.Login.LoginResponse;
import board.backend.Auth.Application.Dto.SignUp.SignUpRequest;
import board.backend.Auth.Application.Dto.Token.TokenRequest;
import board.backend.Auth.Application.Dto.Token.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final LoginCommandHandler loginCommandHandler;
    private final SignUpCommandHandler signUpCommandHandler;
    private final RefreshTokenCommandHandler refreshTokenCommandHandler;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("┌─ [API Request] POST /api/auth/login - Email: {}", request.getEmail());

        try {
            //Request -> Command 형식으로 변환한다.
            LoginCommand command = new LoginCommand(
                    request.getEmail(),
                    request.getPassword()
            );

            //Command Handler 실행
            LoginResponse response  = loginCommandHandler.handle(command);

            log.info("└─ [API Response] POST /api/auth/login - Success");
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e) {
            log.error("└─ [API Response] POST /api/auth/login - Validation Error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("└─ [API Response] POST /api/auth/login - Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        log.info("┌─ [API Request] POST /api/auth/logout");

        try {
            // 로그아웃은 클라이언트에서 토큰을 삭제하는 것으로 충분
            // 서버에서는 특별한 처리가 필요 없음 (stateless JWT)

            Map<String, String> response = new HashMap<>();
            response.put("message", "로그아웃되었습니다");

            log.info("└─ [API Response] POST /api/auth/logout - Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("└─ [API Response] POST /api/auth/logout - Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenRequest request) {
        log.info("┌─ [API Request] POST /api/auth/refresh");

        try {
            RefreshTokenCommand command = new RefreshTokenCommand(request.getRefreshToken());

            // Command Handler 실행
            TokenResponse response = refreshTokenCommandHandler.handle(command);

            log.info("└─ [API Response] POST /api/auth/refresh - Success");
            return ResponseEntity.ok(response);


        } catch (IllegalArgumentException e) {
            log.error("└─ [API Response] POST /api/auth/refresh - Validation Error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("└─ [API Response] POST /api/auth/refresh - Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    //회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody SignUpRequest request) {
        try {
            //Request -> Command 변환
            SignUpCommand command = new SignUpCommand(
                    request.getEmail(),
                    request.getPassword(),
                    request.getName(),
                    request.getPhone(),
                    request.getAddress()
            );

            //Command Hanlder 실행
            signUpCommandHandler.handler(command);

            log.info("└─ [API Response] POST /api/auth/signup - Success");

            Map<String,String> response = new HashMap<>();
            response.put("message", "회원가입이 완료되었습니다");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            log.error("└─ [API Response] POST /api/auth/signup - Validation Error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("└─ [API Response] POST /api/auth/signup - Error: {}", e.getMessage(), e);
            throw e;
        }

    }
}
