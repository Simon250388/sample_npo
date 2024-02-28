package org.simon.npo.cucumber;

import java.util.HashMap;
import java.util.Map;

public class NpoTestContext {
  private final Map<String, Object> map = new HashMap<>();
  private static final String API_RESPONSE_KEY = "API_RESPONSE";

  public void putApiResponse(Object voidResponseEntity) {
    map.put(API_RESPONSE_KEY, voidResponseEntity);
  }

  public Object getApiResponse() {
    return map.get(API_RESPONSE_KEY);
  }
}
