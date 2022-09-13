package com.fzanutto.ktorchat.di

import com.fzanutto.ktorchat.data.message.MessageDataSource
import com.fzanutto.ktorchat.data.message.MessageDataSourceFake
import com.fzanutto.ktorchat.data.room.RoomDataSource
import com.fzanutto.ktorchat.data.room.RoomDataSourceFake
import com.fzanutto.ktorchat.data.user.UserDataSource
import com.fzanutto.ktorchat.data.user.UserDataSourceFake
import com.fzanutto.ktorchat.room.RoomController
import com.fzanutto.ktorchat.user.UserRepository
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase("message_db")
    }

    single<MessageDataSource> {
        MessageDataSourceFake()
    }

    single<RoomDataSource> {
        RoomDataSourceFake()
    }

    single {
        RoomController(get(), get())
    }

    single<UserDataSource> {
        UserDataSourceFake()
    }

    single {
        UserRepository(get())
    }
}