package com.ruben.domain.repository

import com.ruben.domain.model.BasicMenuRecord
import com.ruben.domain.model.CategoryRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface MenuRepository {
  fun getBasicMenu(): Flow<BasicMenuRecord?>
  fun getMenuCategories(): Flow<CategoryRecord?>
}