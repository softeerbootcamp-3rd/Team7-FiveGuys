package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.login.response.LoginResponse
import org.softeer.robocar.data.model.User

fun LoginResponse.toUser(): User {
    return User(
        userId = userId,
        nickname = nickname,
    )
}