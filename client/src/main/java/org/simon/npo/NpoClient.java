package org.simon.npo;

import org.simon.npo.dto.UserNpoResponse;
import org.simon.npo.dto.UserNpoStartRequest;
import org.springframework.http.ResponseEntity;

public interface NpoClient {
    ResponseEntity<Void> startNpoActivity(UserNpoStartRequest request);

    ResponseEntity<UserNpoResponse> isActiveNpoActivityActive(String userName);
}
