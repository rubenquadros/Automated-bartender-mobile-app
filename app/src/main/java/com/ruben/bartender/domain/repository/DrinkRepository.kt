package com.ruben.bartender.domain.repository

import com.ruben.bartender.domain.model.MakeDrinkRecord


/**
 * Created by ruben.quadros on 05/03/20.
 **/
interface DrinkRepository {
  suspend fun makeDrink(drinkName: String): MakeDrinkRecord?
}