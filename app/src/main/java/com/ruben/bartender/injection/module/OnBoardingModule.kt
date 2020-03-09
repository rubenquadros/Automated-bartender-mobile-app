package com.ruben.bartender.injection.module

import com.ruben.data.repository.OnBoardingRepositoryImpl
import com.ruben.domain.interactor.OnBoardingUseCase
import com.ruben.domain.repository.OnBoardingRepository
import dagger.Module
import dagger.Provides

/**
 * Created by ruben.quadros on 09/03/20.
 **/
@Module
class OnBoardingModule {

  @Provides
  fun onBoardingRepository(onBoardingRepositoryImpl: OnBoardingRepositoryImpl): OnBoardingRepository = onBoardingRepositoryImpl

  @Provides
  fun login(onBoardingRepository: OnBoardingRepository) = OnBoardingUseCase(onBoardingRepository)
}