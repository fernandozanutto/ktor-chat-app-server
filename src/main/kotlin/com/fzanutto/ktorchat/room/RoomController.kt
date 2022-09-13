package com.fzanutto.ktorchat.room

import com.fzanutto.ktorchat.data.message.MessageDataSource
import com.fzanutto.ktorchat.data.model.Message
import com.fzanutto.ktorchat.data.room.RoomDataSource
import com.fzanutto.ktorchat.user.User
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource,
    private val roomDataSource: RoomDataSource
) {
    private val members = ConcurrentHashMap<String, User>()

    fun onJoin(
        user: User
    ) {
        if (members.containsKey(user.username)) {
            throw MemberAlreadyExistsException()
        }

        println("User joined ${user.username}")
        members[user.username] = User(
            username = user.username,
            sessionId = user.sessionId,
            socket = user.socket
        )
    }

    suspend fun sendMessage(senderUsername: String, message: String) {
        val messageEntity = Message(
            text = message,
            username = senderUsername,
            timestamp = System.currentTimeMillis()
        )
        println("Message sent {$messageEntity}")

        members.values.forEach { member ->
            messageDataSource.insertMessage(messageEntity)
            val parsedMessage = Json.encodeToString(messageEntity)
            member.socket?.send(Frame.Text(parsedMessage))
        }
    }


    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username)) {
            println("User {$username} disconnected")
            members.remove(username)
        }
    }

    fun getAllRooms(): List<Room> {
        return listOf()
    }
}