package com.fzanutto.ktorchat.user

import io.ktor.websocket.WebSocketSession
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val bio: String = "",
    val image: String = "",
    var sessionId: String? = null,
    var socket: WebSocketSession? = null,
)
