package org.simon.npo;

import org.simon.npo.dto.UserNpoResponse;
import org.simon.npo.dto.UserNpoStartRequest;
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
  public ResponseEntity<Void> startNpoActivity(UserNpoStartRequest request) {
    var url = "/v1/user-npo/%s/start".formatted(warehouseId);
    return restTemplate.postForEntity(url, request, Void.class);
  }

  @Override
  public ResponseEntity<UserNpoResponse> isActiveNpoActivityActive(String userName) {
    var url = "/v1/user-npo/%s/active/%s".formatted(warehouseId, userName);
    return restTemplate.getForEntity(url, UserNpoResponse.class);
  }
}
