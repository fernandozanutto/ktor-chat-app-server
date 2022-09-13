package com.fzanutto.ktorchat.data.room

import com.fzanutto.ktorchat.room.Room

interface RoomDataSource {
    suspend fun getAllRooms(): List<Room>
    suspend fun insertRoom(room: Room): Boolean
    suspend fun deleteRoom(roomId: String): Boolean
}