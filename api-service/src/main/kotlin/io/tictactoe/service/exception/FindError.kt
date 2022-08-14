package io.tictactoe.service.exception

sealed class FindError(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    class NotFound(message: String? = null, cause: Throwable? = null) : FindError(message, cause)
    class Unexpected(message: String? = null, cause: Throwable? = null) : FindError(message, cause)
}