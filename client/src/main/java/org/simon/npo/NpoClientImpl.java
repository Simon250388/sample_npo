package org.simon.npo;

import org.simon.npo.dto.StartNpoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class NpoClientImpl implements NpoClient {
    private final RestTemplate restTemplate;
    private final String warehouseId;

    public NpoClientImpl(String warehouseId, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.warehouseId = warehouseId;
    }

    @Override
    public ResponseEntity<Void> startActivity(StartNpoRequest request) {
        var url = "/v1/user-npo/%s/start/list".formatted(warehouseId);
        return restTemplate.postForEntity(url, request, Void.class);
    }
}
