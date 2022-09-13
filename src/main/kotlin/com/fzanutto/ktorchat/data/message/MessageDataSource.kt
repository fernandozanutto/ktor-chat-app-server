package com.fzanutto.ktorchat.data.message

import com.fzanutto.ktorchat.data.model.Message

interface MessageDataSource {
    suspend fun getAllMessages(): List<Message>
    suspend fun insertMessage(message: Message)
}