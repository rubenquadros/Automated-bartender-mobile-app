package com.ruben.bartender.injection.module

import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.injection.scopes.ActivityScoped
import com.ruben.bartender.presentation.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@ExperimentalCoroutinesApi
@Module(includes = [ViewModelModule::class, MenuModule::class, DrinkModule::class])
abstract class ActivityBuilder {

  @ActivityScoped
  @ContributesAndroidInjector
  abstract fun baseActivity(): BaseActivity

  @ActivityScoped
  @ContributesAndroidInjector
  abstract fun homeActivity(): HomeActivity
}