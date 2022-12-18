package com.ruben.bartender.domain.repository

import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MakeDrinkRecord


/**
 * Created by ruben.quadros on 05/03/20.
 **/
interface DrinkRepository {
  suspend fun makeDrink(drinkName: String): BaseRecord<MakeDrinkRecord, ErrorRecord>
}