package net.anatolich.iris.infra.monobank;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class MonobankApiClientTest {

    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        restTemplate = restTemplateBuilder.build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getClientInfo() {
        final MonobankProperties properties = MonobankProperties.builder().token("access_token").build();
        final MonobankApiClient apiClient = new MonobankApiClient(restTemplate, properties);

        mockServer
                .expect(requestTo("https://api.monobank.ua/personal/client-info"))
                .andExpect(header("X-Token", "access_token"))
                .andRespond(withStatus(HttpStatus.OK)
                        .body(new ClassPathResource("net/anatolich/iris/infra/monobank/client-info.json"))
                        .contentType(MediaType.APPLICATION_JSON));

        final ClientInfo clientInfo = apiClient.getClientInfo();

        Assertions.assertThat(clientInfo)
                .isNotNull();
    }
}