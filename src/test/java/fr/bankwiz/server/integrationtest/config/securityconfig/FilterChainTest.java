package fr.bankwiz.server.integrationtest.config.securityconfig;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient.AuthorityEnum;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient.UriEnum;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FilterChainTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void unlistedURI401() throws Exception {
        this.client.doGetWithoutJwt(UriEnum.NOT_FOUND.getUri()).andExpect(status().isUnauthorized());
    }

    @Test
    void publicURIOk() throws Exception {
        this.client.doGetWithoutJwt(UriEnum.STATUS_PUBLIC.getUri()).andExpect(status().isOk());
    }

    @Test
    void privateURI401() throws Exception {
        this.client.doGetWithoutJwt(UriEnum.STATUS_PRIVATE.getUri()).andExpect(status().isUnauthorized());
    }

    @Autowired
    WebTestClient webTestClient;

    @Test
    void privateURIOk() throws Exception {
        this.client.doGet(UriEnum.STATUS_PRIVATE.getUri(), "auth0|13546354").andExpect(status().isOk());
    }

    @Test
    void adminURI401() throws Exception {
        this.client.doGetWithoutJwt(UriEnum.STATUS_ADMIN.getUri()).andExpect(status().isUnauthorized());
    }

    @Test
    void adminURI403() throws Exception {
        this.client.doGet(UriEnum.STATUS_ADMIN.getUri(), "auth0|13546354").andExpect(status().isForbidden());
    }

    @Test
    void adminURIOk() throws Exception {
        this.client
                .doGetWithAuthority(UriEnum.STATUS_ADMIN.getUri(), "auth0|13546354", AuthorityEnum.ADMIN_CONFIGURATION)
                .andExpect(status().isOk());
    }
}
