package board.backend.Auth.Infrastructure.Repository.Impl;

import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Value.Email;
import board.backend.Auth.Domain.Model.Value.Password;
import board.backend.Auth.Domain.Model.Value.UserId;
import board.backend.Auth.Domain.Repository.UserRepository;
import board.backend.Auth.Infrastructure.Entity.UserMongoEntity;
import board.backend.Auth.Infrastructure.Persistence.UserMapper;
import board.backend.Auth.Infrastructure.Repository.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
        return mongoRepository.findById(userId.getValue())
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return mongoRepository.findByEmail(email.getValue())
                .map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return mongoRepository.existsByEmail(email.getValue());
    }

    @Override
    public void delete(UserId userId) {
        mongoRepository.deleteById(userId.getValue());
    }

    // Mongo Entity → Domain Entity
    private User toDomain(UserMongoEntity entity) {
        return User.register(
                new Email(entity.getEmail()),
                entity.getPassword(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress()
        );
    }

}
