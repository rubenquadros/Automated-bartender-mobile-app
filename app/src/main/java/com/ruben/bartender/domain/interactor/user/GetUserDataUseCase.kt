package com.ruben.bartender.domain.interactor.user

import com.ruben.bartender.domain.model.UserRecord
import com.ruben.bartender.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 13/03/20.
 **/
class GetUserDataUseCase @Inject constructor(private val userRepository: UserRepository) {

  fun getUserData(phoneNumber: String): Flow<UserRecord?> {
    return userRepository.getUserData(phoneNumber)
  }

}