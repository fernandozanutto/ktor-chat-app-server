package com.fzanutto.ktorchat.room

class MemberAlreadyExistsException: Exception(
    "There is alreay a member with that username in the room."
)