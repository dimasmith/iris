package net.anatolich.iris.infra.homemoney;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

class HomemoneyApiClient {

    private static final String API_PREFIX = "https://homemoney.ua/api/api2.asmx";
    private final RestTemplate restTemplate;
    private final HomemoneyProperties properties;

    HomemoneyApiClient(RestTemplate restTemplate, HomemoneyProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Cacheable(cacheNames = "homemoney")
    public BalanceListDto getBalanceList() {
        final URI requestUri = UriComponentsBuilder.fromUriString(API_PREFIX)
                .pathSegment("BalanceList")
                .queryParam("Token", properties.getToken())
                .build().toUri();
        final RequestEntity<BalanceListDto> balanceListRequest = new RequestEntity<>(HttpMethod.GET, requestUri);
        final ResponseEntity<BalanceListDto> balanceListResponse = restTemplate.exchange(
                balanceListRequest, BalanceListDto.class);
        return balanceListResponse.getBody();
    }

}
