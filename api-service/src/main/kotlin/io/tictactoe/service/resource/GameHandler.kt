package io.tictactoe.service.resource

import api.GameHandlerApi
import api.dto.*
import api.dto.CompleteDto
import api.dto.InProgressDto
import api.dto.OutGameByIdDto
import api.dto.OutMovesDto
import api.dto.PlayDto
import api.dto.QuitDto
import io.tictactoe.service.enums.State
import io.tictactoe.service.enums.Type
import io.tictactoe.service.exception.BadRequestException
import io.tictactoe.service.exception.InvalidRequest
import io.tictactoe.service.exception.NotFoundException
import org.springframework.validation.BindException
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import kotlin.collections.ArrayList

@RestController
class GameHandler() : GameHandlerApi {
    val gameroom = GameroomDto()

    private fun isBoardFull(moves: MutableList<MoveDto>): Boolean {
        return moves.size == 9
    }
    private fun isGameWon(board: MutableList<String>, player: String): Boolean {
        return (board[0] == player && board[1] == player && board[2] == player)
    }

    fun gameResult(moves: MutableList<MoveDto>, player: String, game: GameDto): GameDto {
        // Build a list of all possible winning combinations
        val row0 = arrayListOf("", "", "")
        val row1 = arrayListOf("", "", "")
        val row2 = arrayListOf("", "", "")
        val col0 = arrayListOf("", "", "")
        val col1 = arrayListOf("", "", "")
        val col2 = arrayListOf("", "", "")
        val dag1 = arrayListOf("", "", "")
        val dag2 = arrayListOf("", "", "")

        // Map over the moves add to winning combination lists, anytime a player occupies a spot, build add their name to the space. ie. ["x","x","x"]
        game!!.gameboard.moves.map {
            populateWinningLists(it, row0, col0, dag1, col1, col2, dag2, row1, row2)
        }

        // evaluate for a win and update game status
        evaluateWin(row0, player, game, row1, row2, col0, col1, col2, dag1, dag2, moves)
        return game
    }

    private fun populateWinningLists(
        it: MoveDto,
        row0: ArrayList<String>,
        col0: ArrayList<String>,
        dag1: ArrayList<String>,
        col1: ArrayList<String>,
        col2: ArrayList<String>,
        dag2: ArrayList<String>,
        row1: ArrayList<String>,
        row2: ArrayList<String>
    ) {
        if (it.rowNum!! == 1 && it.col!! == 1 && it.player != null) {
            row0[0] = it.player!!
            col0[0] = it.player!!
            dag1[0] = it.player!!
        } else if (it.rowNum!! == 1 && it.col!! == 2 && it.player != null) {
            row0[1] = it.player!!
            col1[0] = it.player!!
        } else if (it.rowNum!! == 1 && it.col!! == 3 && it.player != null) {
            row0[2] = it.player!!
            col2[0] = it.player!!
            dag2[0] = it.player!!
        } else if (it.rowNum!! == 2 && it.col!! == 1 && it.player != null) {
            row1[0] = it.player!!
            col0[1] = it.player!!
        } else if (it.rowNum!! == 2 && it.col!! == 2 && it.player != null) {
            row1[1] = it.player!!
            col1[1] = it.player!!
            dag1[1] = it.player!!
            dag2[1] = it.player!!
        } else if (it.rowNum!! == 2 && it.col!! == 3 && it.player != null) {
            row1[2] = it.player!!
            col2[1] = it.player!!
        } else if (it.rowNum!! == 3 && it.col!! == 1 && it.player != null) {
            row2[0] = it.player!!
            col0[2] = it.player!!
            dag2[2] = it.player!!
        } else if (it.rowNum!! == 3 && it.col!! == 2 && it.player != null) {
            row2[1] = it.player!!
            col1[2] = it.player!!
        } else if (it.rowNum!! == 3 && it.col!! == 3 && it.player != null) {
            row2[2] = it.player!!
            col2[2] = it.player!!
            dag1[2] = it.player!!
        }
    }

