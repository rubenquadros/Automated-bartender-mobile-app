package com.ruben.domain.interactor.boarding

import com.ruben.domain.model.CheckUserRecord
import com.ruben.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 16/03/20.
 **/
class CheckUserUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) {

  fun checkIfUserExists(phoneNumber: String): Flow<CheckUserRecord?> {
    return onBoardingRepository.checkIfUserExists(phoneNumber)
  }

}