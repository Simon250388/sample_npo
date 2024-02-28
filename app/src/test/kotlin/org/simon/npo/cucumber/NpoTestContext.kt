package org.simon.npo.cucumber

import org.springframework.http.ResponseEntity

@Suppress("UNCHECKED_CAST")
class NpoTestContext(
    private val map: MutableMap<String, Any> = HashMap()) {
    fun putApiResponse(voidResponseEntity: Any) {
        map[API_RESPONSE_KEY] = voidResponseEntity
    }

    fun <T> getApiResponse(): ResponseEntity<T> = map[API_RESPONSE_KEY] as ResponseEntity<T>
}

const val  API_RESPONSE_KEY = "API_RESPONSE"
