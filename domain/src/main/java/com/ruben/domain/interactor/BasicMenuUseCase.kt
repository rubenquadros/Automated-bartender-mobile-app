package com.ruben.domain.interactor

import com.ruben.domain.repository.MenuRepository
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
class BasicMenuUseCase @Inject constructor(private val menuRepository: MenuRepository) {

  fun getBasicMenu(): Flow<BasicMenuResponse?> {
    return menuRepository.getBasicMenu()
  }

  fun getMenuCategories(): Flow<CategoryResponse?> {
    return menuRepository.getMenuCategories()
  }

}