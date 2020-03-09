package com.ruben.bartender.injection.module

import com.ruben.bartender.injection.scopes.FragmentScoped
import com.ruben.bartender.presentation.onboarding.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by ruben.quadros on 09/03/20.
 **/
@ExperimentalCoroutinesApi
@Module
interface OnBoardingFragmentBuilder {

  @FragmentScoped
  @ContributesAndroidInjector
  fun bindLoginFragment(): LoginFragment

}