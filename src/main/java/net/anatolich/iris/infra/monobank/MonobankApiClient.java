package net.anatolich.iris.infra.monobank;

import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Log
public class MonobankApiClient {
    private static final String API_PREFIX = "https://api.monobank.ua";
    private final RestTemplate restTemplate;
    private final MonobankProperties properties;

    public MonobankApiClient(RestTemplate restTemplate, MonobankProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Cacheable(cacheNames = "monobank")
    public ClientInfo getClientInfo() {
        log.info("querying monobank api for client info");
        final String resource = "/personal/client-info";
        return getResource(ClientInfo.class, resource);
    }

    private <T> T getResource(Class<T> type, String resourceUri) {
        final String url = API_PREFIX + resourceUri;

        final AccessToken accessToken = properties.accessToken();
        final HttpHeaders headers = new HttpHeaders();
        headers.add(accessToken.getHeader(), accessToken.getToken());

        final RequestEntity<T> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        final ResponseEntity<T> clientInfoResponse = restTemplate.exchange(request, type);
        return clientInfoResponse.getBody();
    }
}
