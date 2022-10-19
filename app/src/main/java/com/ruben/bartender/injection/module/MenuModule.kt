package com.ruben.bartender.injection.module

import com.ruben.bartender.data.repository.MenuRepositoryImpl
import com.ruben.bartender.domain.interactor.menu.BasicMenuUseCase
import com.ruben.bartender.domain.repository.MenuRepository
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