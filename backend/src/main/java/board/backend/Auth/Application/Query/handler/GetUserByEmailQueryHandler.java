package board.backend.Auth.Application.Query.handler;

import board.backend.Auth.Application.Query.GetUserByEmailQuery;
import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Vo.Email;
import board.backend.Auth.Domain.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUserByEmailQueryHandler {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User handle(GetUserByEmailQuery query) {
        log.debug("GetUserByEmailQuery 처리 : {}" , query.getEmail());

        Email email = new Email(query.getEmail());

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
    }
}
