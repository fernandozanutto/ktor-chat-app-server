package com.fzanutto.ktorchat.plugins

import com.fzanutto.ktorchat.sessions.ChatSession
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }

    intercept(Plugins) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"] ?: "GUEST"

            call.sessions.set(ChatSession(username, generateNonce()))
        }
    }
}
