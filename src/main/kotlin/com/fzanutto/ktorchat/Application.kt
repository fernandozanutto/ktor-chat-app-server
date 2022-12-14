package com.fzanutto.ktorchat

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.fzanutto.ktorchat.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureDI()
        configureMonitoring()
        configureSerialization()
        configureSecurity()
        configureSockets()
        configureRouting()
    }.start(wait = true)
}
