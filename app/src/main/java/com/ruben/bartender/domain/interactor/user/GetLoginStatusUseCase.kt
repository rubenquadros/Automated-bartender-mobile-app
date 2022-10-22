package com.ruben.bartender.domain.interactor.user

import com.ruben.bartender.domain.BasePreferenceUseCase
import com.ruben.bartender.domain.record.UserLoginStatus
import com.ruben.bartender.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Ruben Quadros on 22/10/22
 **/
class GetLoginStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
): BasePreferenceUseCase<UserLoginStatus>() {

    override suspend fun execute(): UserLoginStatus {
        return userRepository.isLoggedIn()
    }
}