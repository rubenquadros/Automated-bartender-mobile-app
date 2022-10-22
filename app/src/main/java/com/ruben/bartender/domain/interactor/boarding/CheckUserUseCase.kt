package com.ruben.bartender.domain.interactor.boarding

import com.ruben.bartender.domain.record.CheckUserRecord
import com.ruben.bartender.domain.repository.OnBoardingRepository
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