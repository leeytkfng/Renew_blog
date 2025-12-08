package board.backend.Auth.Domain.Event.handler;

import board.backend.Auth.Domain.Event.UserLoginEvent;
import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserLoginEventHandler {
    private final UserRepository userRepository;

    @Async
    @EventListener
    @Transactional
    public void handleUserLogin(UserLoginEvent event) {
        log.debug(">>> [event] UserLoginEvent 수신 - UserId: {} . Email: {}",
                event.getUserId().getValue(), event.getEmail() );

        try {
            User user = userRepository.findById(event.getUserId())
                    .orElse(null);

            if (user != null) {
                user.recordSuccessfulLogin();
                userRepository.save(user);
                log.debug(">>> [Event] lastLoginAt 업데이트 완료 - UserId: {}" , user.getId().getValue());
            } else  {
                log.warn(">>> [Event] 사용자를 찾을 수 없음 - UserId:{}" , event.getUserId().getValue());
            }
        } catch (Exception e) {
            log.error(">>> [Event] lastLoginAt 업데이트 실패 -  UserId: {} , Error: {}",
                    event.getUserId().getValue(), e.getMessage(), e);
        }
    }
}
