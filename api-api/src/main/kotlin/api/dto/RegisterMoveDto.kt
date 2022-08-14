package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RegisterMoveDto(
    @JsonProperty("col") val col: Int,
    @JsonProperty("row") val row: Int
)
