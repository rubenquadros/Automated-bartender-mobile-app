package com.ruben.bartender.domain.interactor.menu

import com.ruben.bartender.domain.model.BasicMenuRecord
import com.ruben.bartender.domain.model.CategoryRecord
import com.ruben.bartender.domain.repository.MenuRepository
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