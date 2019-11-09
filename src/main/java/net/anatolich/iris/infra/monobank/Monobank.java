package net.anatolich.iris.infra.monobank;

import lombok.extern.java.Log;
import net.anatolich.iris.domain.settlement.Bank;
import net.anatolich.iris.domain.settlement.BankAccount;
import org.javamoney.moneta.Money;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log
public class Monobank implements Bank {

    private final RestTemplate restTemplate;
    private final MonobankProperties properties;

    Monobank(RestTemplate restTemplate, MonobankProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Override
    public Money getAccountBalance(BankAccount.Id bankAccountId) {
        return null;
    }

    @Override
    public List<BankAccount> getAccounts() {
        final String url = "https://api.monobank.ua/personal/client-info";
        final AccessToken accessToken = properties.accessToken();
        final HttpHeaders headers = new HttpHeaders();
        headers.add(accessToken.getHeader(), accessToken.getToken());
        final RequestEntity<ClientInfo> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        final ResponseEntity<ClientInfo> clientInfoResponse = restTemplate.exchange(request, ClientInfo.class);
        final ClientInfo clientInfo = clientInfoResponse.getBody();
        return clientInfo != null ? clientInfo.getAccounts().stream()
                .map(ClientInfo.Account::toBankAccount)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

}
