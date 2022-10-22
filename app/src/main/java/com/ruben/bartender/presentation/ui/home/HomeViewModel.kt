package com.ruben.bartender.presentation.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.bartender.domain.interactor.drink.MakeDrinkUseCase
import com.ruben.bartender.domain.interactor.menu.BasicMenuUseCase
import com.ruben.bartender.domain.interactor.user.GetUserDataUseCase
import com.ruben.bartender.domain.interactor.user.SignOutUseCase
import com.ruben.bartender.domain.model.BasicMenuRecord
import com.ruben.bartender.domain.model.CategoryRecord
import com.ruben.bartender.domain.model.MakeDrinkRecord
import com.ruben.bartender.domain.model.SignOutRecord
import com.ruben.bartender.domain.model.UserRecord
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
  private val makeDrinkUseCase: MakeDrinkUseCase,
  private val getUserDataUseCase: GetUserDataUseCase,
  private val signOutUseCase: SignOutUseCase
) :
  ViewModel(), LifecycleObserver {

  private var basicMenuResponse: MutableLiveData<BasicMenuRecord?> = MutableLiveData()
  private var menuCategories: MutableLiveData<CategoryRecord?> = MutableLiveData()
  private var makeDrinkResponse: MutableLiveData<MakeDrinkRecord?> = MutableLiveData()
  private var userDataResponse: MutableLiveData<UserRecord?> = MutableLiveData()
  private var signoutResponse: MutableLiveData<SignOutRecord?> = MutableLiveData()

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

  fun retrieveUserData(phoneNumber: String) {
    viewModelScope.launch {
      getUserDataUseCase.getUserData(phoneNumber).flowOn(Dispatchers.IO)
        .collect{
          userDataResponse.postValue(it)
        }
    }
  }

  fun logout() {
    viewModelScope.launch {
      signOutUseCase.logout().flowOn(Dispatchers.IO)
        .collect {
          signoutResponse.postValue(it)
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

  fun getUserDataResponse(): LiveData<UserRecord?> {
    return userDataResponse
  }

  fun observeMakeDrink(): LiveData<MakeDrinkRecord?> {
    return makeDrinkResponse
  }

  fun getSignOutResponse(): LiveData<SignOutRecord?> {
    return signoutResponse
  }
}