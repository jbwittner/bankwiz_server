package fr.bankwiz.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {

    @Autowired
    WebApplicationContext context;

    @LocalServerPort
    private int port;

    private MockMvc mvc;

    @Test
    void contextLoads() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/status/toto")).andExpect(status().isUnauthorized());
        mvc.perform(MockMvcRequestBuilders.get("/status/toto").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());

        mvc.perform(MockMvcRequestBuilders.get("/status/public")).andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/status/private")).andExpect(status().isUnauthorized());
        mvc.perform(MockMvcRequestBuilders.get("/status/private").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/status/admin")).andExpect(status().isUnauthorized());
        mvc.perform(MockMvcRequestBuilders.get("/status/admin").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isForbidden());
        mvc.perform(MockMvcRequestBuilders.get("/status/admin").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isForbidden());
        mvc.perform(MockMvcRequestBuilders.get("/status/admin")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("SCOPE_admin:configuration"))))
                .andExpect(status().isOk());
    }
}
