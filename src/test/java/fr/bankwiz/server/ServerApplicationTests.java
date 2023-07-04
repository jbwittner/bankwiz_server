package fr.bankwiz.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {

    @Autowired
    WebApplicationContext context;

    @LocalServerPort
    private int port;

    private MockMvc mvc;

    @Test
    void contextLoads() throws Exception {
        final String baseUrl = "http://localhost:" + port;
        //this.webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();

        mvc = MockMvcBuilders
				.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
				.build();

        mvc.perform(MockMvcRequestBuilders.get("/status/toto")).andExpect(matcher -> {
            System.out.println(matcher.getResponse().getStatus());
        });

        mvc.perform(MockMvcRequestBuilders.get("/status/toto"))

        mvc.perform(MockMvcRequestBuilders.get("/status/public")).andExpect(matcher -> {
            System.out.println(matcher.getResponse().getStatus());
        });

        mvc.perform(MockMvcRequestBuilders.get("/status/private")).andExpect(matcher -> {
            System.out.println(matcher.getResponse().getStatus());
        });

        mvc.perform(MockMvcRequestBuilders.get("/status/private").with(SecurityMockMvcRequestPostProcessors.jwt())).andExpect(matcher -> {
            System.out.println(matcher.getResponse().getStatus());
        });

        mvc.perform(MockMvcRequestBuilders.get("/status/admin").with(SecurityMockMvcRequestPostProcessors.jwt())).andExpect(matcher -> {
            System.out.println(matcher.getResponse().getStatus());
        });

        mvc.perform(MockMvcRequestBuilders.get("/status/admin").with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("SCOPE_admin:configuration")).jwt(jwt -> {
            System.out.println(jwt);
        }))).andExpect(matcher -> {
            System.out.println(matcher.getResponse().getStatus());
        });

        mvc.perform(MockMvcRequestBuilders.get("/status/admin").with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("SCOPE_admin:configurationdqsdqd")).jwt(jwt -> {
            System.out.println(jwt);
        }))).andExpect(matcher -> {
            System.out.println(matcher.getResponse().getStatus());
        });

        Assertions.assertTrue(true);
    }
}
