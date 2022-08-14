package io.tictactoe

import api.dto.*
import feign.Feign
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import io.tictactoe.client.GameClient
import io.tictactoe.service.enums.State
import io.tictactoe.service.enums.Type
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiApplicationTests {

    var randomServerPort: Int = 0
    @Autowired
    var environment: Environment? = null
    var gameClient: GameClient? = null

    fun <T> buildClient(t: Class<T>): T {
        environment.let {
            randomServerPort = Integer.valueOf(it!!.getProperty("local.server.port"))
            return Feign.builder()
                .encoder(GsonEncoder()).decoder(GsonDecoder())
                .target(t, "http://localhost:$randomServerPort")
        }
    }

    @Before
    fun before() {
        gameClient = buildClient(GameClient::class.java)
    }

    @Test
    fun playerAndGameTest() {
        // Test create a game
        val fooRegister = gameClient?.registerGame(RegisterGameDto(players = mutableListOf("x", "o"), rows = 3, cols = 3))
        val gameId = fooRegister!!.gameId!!

        // Assert game was added to gameroom
        val allGames = gameClient?.getGames()
        Assert.assertTrue(allGames!!.games.contains(gameId))

        // Retrieve created gameDTO
        val fooInProgressGame = gameClient?.getInProgressGameById(gameId)!!

        val inProgressDto = InProgressDto(fooInProgressGame.players!!, fooInProgressGame.state)

        // Assert has correct players
        Assert.assertEquals(mutableListOf("x", "o"), inProgressDto.players)

        // Post a move
        val fooRegisterFirstMove = gameClient?.registerMove(
            registerMove = RegisterMoveDto(1, 1), gameId = gameId, playerName = "x"
        )

        // Assert the detail of the game
        val firstMove = gameClient?.getMoveDetail(gameId, 1)

        Assert.assertEquals(firstMove!!.player, "x")
        Assert.assertEquals(firstMove!!.type, Type.MOVE.name)

        // Post another move
        val fooRegisterSecondMove = gameClient?.registerMove(RegisterMoveDto(2, 2,), gameId, "o")

        // Game is in progress

        Assert.assertTrue(fooInProgressGame.state == State.IN_PROGRESS.name)

        println("Register foo OK")
    }
}
