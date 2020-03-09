package com.ruben.bartender.injection.module

import com.ruben.bartender.injection.scopes.FragmentScoped
import com.ruben.bartender.presentation.onboarding.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ruben.quadros on 09/03/20.
 **/
@Module
interface OnBoardingFragmentBuilder {

  @FragmentScoped
  @ContributesAndroidInjector
  fun bindLoginFragment(): LoginFragment

}