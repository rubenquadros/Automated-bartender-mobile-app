package com.ruben.bartender.base

import android.app.Application
import android.content.Intent
import android.util.Log
import com.ruben.bartender.R
import com.ruben.bartender.injection.component.DaggerAppComponent
import com.ruben.bartender.presentation.home.HomeActivity
import com.ruben.bartender.utils.ApplicationUtility
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@Suppress("PrivatePropertyName")
@ExperimentalCoroutinesApi
open class BaseApplication: Application(), HasAndroidInjector {

  @Inject
  lateinit var androidInjector : DispatchingAndroidInjector<Any>

  private val TAG = javaClass.simpleName

  override fun onCreate() {
    super.onCreate()
    this.initDagger()
    Thread.setDefaultUncaughtExceptionHandler { _, e -> handleUncaughtException(e) }
  }

  private fun handleUncaughtException(e: Throwable) {
    Log.d(TAG, e.message.toString())
    ApplicationUtility.showToast(this, this.resources.getString(R.string.all_unexpected_crash))
    val intent = Intent(this, HomeActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
  }

  open fun initDagger() =
    DaggerAppComponent.builder().application(this).build().inject(this)


  override fun androidInjector(): AndroidInjector<Any> {
    return androidInjector
  }
}