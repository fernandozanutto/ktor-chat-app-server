package com.fzanutto.ktorchat.data.user

import com.fzanutto.ktorchat.user.User
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserDTO(
    val username: String,
    val bio: String = "",
    val image: String = "",
    @BsonId
    val id: String = ObjectId().toString()
) {
    fun toUser(): User {
        return User(
            username,
            bio,
            image
        )
    }
}
