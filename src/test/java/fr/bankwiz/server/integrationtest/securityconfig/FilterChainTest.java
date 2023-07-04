package fr.bankwiz.server.integrationtest.securityconfig;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.integrationtest.testhelper.AbstractIntegrationTestBase;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient.AuthorityEnum;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient.UriEnum;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FilterChainTest extends AbstractIntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void unexistedURI401() throws Exception {
        this.client.doGet(UriEnum.NOT_FOUND.getUri()).andExpect(status().isUnauthorized());
    }

    @Test
    void publicURI200() throws Exception {
        this.client.doGet(UriEnum.STATUS_PUBLIC.getUri()).andExpect(status().isOk());
    }

    @Test
    void privateURI401() throws Exception {
        this.client.doGet(UriEnum.STATUS_PRIVATE.getUri()).andExpect(status().isUnauthorized());
    }

    @Test
    void privateURI200() throws Exception {
        this.client
                .doGetWithJwt(UriEnum.STATUS_PRIVATE.getUri(), "auth0|13546354")
                .andExpect(status().isOk());
        System.out.println("after call");
    }

    @Test
    void adminURI401() throws Exception {
        this.client.doGet(UriEnum.STATUS_ADMIN.getUri()).andExpect(status().isUnauthorized());
    }

    @Test
    void adminURI403() throws Exception {
        this.client
                .doGetWithJwt(UriEnum.STATUS_ADMIN.getUri(), "auth0|13546354")
                .andExpect(status().isForbidden());
    }

    @Test
    void adminURI200() throws Exception {
        this.client
                .doGetWithJwtAndAuthority(
                        UriEnum.STATUS_ADMIN.getUri(), "auth0|13546354", AuthorityEnum.ADMIN_CONFIGURATION)
                .andExpect(status().isOk());
    }
}
