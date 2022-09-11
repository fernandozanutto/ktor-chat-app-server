package com.example.plugins

import com.example.di.mainModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin


fun Application.configureDI() {
    install(Koin) {
        modules(mainModule)
    }
}