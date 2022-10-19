package com.ruben.bartender.domain.interactor.user

import com.ruben.bartender.domain.model.SignOutRecord
import com.ruben.bartender.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 14/03/20.
 **/
class SignOutUseCase @Inject constructor(private val userRepository: UserRepository) {

  fun logout(): Flow<SignOutRecord?> {
    return userRepository.logout()
  }

}