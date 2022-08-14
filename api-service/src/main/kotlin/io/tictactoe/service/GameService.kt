package io.tictactoe.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GameService(
    @Value("\${jwt.secret}") val jwtSecret: String,
    @Value("\${jwt.issuer}") val jwtIssuer: String
)
