package com.ruben.bartender.presentation.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.domain.interactor.BasicMenuUseCase
import com.ruben.domain.interactor.MakeDrinkUseCase
import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
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

  private var basicMenuResponse: MutableLiveData<BasicMenuResponse?> = MutableLiveData()
  private var menuCategories: MutableLiveData<CategoryResponse?> = MutableLiveData()
  private var makeDrinkResponse: MutableLiveData<MakeDrinkResponse?> = MutableLiveData()

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
      val response = makeDrinkUseCase.makeDrink(MakeDrinkRequest(name)).await()
      makeDrinkResponse.postValue(response)
    }
  }

  fun getBasicMenu(): LiveData<BasicMenuResponse?> {
    return basicMenuResponse
  }

  fun getMenuCategories(): LiveData<CategoryResponse?> {
    return menuCategories
  }

  fun observeMakeDrink(): LiveData<MakeDrinkResponse?> {
    return makeDrinkResponse
  }
}