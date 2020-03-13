package com.ruben.domain.interactor.boarding

import com.ruben.domain.model.SaveUserRecord
import com.ruben.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 12/03/20.
 **/
class SaveUserUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

  fun saveUser(firstName: String, lastName: String, phoneNumber: String): Flow<SaveUserRecord?> {
    return signUpRepository.saveUserDetails(firstName, lastName, phoneNumber)
  }
}