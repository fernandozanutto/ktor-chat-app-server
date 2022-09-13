package com.fzanutto.ktorchat.room

import com.fzanutto.ktorchat.data.model.Message
import com.fzanutto.ktorchat.user.User

data class Room(
    val id: String,
    val users: List<User>,
    val messages: List<Message>
)
