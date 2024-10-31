package fr.bankwiz.server.infrastructure.apirest.controller.usercontroller;

import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.infrastructure.apirest.controller.Endpoints;
import fr.bankwiz.server.infrastructure.apirest.controller.data.dto.UserDTO;

@DisplayName("Authentication User Test")
class AuthenticationUserTest extends UserControllerTestBase {

    final String url = this.base_url + Endpoints.User.AUTHENTICATE;

    @Test
    @DisplayName("User not authenticated")
    void user_not_authenticated() {
        // 👉 When
        this.apiTestHelper.getRequestWithoutAuthentication(url, HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("User authenticated")
    void user_authenticated() {
        // ⚙ Given that
        final UserDomain userDomain = Instancio.create(UserDomain.class);
        Mockito.when(this.userDomainApi.authenticationUser()).thenReturn(userDomain);

        // 👉 When
        final var result = this.apiTestHelper.getRequest(url, HttpStatus.OK, UserDTO.class);

        // ✅ Then
        final UserDTO expectedUserDTO = this.restUserDomainMapper.toDTO(userDomain);
        Assertions.assertThat(result).isEqualTo(expectedUserDTO);
    }
}
