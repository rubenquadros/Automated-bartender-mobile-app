package com.ruben.bartender.domain.repository

import com.ruben.bartender.domain.record.BasicMenuRecord
import com.ruben.bartender.domain.record.CategoryRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface MenuRepository {
  fun getBasicMenu(): Flow<BasicMenuRecord?>
  fun getMenuCategories(): Flow<CategoryRecord?>
}