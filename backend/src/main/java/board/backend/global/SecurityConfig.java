package board.backend.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Spring Security에서 Http 요청을 처리할 때 거치는 필터들의 체인이다.
     * 요청이 컨트롤러에 도달하기 전에 이 필터들은 순차적으로 통과하면서 보안 검사를 실시한다.
     * @param httpSecurity 통일된 보안정책을 제시하기위한 Spring jar
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)  throws  Exception{
        httpSecurity
                //CORS 설정 활성화
                .cors(cors -> cors.configurationSource(configurationSource()))
                // CSRF 비활성화  (Rest API에서는 보통 비활성화)
                .csrf(csrf -> csrf.disable())
                // 세션 관리 (JWT 사용시 Stateless) -> 토큰은 세션이 필요없음.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //권한 설정( 중요!)
                .authorizeHttpRequests(auth -> auth
                    //인증 없이 접근 가능한 경로
                        .requestMatchers("/api/**").permitAll()
                        //그외 경로는 인증 절차를  밟음
                        .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }

    /**
     * CORS(CROSS-Origin Resource sharing)
     * 브라우저는 보안상 다른 도메인 간의 요청을 기본적으로 차단한다.
     * @return
     */
    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 ORIGIN (개발 환경)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173"
        ));

        //허용할 Http 메서도
        configuration.setAllowedMethods(Arrays.asList(
                "GET" , "POST" , "PUT" , "DELETE" , "PATCH" , "OPTION"
        ));

        //허용할 헤더
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization", //JWT 토큰
                "Content-Type" , //JSON, XML등
                "X-Requested-With", //AJAX 요청
                "Accept", //응답 형식
                "Origin", //요청 출처
                "Access-Control-Request-Method", // Preflight
                "Access-Control-Request-Headers" // Preflight
        ));
        //-> 위와 같은 헤더 요청들을 포함해서 파라미터를 받는다.
        // 노출할헤더
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization", //응답에 포함된 토큰
                "Content-Disposition" // 파일 다운로드 정보
        ));
        // -> 클라이언트가 JavaScript로 이 헤더들을 읽을 수 있게.
        //인증 정보 포함 허용
        configuration.setAllowCredentials(true);

        // Preflight 요청 캐시 시간(초)
        configuration.setMaxAge(3600L);
        // 매번 OPTIONS 요청을 보내지 않아도 됨.

        //7. URL 패턴에 CORS 설정 등록
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        // -> "/api/** " 로 시작하는 모든 경로에 이 CORS 설정 적용.


        return source;
    }

    /**
     * 실제 동작 예시
     * 1. CORS 필터 : localhost:5173에서 허용
     * 2. CSRF 필터 : (비활성화)
     * 3. 세션 필터 : 세션 비활성화
     * 4. 권한 체크 /api/** 로그인 허용
     * -> 컨트롤러로 전달.
     */

    /**
     * BcryptEncoder로 비밀번호 암호화 및 비교 인스턴스 생성.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     *  Preflight 요청이란
     *
     *  브라우저가 실제로 요청하기 전에 " 이요청 보내도 돼? " 라고 사전에 확인하는 과정이다.
     *  1. Preflight 사전확인
     *  브라우저 -> OPTIONS /api/users
     *  " POST 요청 보내도 돼? Authorization 헤더 포함해도 돼?"
     *
     *  서버 -> 200 OK
     *      "응 , POST 가능해 Authorization도 OK!"
     *  실제 요청
     *  브라우저 -> POST /api/users
     *      Header: Authorization : Bearer xxx...
     *      Body : {"name" : "홍길동"}
     */
}
