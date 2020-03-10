package com.ruben.bartender.injection.module

import com.ruben.data.repository.MenuRepositoryImpl
import com.ruben.domain.interactor.menu.BasicMenuUseCase
import com.ruben.domain.repository.MenuRepository
import dagger.Module
import dagger.Provides

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@Module
open class MenuModule {

  @Provides
  fun menuRepository(menuRepositoryImpl: MenuRepositoryImpl): MenuRepository = menuRepositoryImpl

  @Provides
  fun basicMenu(menuRepository: MenuRepository): BasicMenuUseCase =
    BasicMenuUseCase(menuRepository)
}