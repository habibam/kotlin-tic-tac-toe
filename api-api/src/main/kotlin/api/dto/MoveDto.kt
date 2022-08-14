package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class MoveDto(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("moveId") val moveId: UUID? = null,
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("moveNumber") val moveNumber: Int? = null,
    @JsonProperty("gameId") val gameId: UUID? = null,
    @JsonProperty("player") val player: String? = null,
    @JsonProperty("type") val type: String? = null,
    @JsonProperty("rowNum") val rowNum: Int? = null,
    @JsonProperty("col") val col: Int? = null
)
