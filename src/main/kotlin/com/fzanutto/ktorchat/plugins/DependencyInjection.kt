package com.fzanutto.ktorchat.plugins

import com.fzanutto.ktorchat.di.mainModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin


fun Application.configureDI() {
    install(Koin) {
        modules(mainModule)
    }
}