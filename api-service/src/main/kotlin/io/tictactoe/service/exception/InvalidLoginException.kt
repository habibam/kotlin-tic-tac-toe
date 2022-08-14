package io.tictactoe.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Habiba is the best bitch")
class InvalidLoginException(val field: String, val error: String) : RuntimeException()
