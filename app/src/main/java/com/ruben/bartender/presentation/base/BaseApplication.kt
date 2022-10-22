package com.ruben.bartender.presentation.base

import android.app.Application
import android.content.Intent
import com.ruben.bartender.R
import com.ruben.bartender.utils.ApplicationUtility
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@HiltAndroidApp
open class BaseApplication: Application() {

  override fun onCreate() {
    super.onCreate()
    Thread.setDefaultUncaughtExceptionHandler { _, e -> handleUncaughtException(e) }
  }

  private fun handleUncaughtException(e: Throwable) {
    ApplicationUtility.showToast(this, this.resources.getString(R.string.all_unexpected_crash))
    val intent = Intent(this, HomeActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
  }
}