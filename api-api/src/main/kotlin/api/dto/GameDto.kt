package api.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class GameDto(
    @Id
    @JsonProperty("gameId") val gameId: UUID? = null,
    @JsonProperty("gameRoomId") val gameRoomId: UUID = UUID.randomUUID(),
    @OneToOne
    @JsonProperty("gameboard") val gameboard: GameboardDto = GameboardDto(),
    @JsonIgnore
    @JsonProperty("state") var state: String? = null,
    @JsonProperty("cols") val cols: Int? = null,
    @JsonProperty("rows") val rows: Int? = null,
    @JsonProperty("winner") var winner: String? = null
)
