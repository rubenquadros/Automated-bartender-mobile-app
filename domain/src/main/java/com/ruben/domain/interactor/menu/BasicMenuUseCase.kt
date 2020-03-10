package com.ruben.domain.interactor.menu

import com.ruben.domain.model.BasicMenuRecord
import com.ruben.domain.model.CategoryRecord
import com.ruben.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
class BasicMenuUseCase @Inject constructor(private val menuRepository: MenuRepository) {

  fun getBasicMenu(): Flow<BasicMenuRecord?> {
    return menuRepository.getBasicMenu()
  }

  fun getMenuCategories(): Flow<CategoryRecord?> {
    return menuRepository.getMenuCategories()
  }

}