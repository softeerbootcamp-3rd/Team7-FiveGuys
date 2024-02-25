package org.softeer.robocar.ui.viewmodel

import org.softeer.robocar.data.repository.user.UserRepository
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
) {
}