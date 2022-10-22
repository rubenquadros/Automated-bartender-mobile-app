package com.ruben.bartender.domain.interactor.boarding

import com.ruben.bartender.domain.record.SaveUserRecord
import com.ruben.bartender.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 12/03/20.
 **/
class SignUpUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

  fun saveUser(firstName: String, lastName: String, phoneNumber: String): Flow<SaveUserRecord?> {
    return signUpRepository.saveUserDetails(firstName, lastName, phoneNumber)
  }
}