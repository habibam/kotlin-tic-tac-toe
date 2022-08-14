package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
sealed class OutGameByIdDto {
    abstract val players: MutableList<String>
    abstract val state: String
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompleteDto(
    @JsonProperty("players") override val players: MutableList<String>,
    @JsonProperty("state") override val state: String,
    @JsonProperty("winner") val winner: String?

) : OutGameByIdDto()

@JsonIgnoreProperties(ignoreUnknown = true)
data class InProgressDto(
    @JsonProperty("players") override val players: MutableList<String>,
    @JsonProperty("state") override val state: String,
) : OutGameByIdDto()
