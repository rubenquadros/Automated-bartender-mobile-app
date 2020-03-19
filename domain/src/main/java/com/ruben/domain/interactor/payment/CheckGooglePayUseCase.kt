package com.ruben.domain.interactor.payment

import com.ruben.domain.model.CheckGooglePayRecord
import com.ruben.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/03/20.
 **/
class CheckGooglePayUseCase @Inject constructor(private val paymentRepository: PaymentRepository) {

  fun checkGooglePayAvailability(
    type: String,
    allowedCards: List<String>,
    allowedAuth: List<String>,
    apiVersion: Int,
    apiMinorVersion: Int
  ): Flow<CheckGooglePayRecord?> {
    return paymentRepository.checkGooglePayAvailability(
      type,
      allowedCards,
      allowedAuth,
      apiVersion,
      apiMinorVersion
    )
  }
}