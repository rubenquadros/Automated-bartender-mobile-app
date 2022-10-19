package com.ruben.bartender.domain.interactor.user

import com.ruben.bartender.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class UserHandlerImpl @Inject constructor(private val userRepository: UserRepository) : UserHandler {
  override fun isLoggedIn(): Boolean {
    return userRepository.isLoggedIn()
  }

  override fun isRegistered(): Boolean {
    return userRepository.isRegistered()
  }

  override fun phoneNumber(): String? {
    return userRepository.phoneNumber()
  }
}