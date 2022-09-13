package com.fzanutto.ktorchat.data.room

import com.fzanutto.ktorchat.room.Room

class RoomDataSourceFake : RoomDataSource {

    val rooms = ArrayList<Room>()

    override suspend fun getAllRooms(): List<Room> {
        return rooms.toList()
    }

    override suspend fun insertRoom(room: Room): Boolean {
        return if (rooms.any { it.id == room.id }) {
            false
        } else {
            rooms.add(room)
            true
        }
    }

    override suspend fun deleteRoom(roomId: String): Boolean {
        return rooms.removeIf { it.id == roomId }
    }
}