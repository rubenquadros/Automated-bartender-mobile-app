package com.ruben.domain.interactor.user

import com.ruben.domain.model.UserRecord
import com.ruben.domain.repository.UserRepository
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