    private fun evaluateWin(
        row0: ArrayList<String>,
        player: String,
        game: GameDto,
        row1: ArrayList<String>,
        row2: ArrayList<String>,
        col0: ArrayList<String>,
        col1: ArrayList<String>,
        col2: ArrayList<String>,
        dag1: ArrayList<String>,
        dag2: ArrayList<String>,
        moves: MutableList<MoveDto>
    ) {
        when {
            isGameWon(row0, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isGameWon(row1, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isGameWon(row2, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isGameWon(col0, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isGameWon(col1, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isGameWon(col2, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isGameWon(dag1, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isGameWon(dag2, player = player) -> {
                game.state = State.COMPLETE.name
                game.winner = player
            }
            isBoardFull(moves) && game.winner == null -> {
                game.state = State.COMPLETE.name
                game.winner = null
            }
        }
    }

    private fun checkDuplicatePlayers(errors: BindException, players: List<String?>?) {
        players?.let {
            if (it.size != it.distinct().count()) {
                errors.addError(FieldError("", "Players", "must have unique names, duplicates found in $players"))
            }
        }
    }

    // row0 ["x", "x", "x"]

    // row1 ["o", ]
    private fun gameStatus(game: GameDto, row: Int, col: Int, playerName: String): GameDto {

        var currMove = MoveDto(gameId = game.gameId, player = playerName, type = Type.MOVE.name, rowNum = row, col = col, moveNumber = game.gameboard.moves.size + 1)

        val moves = gameroom.games[game.gameId]!!.gameboard.moves
        moves.add(currMove)

        gameResult(moves, playerName, game)

        return game
    }

    @PostMapping("/tictactoe")
    override fun registerGame(@Valid @RequestBody register: RegisterGameDto, errors: Errors): CreatedGameDto {
        InvalidRequest.check(errors)

        // check for duplicate players within one game
        val registerErrors = BindException(this, "")
        checkDuplicatePlayers(registerErrors, register.players)
        InvalidRequest.check(registerErrors)

        // Check no more than 2 players per game, game board dimensions valid
        if (register.players!!.size > 2 || register.rows!! > 3 || register.cols!! > 3) throw BadRequestException()

        val players = register.players!!
        val playsList = ArrayList<PlayerDto>()
        val gameId = UUID.randomUUID()

        var count = 1L
        for (player in players) {
            val player = PlayerDto(gameId = gameId, playerName = player)
            playsList.add(player)
            count ++
        }

        val gameboard = GameboardDto(gameId = gameId, players = playsList)

        val game = GameDto(
            gameId = gameId,
            cols = register.cols,
            rows = register.rows,
            gameboard = gameboard,
            state = State.IN_PROGRESS.name
        )

        val allGames = mutableMapOf<UUID, GameDto>()

        allGames.put(game.gameId!!, game)
        gameroom.games.getOrPut(gameId!!) { game }
        return CreatedGameDto(gameId = gameId)
    }

    @PostMapping("/tictactoe/{gameId}/{playerName}")
    override fun registerMove(@Valid @RequestBody registerMove: RegisterMoveDto, @PathVariable gameId: UUID, @PathVariable playerName: String): OutMoveDto {
        // Game Not found
        if (gameroom.games[gameId] == null) throw NotFoundException()

        val game = gameroom.games[gameId]

        // Player Not game
        val playersList = mutableListOf<String>()

        game!!.gameboard.players.map {
            playersList.add(it.playerName!!)
        }
        if (!playersList.contains(playerName)) throw NotFoundException()

        // Move is in bound r=<3, col=<3
        if (registerMove.col > 3 || registerMove.row > 3) throw BadRequestException()

        // Move Already done
        val movesList = mutableListOf<Map<Int, Int>>()

        game!!.gameboard.moves.map {
            val mutableMapOf = mutableMapOf<Int, Int>()
            mutableMapOf[it.rowNum!!] = it.col!!
            movesList.add(mutableMapOf)
        }

        val mutableMapOfCurrMove = mutableMapOf<Int, Int>()
        mutableMapOfCurrMove[registerMove.row] = registerMove.col

        val indexOf = movesList.contains(mutableMapOfCurrMove)

        // Spot occupied
        if (indexOf) throw BadRequestException()

        // Board full
        if (movesList.size >= 9) throw NotFoundException()

        // Can't play finished game
        if (game!!.state == State.COMPLETE.name) throw NotFoundException()

        // Can't take turns twice in a row
        if (game!!.gameboard.recentPlayer == playerName) throw BadRequestException()

        val gameStatus = gameStatus(game = game!!, playerName = playerName, row = registerMove.row, col = registerMove.col)

        // Remove previous game with same id and outdated data
        gameroom.games.remove(gameId)

        val allGames = mutableMapOf<UUID, GameDto>()

        // Pull down all the saved gamesm, inject the list with the current game with updated game data

        allGames[game.gameId!!] = gameStatus
        gameroom.games[gameId] = gameStatus

        return OutMoveDto(move = "${game.gameId!!}/moves/${game.gameboard.moves.size}")
    }

    @GetMapping("/tictactoe")
    override fun getGames(): GameIdsDto {
        val inProgessGames = mutableListOf<UUID>()
        gameroom.games.map {
            if (it.value.state == State.IN_PROGRESS.name) {
                inProgessGames.add(it.value.gameId!!)
            }
        }
        return GameIdsDto(inProgessGames)
    }

    @PutMapping("/tictactoe/{gameId}/{playerName}/quit")
    override fun quitGame(@PathVariable gameId: UUID, @PathVariable playerName: String): String {
        // Game Not found
        if (gameroom.games[gameId] == null) throw NotFoundException()

        val game = gameroom.games[gameId]!!

        // Player Not game
        val playersList = mutableListOf<String>()

        game.gameboard.players.map {
            playersList.add(it.playerName!!)
        }
        if (!playersList.contains(playerName)) throw NotFoundException()

        var currMove = MoveDto(gameId = game.gameId, player = playerName, type = Type.QUIT.name, moveNumber = game.gameboard.moves.size + 1)

        val moves = gameroom.games[game.gameId]!!.gameboard.moves
        moves.add(currMove)

        val nonQuitter = playersList.find { it != playerName }
        game.winner = nonQuitter
        game.state = State.COMPLETE.name

        return "Game forfeited by $playerName, winner by default is ${nonQuitter!!}"
    }

    @GetMapping("/tictactoe/{gameId}")
    override fun getGameById(@PathVariable gameId: UUID): OutGameByIdDto {
        if (gameroom.games[gameId] == null) throw NotFoundException()
        val game = gameroom.games[gameId]!!
        val playersList = mutableListOf<String>()

        game.gameboard.players.map {
            playersList.add(it.playerName!!)
        }
        if (game.state == State.COMPLETE.name) {
            return CompleteDto(players = playersList, state = game.state!!, winner = game.winner)
        }

        return InProgressDto(players = playersList, state = game.state!!)
    }

    @GetMapping("/tictactoe/{gameId}/moves")
    override fun getMovesById(@PathVariable gameId: UUID): MovesDto {
        if (gameroom.games[gameId] == null) throw NotFoundException()
        val outMoves = gameroom.games[gameId]!!.gameboard.moves.map {
            if (it.type!! == Type.MOVE.name) {
                PlayDto(type = it.type!!, player = it.player!!, row = it.rowNum!!, column = it.col!!)
            } else {
                QuitDto(type = it.type!!, player = it.player!!)
            }
        }.toMutableList()

        return MovesDto(outMoves)
    }

    @GetMapping("/tictactoe/{gameId}/moves/{moveNumber}")
    override fun getMoveDetail(@PathVariable gameId: UUID, @PathVariable moveNumber: Int): OutMovesDto {
        if (gameroom.games[gameId] == null) throw NotFoundException()

        val move = gameroom.games[gameId]!!.gameboard.moves.find {
            it.moveNumber == moveNumber
        } ?: throw NotFoundException()
        if (move.type == Type.MOVE.name) {
            return PlayDto(type = move.type!!, player = move.player!!, row = move.rowNum!!, column = move.col!!)
        }
        return QuitDto(type = move.type!!, player = move.player!!)
    }
}
