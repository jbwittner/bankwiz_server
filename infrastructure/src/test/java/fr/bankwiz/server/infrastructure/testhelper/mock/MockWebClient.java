package fr.bankwiz.server.infrastructure.testhelper.mock;

import org.springframework.web.reactive.function.client.WebClient;

public class MockWebClient extends MockHelper<WebClient> {

    public MockWebClient() {
        super(WebClient.class);
    }
    
}
