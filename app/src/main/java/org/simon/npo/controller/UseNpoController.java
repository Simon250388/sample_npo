package org.simon.npo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.simon.npo.dto.StartNpoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-npo")
@RequiredArgsConstructor
public class UseNpoController {

  @PostMapping("/{warehouseId}/start/list")
  public ResponseEntity<Void> start(
      @PathVariable String warehouseId, @Valid StartNpoRequest request) {
    throw new UnsupportedOperationException();
  }
}
