package board.backend.Auth.Infrastructure.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long accessTokenValidityMs;
    private final long refreshTokenValidityMs;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long accessTokenValidityMs,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidityMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityMs = accessTokenValidityMs;
        this.refreshTokenValidityMs = refreshTokenValidityMs;

        log.info("JWT 토큰 초기화 완료");
        log.debug("AccessToken 유효 시간: {}ms", accessTokenValidityMs);
        log.debug("RefreshToken 유효 시간: {}ms" , refreshTokenValidityMs);
    }

    public String createAccessToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityMs);

        return Jwts.builder()
                .setSubject(userId)
                .claim("type", "access")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityMs);

        return Jwts.builder()
                .setSubject(userId)
                .claim("type" , "refresh")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * Access Token인지 확인
     */
    public boolean isAccessToken(String token) {
        String type = getClaims(token).get("type", String.class);
        return "access".equals(type);
    }

    /**
     * Refresh Token인지 확인
     */
    public boolean isRefreshToken(String token) {
        String type = getClaims(token).get("type", String.class);
        return "refresh".equals(type);
    }





    public boolean isTokenExpirationSoon(String token) {
        Date expiration = getExpirationDate(token);
        Date now = new Date();
        long diff = expiration.getTime() - now.getTime();
        return diff < 5 * 60 * 1000; // 5분
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
