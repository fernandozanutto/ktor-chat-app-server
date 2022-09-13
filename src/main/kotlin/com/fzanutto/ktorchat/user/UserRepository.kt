package com.fzanutto.ktorchat.user

import com.fzanutto.ktorchat.data.user.UserDTO
import com.fzanutto.ktorchat.data.user.UserDataSource

class UserRepository(
    private val userDataSource: UserDataSource
) {
    suspend fun searchUser(username: String): User? {
        return userDataSource.searchUser(username)?.toUser()
    }
    suspend fun signupUser(user: UserDTO): Boolean {
        return userDataSource.signupUser(user)
    }
}