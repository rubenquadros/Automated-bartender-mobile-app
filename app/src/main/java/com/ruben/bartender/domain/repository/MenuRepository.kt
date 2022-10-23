package com.ruben.bartender.domain.repository

import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.MainMenuRecord
import com.ruben.bartender.domain.record.CategoryRecord
import com.ruben.bartender.domain.record.ErrorRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface MenuRepository {
  suspend fun getMainMenu(): Flow<BaseRecord<MainMenuRecord, ErrorRecord>>
  fun getMenuCategories(): Flow<CategoryRecord?>
}