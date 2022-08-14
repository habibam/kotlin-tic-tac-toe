package api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
class GameroomDto(
    @Id
    @JsonProperty("gameRoomId") val gameRoomId: Long? = null,
    @OneToMany
    @JsonProperty("games") val games: MutableMap<UUID, GameDto> = mutableMapOf(),
)
