package board.backend.Auth.Infrastructure.Persistence;

import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Enum.UserStatus;
import board.backend.Auth.Domain.Model.Value.Email;
import board.backend.Auth.Domain.Model.Value.UserId;
import board.backend.Auth.Infrastructure.Entity.UserMongoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper {
   //Domain -> JPA Entity
    public UserMongoEntity toJpaEntity(User user) {
        log.debug(">>> Domain → Mongo 변환");
        log.debug("    name: {}", user.getName());
        log.debug("    phone: {}", user.getPhone());
        log.debug("    address: {}", user.getAddress());
         UserMongoEntity entity = new UserMongoEntity(
                user.getId().getValue(), //id
                user.getEmail().getValue(), // email
                user.getEncryptedPassword(), // password
                user.getName(),
                user.getPhone(),
                user.getAddress(),
                user.getStatus().name(),
                user.getCreatedAt(),
                user.getLastLoginAt(),
                user.getPlatform(),
                user.getDeletedAt(),
                user.getIpAddress()
        );

        log.debug("<<< Mongo Entity 생성");
        log.debug("    name: {}", entity.getName());
        log.debug("    phone: {}", entity.getPhone());
        log.debug("    address: {}", entity.getAddress());

        return entity;
    }
    public User toDomain(UserMongoEntity entity) {
        return User.reconstitute( UserId.of(entity.getId()),
                new Email(entity.getEmail()),
                entity.getPassword(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress(),
                UserStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getLastLoginAt()
        );
    }
}
