package com.ruben.bartender.domain.interactor.drink

import com.ruben.bartender.domain.record.MakeDrinkRecord
import com.ruben.bartender.domain.repository.DrinkRepository
import javax.inject.Inject

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@Suppress("DeferredIsResult")
class MakeDrinkUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {

  suspend fun makeDrink(drinkName: String): MakeDrinkRecord? {
    return drinkRepository.makeDrink(drinkName)
  }
}