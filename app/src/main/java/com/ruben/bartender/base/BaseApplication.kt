package com.ruben.bartender.base

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@HiltAndroidApp
open class BaseApplication: Application() {

  @Inject
  protected lateinit var appLifecycle: AppLifecycle

  override fun onCreate() {
    super.onCreate()
    ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycle)
  }
}