package com.ruben.bartender.injection.module

import android.content.Context
import com.ruben.bartender.base.BaseApplication
import com.ruben.bartender.injection.scopes.ApplicationScoped
import com.ruben.data.DataSource
import com.ruben.data.DataSourceImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@ExperimentalCoroutinesApi
@Module
open class AppModule {

  @ApplicationScoped
  @Provides
  fun providesApplication(baseApplication: BaseApplication): Context = baseApplication

  @ApplicationScoped
  @Provides
  fun provideDataSource(context: Context): DataSource {
    return DataSourceImpl(context)
  }

}