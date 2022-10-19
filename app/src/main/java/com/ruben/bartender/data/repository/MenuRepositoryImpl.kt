package com.ruben.bartender.data.repository

import com.ruben.bartender.data.DataSource
import com.ruben.bartender.domain.model.BasicMenuRecord
import com.ruben.bartender.domain.model.CategoryRecord
import com.ruben.bartender.domain.repository.MenuRepository
import com.ruben.bartender.data.mapper.MenuMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
class MenuRepositoryImpl @Inject constructor(dataSource: DataSource): MenuRepository {

  private val firebaseApi = dataSource.api().firebaseApiHandler()
  private val menuMapper = MenuMapper()

  override fun getBasicMenu(): Flow<BasicMenuRecord?> {
    return firebaseApi.getBasicMenu().map {
      menuMapper.mapBasicMenu(it)
    }
  }

  override fun getMenuCategories(): Flow<CategoryRecord?> {
    return firebaseApi.getMenuCategories().map {
      menuMapper.mapCategories(it)
    }
  }

}