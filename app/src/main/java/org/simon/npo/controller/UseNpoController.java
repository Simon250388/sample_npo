package org.simon.npo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.simon.npo.dto.UserNpoResponse;
import org.simon.npo.dto.UserNpoStartRequest;
import org.simon.npo.service.UserNpoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-npo")
@RequiredArgsConstructor
@Validated
public class UseNpoController {
  private final UserNpoService userNpoService;

  @PostMapping("/{warehouseId}/start")
  public ResponseEntity<Void> start(
      @PathVariable String warehouseId, @Valid @RequestBody UserNpoStartRequest request) {
    userNpoService.start(warehouseId, request);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{warehouseId}/active/{userName}")
  public ResponseEntity<UserNpoResponse> getActive(
      @PathVariable String warehouseId, @PathVariable @NotBlank String userName) {
    return Optional.ofNullable(userNpoService.getActive(warehouseId, userName))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent().build());
  }
}
