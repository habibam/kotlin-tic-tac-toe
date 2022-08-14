package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class GameboardDto(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("gameboardId") val gameboardId: Long? = null,
    @JsonProperty("gameId") val gameId: UUID? = null,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "gameId")
    @JsonProperty("players") val players: MutableList<PlayerDto> = mutableListOf(),
    @JsonProperty("recentPlayer") var recentPlayer: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "gameId")
    @JsonProperty("moves") val moves: MutableList<MoveDto> = mutableListOf(),
)
