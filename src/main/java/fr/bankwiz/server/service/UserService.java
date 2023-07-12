package fr.bankwiz.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserUpdateRequest;

@Service
public class UserService {

    public UserDTO getCurrentUserInfo() {
        return null;
    }

    public UserDTO checkRegistration() {
        return null;
    }

    public UserDTO getUser(final Long userId) {
        return null;
    }

    public UserDTO updateUser(final UserUpdateRequest userUpdateRequest) {
        return null;
    }

    public List<UserDTO> getUsers() {
        return null;
    }
}
