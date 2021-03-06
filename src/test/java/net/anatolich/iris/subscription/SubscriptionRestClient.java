package net.anatolich.iris.subscription;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.anatolich.iris.subscription.infra.rest.SubscriptionDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class SubscriptionRestClient {

    private final MockMvc mockMvc;
    private final ObjectMapper json;

    public SubscriptionRestClient(MockMvc mockMvc, ObjectMapper json) {
        this.mockMvc = mockMvc;
        this.json = json;
    }

    public ResultActions subscribe(SubscriptionDto subscriptionCommand) throws Exception {
        return mockMvc.perform(post("/v1/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsBytes(subscriptionCommand)));
    }

    public ResultActions listSubscriptions() throws Exception {
        return mockMvc.perform(get("/v1/subscriptions")
            .accept(MediaType.APPLICATION_JSON));
    }
}
