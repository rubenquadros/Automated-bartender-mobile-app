package com.ruben.bartender.injection.module

import com.ruben.bartender.data.repository.OnBoardingRepositoryImpl
import com.ruben.bartender.data.repository.SignUpRepositoryImpl
import com.ruben.bartender.domain.interactor.boarding.CheckUserUseCase
import com.ruben.bartender.domain.interactor.boarding.OnBoardingUseCase
import com.ruben.bartender.domain.interactor.boarding.SignUpUseCase
import com.ruben.bartender.domain.repository.OnBoardingRepository
import com.ruben.bartender.domain.repository.SignUpRepository
import dagger.Module
import dagger.Provides

/**
 * Created by ruben.quadros on 09/03/20.
 **/
@Module
class OnBoardingModule {

  @Provides
  fun onBoardingRepository(onBoardingRepositoryImpl: OnBoardingRepositoryImpl): OnBoardingRepository =
    onBoardingRepositoryImpl

  @Provides
  fun login(onBoardingRepository: OnBoardingRepository) = OnBoardingUseCase(onBoardingRepository)

  @Provides
  fun signUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository =
    signUpRepositoryImpl

  @Provides
  fun signUp(signUpRepository: SignUpRepository) = SignUpUseCase(signUpRepository)

  @Provides
  fun checkUser(onBoardingRepository: OnBoardingRepository) = CheckUserUseCase(onBoardingRepository)
}