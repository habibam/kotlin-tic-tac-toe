package io.tictactoe.service.resource

import api.dto.* // ktlint-disable no-wildcard-imports
import io.tictactoe.service.enums.State
import io.tictactoe.service.enums.Type
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class GameHandlerTest {
    val gameHandler = GameHandler()

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun gameResultTestWin() {
        val gameId = UUID.randomUUID()
        val playersList = mutableListOf<PlayerDto>(
            PlayerDto(UUID.randomUUID(), "x", gameId = gameId),
            PlayerDto(UUID.randomUUID(), "o", gameId = gameId)
        )

        val movesList = mutableListOf<MoveDto>(
            MoveDto(UUID.randomUUID(), 1, gameId, "x", Type.MOVE.name, 1, 1),
            MoveDto(UUID.randomUUID(), 2, gameId, "o", Type.MOVE.name, 2, 1),
            MoveDto(UUID.randomUUID(), 3, gameId, "x", Type.MOVE.name, 1, 2),
            MoveDto(UUID.randomUUID(), 4, gameId, "o", Type.MOVE.name, 2, 2),
            MoveDto(UUID.randomUUID(), 5, gameId, "x", Type.MOVE.name, 1, 3)
        )

        val gameboardDto = GameboardDto(1L, gameId, playersList, null, movesList)
        val gameDto = GameDto(gameId, UUID.randomUUID(), gameboardDto, State.IN_PROGRESS.name, 3, 3, null)

        val gameResult = gameHandler.gameResult(movesList, "x", gameDto)

        Assert.assertEquals(gameResult.winner, "x")
        Assert.assertEquals(gameResult.state, State.COMPLETE.name)
    }

    @Test
    fun gameResultTestDraw() {
        val gameId = UUID.randomUUID()
        val playersList = mutableListOf<PlayerDto>(
            PlayerDto(UUID.randomUUID(), "x", gameId = gameId),
            PlayerDto(UUID.randomUUID(), "o", gameId = gameId)
        )

        val movesList = mutableListOf<MoveDto>(
            MoveDto(UUID.randomUUID(), 1, gameId, "x", Type.MOVE.name, 1, 1),
            MoveDto(UUID.randomUUID(), 2, gameId, "o", Type.MOVE.name, 2, 1),
            MoveDto(UUID.randomUUID(), 3, gameId, "x", Type.MOVE.name, 2, 2),
            MoveDto(UUID.randomUUID(), 4, gameId, "o", Type.MOVE.name, 1, 2),
            MoveDto(UUID.randomUUID(), 5, gameId, "x", Type.MOVE.name, 1, 3),
            MoveDto(UUID.randomUUID(), 1, gameId, "x", Type.MOVE.name, 2, 3),
            MoveDto(UUID.randomUUID(), 2, gameId, "o", Type.MOVE.name, 3, 1),
            MoveDto(UUID.randomUUID(), 3, gameId, "x", Type.MOVE.name, 3, 2),
            MoveDto(UUID.randomUUID(), 4, gameId, "o", Type.MOVE.name, 3, 3)
        )

        val gameboardDto = GameboardDto(1L, gameId, playersList, null, movesList)
        val gameDto = GameDto(gameId, UUID.randomUUID(), gameboardDto, State.IN_PROGRESS.name, 3, 3, null)

        val gameResult = gameHandler.gameResult(movesList, "x", gameDto)

        Assert.assertEquals(gameResult.winner, null)
        Assert.assertEquals(gameResult.state, State.COMPLETE.name)
    }
}
