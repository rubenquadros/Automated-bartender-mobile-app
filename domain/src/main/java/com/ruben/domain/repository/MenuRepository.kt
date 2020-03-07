package com.ruben.domain.repository

import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface MenuRepository {
  fun getBasicMenu(): Flow<BasicMenuResponse?>
  fun getMenuCategories(): Flow<CategoryResponse?>
}