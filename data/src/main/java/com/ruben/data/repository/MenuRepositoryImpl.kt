package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.domain.repository.MenuRepository
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
class MenuRepositoryImpl @Inject constructor(dataSource: DataSource): MenuRepository {

  private val firebaseApi = dataSource.api().firebaseApiHandler()

  override fun getBasicMenu(): Flow<BasicMenuResponse?> {
    return firebaseApi.getBasicMenu()
  }

  override fun getMenuCategories(): Flow<CategoryResponse?> {
    return firebaseApi.getMenuCategories()
  }

}