package com.fzanutto.ktorchat.data.message

import com.fzanutto.ktorchat.data.model.Message

class MessageDataSourceFake: MessageDataSource {

    private val messages = ArrayList<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages
            .sortedByDescending { it.timestamp }
    }

    override suspend fun insertMessage(message: Message) {
        messages.add(message)
    }
}