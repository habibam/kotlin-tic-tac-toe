package api

import api.dto.*
import feign.Headers
import feign.Param
import feign.RequestLine
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import javax.validation.Valid

@Headers("Content-Type: application/json")
interface GameHandlerApi {

    @RequestLine("POST /tictactoe")
    fun registerGame(register: RegisterGameDto, errors: Errors): CreatedGameDto

    @RequestLine("POST /tictactoe/{gameId}/{playerId}")
    fun registerMove(@Valid @RequestBody registerMove: RegisterMoveDto, @PathVariable("gameId") gameId: UUID, @PathVariable("playerName") playerName: String): OutMoveDto

    @RequestLine("GET /tictactoe")
    fun getGames(): GameIdsDto

    @RequestLine("PUT /tictactoe/{gameId}/{playerName}/quit")
    fun quitGame(@PathVariable("gameId") gameId: UUID, @PathVariable("playerName") playerName: String): String

    @RequestLine("GET /tictactoe/{gameId}")
    fun getGameById(@PathVariable("gameId") gameId: UUID): OutGameByIdDto

    @RequestLine("GET /tictactoe/{gameId}/moves")
    fun getMovesById(@PathVariable("gameId") gameId: UUID): MovesDto

    @RequestLine("GET /tictactoe/{gameId}/moves/{moveNumber}")
    fun getMoveDetail(@PathVariable("gameId") gameId: UUID, @PathVariable("moveNumber") moveNumber: Int): OutMovesDto
}
