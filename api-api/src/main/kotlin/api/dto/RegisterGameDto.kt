package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RegisterGameDto(
    @JsonProperty("players") val players: MutableList<String>,
    @JsonProperty("cols") val cols: Int,
    @JsonProperty("rows") val rows: Int
)
