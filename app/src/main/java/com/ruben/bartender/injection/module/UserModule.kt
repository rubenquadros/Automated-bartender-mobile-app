package com.ruben.bartender.injection.module

import com.ruben.bartender.data.repository.UserRepositoryImpl
import com.ruben.bartender.domain.interactor.user.GetUserDataUseCase
import com.ruben.bartender.domain.interactor.user.SignOutUseCase
import com.ruben.bartender.domain.interactor.user.UserHandler
import com.ruben.bartender.domain.interactor.user.UserHandlerImpl
import com.ruben.bartender.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

/**
 * Created by ruben.quadros on 10/03/20.
 **/
@Module
class UserModule {

  @Provides
  fun provideRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository = userRepositoryImpl

  @Provides
  fun userHandler(userHandlerImpl: UserHandlerImpl): UserHandler = userHandlerImpl

  @Provides
  fun userData(userRepository: UserRepository): GetUserDataUseCase = GetUserDataUseCase(userRepository)

  @Provides
  fun logout(userRepository: UserRepository): SignOutUseCase = SignOutUseCase(userRepository)
}