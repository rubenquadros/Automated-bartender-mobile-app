package com.ruben.bartender.domain.repository

import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.MainMenuRecord
import com.ruben.bartender.domain.record.CategoryRecord
import com.ruben.bartender.domain.record.DrinkDetailsRecord
import com.ruben.bartender.domain.record.ErrorRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface MenuRepository {
  suspend fun getMainMenu(): BaseRecord<MainMenuRecord, ErrorRecord>
  suspend fun getDrinkDetails(drinkId: String): BaseRecord<DrinkDetailsRecord, ErrorRecord>
  fun getMenuCategories(): Flow<CategoryRecord?>
}