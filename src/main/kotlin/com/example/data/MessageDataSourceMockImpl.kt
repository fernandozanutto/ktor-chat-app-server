package com.example.data

import com.example.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageDataSourceMockImpl: MessageDataSource {

    private val messages = ArrayList<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages
            .sortedByDescending { it.timestamp }
    }

    override suspend fun insertMessage(message: Message) {
        messages.add(message)
    }
}