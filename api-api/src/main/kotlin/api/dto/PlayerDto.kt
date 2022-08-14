package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerDto(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("playerId") val playerId: UUID? = null,
    @JsonProperty("playerName") val playerName: String? = null,
    @JsonProperty("gameId") val gameId: UUID? = null
)
