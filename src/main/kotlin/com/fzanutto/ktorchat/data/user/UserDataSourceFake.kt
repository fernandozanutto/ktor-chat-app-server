package com.fzanutto.ktorchat.data.user

class UserDataSourceFake: UserDataSource {

    private val users = ArrayList<UserDTO>()

    override suspend fun searchUser(username: String): UserDTO? {
        return users.firstOrNull {
            it.username == username
        }
    }

    override suspend fun signupUser(newUser: UserDTO): Boolean {
        return if (users.any { it.username == newUser.username }) {
            false
        } else {
            users.add(newUser)
            true
        }
    }
}