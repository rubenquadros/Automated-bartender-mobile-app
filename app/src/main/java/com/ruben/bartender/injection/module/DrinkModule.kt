package com.ruben.bartender.injection.module

import com.ruben.data.repository.DrinkRepositoryImpl
import com.ruben.domain.interactor.MakeDrinkUseCase
import com.ruben.domain.repository.DrinkRepository
import dagger.Module
import dagger.Provides

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@Module
open class DrinkModule {

  @Provides
  fun drinkRepository(drinkRepositoryImpl: DrinkRepositoryImpl): DrinkRepository = drinkRepositoryImpl

  @Provides
  fun makeDrink(drinkRepository: DrinkRepository): MakeDrinkUseCase = MakeDrinkUseCase(drinkRepository)

}