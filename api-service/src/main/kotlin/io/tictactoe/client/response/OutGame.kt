package io.tictactoe.client.response

//import io.tictactoe.model.Game
import java.util.*

//
//
//data class OutGame(
//    val gameId: Game = Game()
//
//)
//
//data class CreatedGame(
//    val gameId: UUID? = null
//
//)
//
//data class OutMove(
//    val move: String
//)
//
//data class GameIds(
//    val games: List<UUID>
//)
//
//data class Moves(
//    val moves: List<OutMoves>
//)

sealed class OutMoves {
    abstract val type: String
    abstract val player: String
}

data class Play(
    override val type: String,
    override val player: String,
    val row: Int,
    val column: Int
) : OutMoves()

data class Quit(
    override val type: String,
    override val player: String
) : OutMoves()


sealed class OutGameById {
    abstract val players: List<String>
    abstract val state: String
}

data class Complete(
    override val players: MutableList<String>,
    override val state: String,
    val winner: String?

) : OutGameById()

data class InProgress(
    override val players: MutableList<String>,
    override val state: String
) : OutGameById()
