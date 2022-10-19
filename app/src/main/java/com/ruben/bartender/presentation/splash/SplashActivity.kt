package com.ruben.bartender.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.domain.interactor.user.UserHandler
import com.ruben.bartender.presentation.home.HomeActivity
import com.ruben.bartender.presentation.onboarding.OnBoardingActivity
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SplashActivity : BaseActivity() {

  @Inject
  lateinit var userHandler: UserHandler

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    this.startSplash()
  }

  private fun startSplash() {
    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    Handler().postDelayed({
      if(userHandler.isLoggedIn() && userHandler.isRegistered()) {
        startActivity(Intent(this, HomeActivity::class.java))
      }else {
        startActivity(Intent(this, OnBoardingActivity::class.java))
      }
      finish()
    }, 2000)
  }
}
