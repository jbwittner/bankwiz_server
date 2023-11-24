package fr.bankwiz.server.infrastructure.transformer;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public final class UserTransformer {

    private UserTransformer() {}

    public static UserDTO toUserDTO(final User user) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static User fromUserEntity(final UserEntity userEntity) {
        return User.builder()
                .authId(userEntity.getAuthId())
                .email(userEntity.getEmail())
                .userId(userEntity.getId())
                .build();
    }

    public static UserEntity toUserEntity(final User user) {
        return UserEntity.builder()
                .authId(user.getAuthId())
                .email(user.getEmail())
                .id(user.getUserId())
                .build();
    }
}
