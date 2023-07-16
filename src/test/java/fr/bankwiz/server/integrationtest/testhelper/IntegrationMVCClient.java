package fr.bankwiz.server.integrationtest.testhelper;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fr.bankwiz.openapi.model.FunctionalExceptionDTO;
import fr.bankwiz.server.exception.FunctionalException;

@Component
public class IntegrationMVCClient {

    private final MockMvc mvc;

    public IntegrationMVCClient(WebApplicationContext context) {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    public enum AuthorityEnum {
        ADMIN_CONFIGURATION("admin:configuration");

        private final String authority;

        AuthorityEnum(String authority) {
            this.authority = authority;
        }

        public String getAuthority() {
            return this.authority;
        }
    }

    public enum UriEnum {
        NOT_FOUND("/toto"),
        STATUS_PUBLIC("/status/public"),
        STATUS_PRIVATE("/status/private"),
        STATUS_ADMIN("/status/admin"),
        USER("/user"),
        USER_CHECKREGISTRATION("/user/checkregistration"),
        USERS("/user/users"),
        USER_ID("/user/{0}"),
        GROUP("/group"),
        GROUP_ID_USER("/group/{0}/user"),
        GROUP_ID("/group/{0}"),
        GROUPS("/group/groups"),
        GROUP_ID_USER_ID("/group/{0}/user/{1}"),
        ACCOUNT("/account"),
        ACCOUNT_ID_ACCOUNT("/account/{0}"),
        ACCOUNTS("/account/accounts"),
        ACCOUNTLINE("/accountline"),
        ACCOUNTLINE_ID_LINE("/accountline/{0}");

        private final String uri;

        UriEnum(String uri) {
            this.uri = uri;
        }

        public String getUri() {
            return this.uri;
        }

        public String getUri(Object... arguments) {
            return MessageFormat.format(this.uri, arguments);
        }
    }

    public static <T> T convertMvcResultToResponseObject(MvcResult mvcResult, Class<T> responseClass) throws Exception {
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(contentAsString, responseClass);
    }

    public static void checkResponseFunctionalException(
            final ResultActions resultActions, final FunctionalException functionalExceptionExpected) throws Exception {

        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        final Exception exception = resultActions.andReturn().getResolvedException();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(exception),
                () -> Assertions.assertEquals(functionalExceptionExpected.getClass(), exception.getClass()));

        final var response = resultActions.andReturn();

        final FunctionalExceptionDTO functionalExceptionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(response, FunctionalExceptionDTO.class);

        var uri = "uri=" + resultActions.andReturn().getRequest().getRequestURI();

        Assertions.assertAll(
                "Check functionalExceptionDTO",
                () -> Assertions.assertEquals(
                        functionalExceptionExpected.getMessage(), functionalExceptionDTO.getMessage()),
                () -> Assertions.assertEquals(
                        functionalExceptionExpected.getClass().getSimpleName(), functionalExceptionDTO.getException()),
                () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), functionalExceptionDTO.getStatus()),
                () -> Assertions.assertEquals(uri, functionalExceptionDTO.getDetails()));
    }

    public ResultActions doGetWithoutJwt(final String url) throws Exception {
        return this.mvc.perform(MockMvcRequestBuilders.get(url));
    }

    public ResultActions doGet(final String url, final String subject) throws Exception {
        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", subject)
                .build();

        return this.mvc.perform(MockMvcRequestBuilders.get(url)
                .with(SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt)));
    }

    public ResultActions doGetWithAuthority(final String url, final String subject, final AuthorityEnum... authorities)
            throws Exception {

        final List<String> authoritiesStringList =
                Arrays.stream(authorities).map(s -> "SCOPE_" + s.getAuthority()).toList();

        final Collection<GrantedAuthority> authoritiesCollection =
                AuthorityUtils.createAuthorityList(authoritiesStringList);

        return this.mvc.perform(MockMvcRequestBuilders.get(url)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                        .authorities(authoritiesCollection)
                        .jwt(jwt -> {
                            jwt.subject(subject);
                        })));
    }
}
