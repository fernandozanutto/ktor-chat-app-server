package com.example.di

import com.example.data.MessageDataSource
import com.example.data.MessageDataSourceImpl
import com.example.data.MessageDataSourceMockImpl
import com.example.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase("message_db")
    }

    single<MessageDataSource> {
        MessageDataSourceMockImpl()
    }

    single {
        RoomController(get())
    }
}