package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
sealed class OutMovesDto {
    abstract val type: String
    abstract val player: String
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayDto(
    @JsonProperty("type") override val type: String,
    @JsonProperty("player") override val player: String,
    @JsonProperty("row") val row: Int,
    @JsonProperty("column") val column: Int
) : OutMovesDto()

@JsonIgnoreProperties(ignoreUnknown = true)
data class QuitDto(
    @JsonProperty("type") override val type: String,
    @JsonProperty("player") override val player: String
) : OutMovesDto()
