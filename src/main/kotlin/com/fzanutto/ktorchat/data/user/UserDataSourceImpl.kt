package com.fzanutto.ktorchat.data.user

import org.litote.kmongo.coroutine.CoroutineDatabase

class UserDataSourceImpl(private val db: CoroutineDatabase) : UserDataSource {

    private val users = db.getCollection<UserDTO>()

    override suspend fun searchUser(username: String): UserDTO? {
        return users.find("{name:'$username'").first()
    }

    override suspend fun signupUser(newUser: UserDTO): Boolean {
        return users.insertOne(newUser).insertedId != null
    }
}