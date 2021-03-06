package com.ruben.bartender.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ruben.bartender.injection.BartenderViewModelFactory
import com.ruben.bartender.injection.ViewModelKey
import com.ruben.bartender.presentation.home.HomeViewModel
import com.ruben.bartender.presentation.onboarding.login.LoginViewModel
import com.ruben.bartender.presentation.onboarding.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@ExperimentalCoroutinesApi
@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindViewModelFactory(factory: BartenderViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  internal abstract fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(SignUpViewModel::class)
  internal abstract fun signUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(HomeViewModel::class)
  internal abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel
}