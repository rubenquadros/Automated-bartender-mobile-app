package com.ruben.bartender.presentation.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.domain.interactor.drink.MakeDrinkUseCase
import com.ruben.domain.interactor.menu.BasicMenuUseCase
import com.ruben.domain.model.BasicMenuRecord
import com.ruben.domain.model.CategoryRecord
import com.ruben.domain.model.MakeDrinkRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(
  private val basicMenuUseCase: BasicMenuUseCase,
  private val makeDrinkUseCase: MakeDrinkUseCase
) :
  ViewModel(), LifecycleObserver {

  private var basicMenuResponse: MutableLiveData<BasicMenuRecord?> = MutableLiveData()
  private var menuCategories: MutableLiveData<CategoryRecord?> = MutableLiveData()
  private var makeDrinkResponse: MutableLiveData<MakeDrinkRecord?> = MutableLiveData()

  fun retrieveBasicMenu() {
    viewModelScope.launch {
      basicMenuUseCase.getBasicMenu().flowOn(Dispatchers.IO)
        .collect {
          basicMenuResponse.postValue(it)
        }
    }
  }

  fun retrieveCategories() {
    viewModelScope.launch {
      basicMenuUseCase.getMenuCategories().flowOn(Dispatchers.IO)
        .collect {
          menuCategories.postValue(it)
        }
    }
  }

  fun makeDrink(name: String) {
    viewModelScope.launch {
      val response = makeDrinkUseCase.makeDrink(name)
      makeDrinkResponse.postValue(response)
    }
  }

  fun getBasicMenu(): LiveData<BasicMenuRecord?> {
    return basicMenuResponse
  }

  fun getMenuCategories(): LiveData<CategoryRecord?> {
    return menuCategories
  }

  fun observeMakeDrink(): LiveData<MakeDrinkRecord?> {
    return makeDrinkResponse
  }
}