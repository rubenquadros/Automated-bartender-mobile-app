package com.ruben.bartender.injection.component

import com.ruben.bartender.base.BaseApplication
import com.ruben.bartender.injection.module.ActivityBuilder
import com.ruben.bartender.injection.module.AppModule
import com.ruben.bartender.injection.scopes.ApplicationScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by ruben.quadros on 28/02/20.
 **/
@ExperimentalCoroutinesApi
@ApplicationScoped
@Component(modules = [AppModule::class, ActivityBuilder::class, AndroidInjectionModule::class])
interface AppComponent {

  fun inject(application: BaseApplication)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: BaseApplication): Builder

    fun build(): AppComponent
  }
}