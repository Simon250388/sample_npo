package org.simon.npo;

import org.simon.npo.dto.StartNpoRequest;
import org.springframework.http.ResponseEntity;

public interface NpoClient {
    ResponseEntity<Void> startActivity(StartNpoRequest request);
}
