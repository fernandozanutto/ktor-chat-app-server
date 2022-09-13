package com.fzanutto.ktorchat.plugins

import com.fzanutto.ktorchat.room.RoomController
import com.fzanutto.ktorchat.routes.chatSocket
import com.fzanutto.ktorchat.routes.getAllMessages
import com.fzanutto.ktorchat.routes.getAllRooms
import com.fzanutto.ktorchat.routes.loginUser
import com.fzanutto.ktorchat.routes.signupUser
import com.fzanutto.ktorchat.user.UserRepository
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    val userRepository by inject<UserRepository>()
    install(Routing) {
        chatSocket(roomController, userRepository)
        getAllMessages(roomController)
        getAllRooms(roomController)
        signupUser(userRepository)
        loginUser(userRepository)
    }
}
