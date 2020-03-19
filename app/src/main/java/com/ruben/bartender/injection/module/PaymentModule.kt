package com.ruben.bartender.injection.module

import com.ruben.data.repository.PaymentRepositoryImpl
import com.ruben.domain.interactor.payment.CheckGooglePayUseCase
import com.ruben.domain.repository.PaymentRepository
import dagger.Module
import dagger.Provides

/**
 * Created by ruben.quadros on 19/03/20.
 **/
@Module
class PaymentModule {

  @Provides
  fun provideRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository = paymentRepositoryImpl

  @Provides
  fun checkGooglePay(paymentRepository: PaymentRepository): CheckGooglePayUseCase = CheckGooglePayUseCase(paymentRepository)
}