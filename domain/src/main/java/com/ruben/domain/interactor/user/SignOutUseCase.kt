package com.ruben.domain.interactor.user

import com.ruben.domain.model.SignOutRecord
import com.ruben.domain.repository.UserRepository
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