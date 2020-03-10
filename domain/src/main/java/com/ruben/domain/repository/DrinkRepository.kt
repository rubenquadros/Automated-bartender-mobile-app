package com.ruben.domain.repository

import com.ruben.domain.model.MakeDrinkRecord

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@Suppress("DeferredIsResult")
interface DrinkRepository {
  suspend fun makeDrink(drinkName: String): MakeDrinkRecord?
}