package com.ruben.bartender.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.presentation.home.HomeActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SplashActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    this.startSplash()
  }

  private fun startSplash() {
    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    Handler().postDelayed({
      startActivity(Intent(this, HomeActivity::class.java))
      finish()
    }, 2000)
  }
}
