package io.tictactoe.client

import api.dto.*
import feign.Headers
import feign.Param
import feign.RequestLine
import java.util.*

@Headers("Content-Type: application/json")
interface GameClient {

    @RequestLine("POST /tictactoe")
    fun registerGame(register: RegisterGameDto): CreatedGameDto

    @RequestLine("POST /tictactoe/{gameId}/{playerName}")
    fun registerMove(registerMove: RegisterMoveDto, @Param("gameId") gameId: UUID, @Param("playerName") playerName: String): OutMoveDto

    @RequestLine("GET /tictactoe")
    fun getGames(): GameIdsDto

    @RequestLine("PUT /tictactoe/{gameId}/{playerName}/quit")
    fun quitGame(@Param("gameId") gameId: UUID, @Param("playerName") playerName: String): String

    @RequestLine("GET /tictactoe/{gameId}")
    fun getCompleteGameById(@Param("gameId") gameId: UUID): CompleteDto

    @RequestLine("GET /tictactoe/{gameId}")
    fun getInProgressGameById(@Param("gameId") gameId: UUID): InProgressDto

    @RequestLine("GET /tictactoe/{gameId}/moves")
    fun getMovesById(@Param("gameId") gameId: UUID): MovesDto

    @RequestLine("GET /tictactoe/{gameId}/moves/{moveNumber}")
    fun getMoveDetail(@Param("gameId") gameId: UUID, @Param("moveNumber") moveNumber: Int): PlayDto

    @RequestLine("GET /tictactoe/{gameId}/moves/{moveNumber}")
    fun getQuitDetail(@Param("gameId") gameId: UUID, @Param("moveNumber") moveNumber: Int): QuitDto
}
