package com.fzanutto.ktorchat.routes

import com.fzanutto.ktorchat.data.user.UserDTO
import com.fzanutto.ktorchat.room.MemberAlreadyExistsException
import com.fzanutto.ktorchat.room.RoomController
import com.fzanutto.ktorchat.sessions.ChatSession
import com.fzanutto.ktorchat.user.User
import com.fzanutto.ktorchat.user.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(
    roomController: RoomController,
    userRepository: UserRepository
) {
    webSocket(path = "/chat-socket") {
        val session = call.sessions.get<ChatSession>()

        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }

        val username = session.username

        try {
            val user = userRepository.searchUser(username)
            if (user == null) {
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "User not found."))
                return@webSocket
            }
            user.sessionId = session.sessionId
            user.socket = this

            roomController.onJoin(user)

            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    roomController.sendMessage(
                        session.username,
                        frame.readText()
                    )
                }
            }
        } catch (e: MemberAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            roomController.tryDisconnect(session.username)
        }
    }
}

fun Route.getAllMessages(roomController: RoomController) {
    get("/messages") {
        call.respond(
            HttpStatusCode.OK,
            roomController.getAllMessages()
        )
    }
}

fun Route.getAllRooms(roomController: RoomController) {
    get("/rooms") {
        call.respond(
            HttpStatusCode.OK,
            roomController.getAllRooms()
        )
    }
}

fun Route.loginUser(userRepository: UserRepository) {
    get("/login") {
        val result = call.parameters.let {
            val username = it.get("username") ?: return@let false
            return@let userRepository.searchUser(username)
        }

        println("Login result $result")
        if (result != null) {
            call.respond(
                HttpStatusCode.OK,
                result
            )
        } else {
            call.respond(
                HttpStatusCode.BadRequest,
                "No user found"
            )
        }
    }
}

fun Route.signupUser(userRepository: UserRepository) {
    get("/signup") {
        val result = call.parameters.let {
            val username = it.get("username") ?: return@let false
            val bio = it.get("bio") ?: ""

            val newUser = UserDTO(
                username = username,
                bio = bio
            )
            println("Sign up user $newUser")
            return@let userRepository.signupUser(newUser)
        }

        println("Sign up result $result")
        if (result) {
            call.respond(
                HttpStatusCode.OK,
                true
            )
        } else {
            call.respond(
                HttpStatusCode.BadRequest,
                false
            )
        }
    }
}
