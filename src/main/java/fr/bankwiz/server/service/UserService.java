package fr.bankwiz.server.service;

import java.util.List;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserUpdateRequest;

public interface UserService {

    UserDTO getCurrentUserInfo();

    UserDTO checkRegistration();

    UserDTO getUser(Long userId);

    UserDTO updateUser(UserUpdateRequest userUpdateRequest);

    List<UserDTO> getUsers();
}
