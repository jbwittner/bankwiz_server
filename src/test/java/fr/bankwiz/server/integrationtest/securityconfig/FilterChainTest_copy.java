package fr.bankwiz.server.integrationtest.securityconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bankwiz.server.integrationtest.testhelper.AbstractIntegrationTestBase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FilterChainTest_copy extends AbstractIntegrationTestBase {

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Override
    protected void initDataBeforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void unexistedURI401() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/toto")).andExpect(status().isUnauthorized());
    }

    @Test
    void unexistedURI404() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/toto").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void publicURI200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/public")).andExpect(status().isOk());
    }

    @Test
    void privateURI401() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/private")).andExpect(status().isUnauthorized());
    }

    @Test
    void privateURI200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/private").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

    @Test
    void adminURI401() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/admin")).andExpect(status().isUnauthorized());
    }

    @Test
    void adminURI403() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/admin").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminURI200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status/admin")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("SCOPE_admin:configuration"))))
                .andExpect(status().isOk());
    }
}
