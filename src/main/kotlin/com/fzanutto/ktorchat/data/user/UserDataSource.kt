package com.fzanutto.ktorchat.data.user

interface UserDataSource {
    suspend fun searchUser(username: String): UserDTO?
    suspend fun signupUser(newUser: UserDTO): Boolean
}