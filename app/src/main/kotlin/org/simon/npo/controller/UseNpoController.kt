package org.simon.npo.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import lombok.RequiredArgsConstructor
import org.simon.npo.dto.UserNpoResponse
import org.simon.npo.dto.UserNpoStartRequest
import org.simon.npo.service.UserNpoService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/user-npo")
@RequiredArgsConstructor
@Validated
class UseNpoController(private val userNpoService: UserNpoService) {

    @PostMapping("/{warehouseId}/start")
    fun start(
        @PathVariable warehouseId: String, @Valid @RequestBody request: UserNpoStartRequest
    ): ResponseEntity<Void> = userNpoService.start(warehouseId, request).run { ResponseEntity.ok().build() }


    @GetMapping("/{warehouseId}/active/{userName}")
    fun getActive(
        @PathVariable warehouseId: String, @PathVariable userName: @NotBlank String
    ): ResponseEntity<UserNpoResponse> = userNpoService.getActive(warehouseId, userName)
        ?.let { ResponseEntity.ok(it) }
        ?: ResponseEntity.noContent().build()
}
