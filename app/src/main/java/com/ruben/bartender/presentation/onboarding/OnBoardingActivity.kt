package com.ruben.bartender.presentation.onboarding

import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.presentation.onboarding.login.LoginFragment
import com.ruben.bartender.utils.ApplicationConstants.LOGIN_TAG
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.domain.interactor.user.UserHandler
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.all_appbar_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class OnBoardingActivity : BaseActivity(), HasAndroidInjector {

  @Inject
  lateinit var androidInjector: DispatchingAndroidInjector<Any>

  @Inject
  lateinit var userHandler: UserHandler

  @BindView(R.id.toolbarTitle)
  lateinit var toolBarTitle: AppCompatTextView

  @BindString(R.string.all_boarding)
  lateinit var signUp: String

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_on_boarding)
    ButterKnife.bind(this)
    setupToolBar()
    ApplicationUtility.showFragment(
      LoginFragment.newInstance(),
      false,
      LOGIN_TAG,
      null,
      supportFragmentManager
    )
  }

  private fun setupToolBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowHomeEnabled(false)
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    toolBarTitle.text = signUp
  }

  override fun androidInjector(): AndroidInjector<Any> {
    return androidInjector
  }

  override fun onBackPressed() {
    super.onBackPressed()
    finish()
  }
}
