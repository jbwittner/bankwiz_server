package fr.bankwiz.server.integrationtest.testhelper;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Component
public class IntegrationMVCClient {

    private MockMvc mvc;

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

    public ResultActions doGet(final String url) throws Exception {
        return this.mvc.perform(MockMvcRequestBuilders.get(url));
    }

    public ResultActions doGetWithJwt(final String url, final String subject) throws Exception {
        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user")
                .build();

        return this.mvc.perform(MockMvcRequestBuilders.get(url)
                .with(SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt)));
    }

    public ResultActions doGetWithJwtAndAuthority(
            final String url, final String subject, final AuthorityEnum... authorities) throws Exception {

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
