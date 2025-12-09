package board.backend.Auth.Infrastructure.Repository.Impl;

import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Vo.Email;
import board.backend.Auth.Domain.Model.Vo.UserId;
import board.backend.Auth.Domain.Repository.UserRepository;
import board.backend.Auth.Infrastructure.Entity.UserMongoEntity;
import board.backend.Auth.Infrastructure.Persistence.UserMapper;
import board.backend.Auth.Infrastructure.Repository.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMongoRepository mongoRepository;
    private final UserMapper userMapper;
    @Override
    public User save(User user) {
        UserMongoEntity entity = userMapper.toJpaEntity(user);
        UserMongoEntity saved = mongoRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        log.debug(">>> [Repository] findById 호출: {}", userId.getValue());

        //Spring Data가 자동으로 _id로 조회
        return mongoRepository.findById(userId.getValue())
                .map(entity -> {
                    log.debug(">>> [Repository] 사용자 찾음: {}", entity.getId());
                    return userMapper.toDomain(entity);
                });
    }


    @Override
    public Optional<User> findByEmail(Email email) {
        log.debug(">>> [Repository] findByEmail 호출: email={}", email.getValue());

        return mongoRepository.findByEmail(email.getValue())
                .map(entity -> {
                    log.debug(">>> [Repository] 이메일로 사용자 찾음: userId={}", entity.getId());
                    return userMapper.toDomain(entity);
                });
    }

    @Override
    public boolean existsByEmail(Email email) {
        return mongoRepository.existsByEmail(email.getValue());
    }

    @Override
    public void delete(UserId userId) {
        mongoRepository.deleteById(userId.getValue());
    }

}
