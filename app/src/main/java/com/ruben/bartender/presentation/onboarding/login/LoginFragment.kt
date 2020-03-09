package com.ruben.bartender.presentation.onboarding.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ruben.bartender.R
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.remote.model.response.onBoardingResponse.SignInResponse
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginFragment : Fragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @BindView(R.id.boardingMobileEt)
  lateinit var boardingPhoneNum: AppCompatEditText

  @BindView(R.id.boardingOtpEt)
  lateinit var boardingOtp: AppCompatEditText

  @BindView(R.id.boardingDescriptionTv)
  lateinit var boardingDescTv: AppCompatTextView

  @BindView(R.id.boardingContinueBtn)
  lateinit var continueButton: AppCompatButton

  @BindView(R.id.boardingPb)
  lateinit var progressBar: ProgressBar

  private lateinit var loginViewModel: LoginViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_login, container, false)
    ButterKnife.bind(this, view)
    return view
  }

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    subscribeOtpResponse()
  }

  private fun parseOtpResponse(sendOtpResponse: SendOtpResponse?) {
    ApplicationUtility.stopProgress(progressBar, activity!!)
    if(sendOtpResponse != null) {
      if(sendOtpResponse.phoneAuthCredential != null) {
        loginViewModel.signIn(sendOtpResponse.phoneAuthCredential!!)
      }
    }
  }

  private fun parseSignInResponse(signInResponse: SignInResponse?) {
    if(signInResponse != null) {
      Log.d("@@@", signInResponse.message)
    }
  }

  @OnClick(value = [R.id.boardingContinueBtn])
  fun onClick() {
    ApplicationUtility.showProgress(progressBar, activity!!)
    loginViewModel.sendOTP(activity!!.resources.getString(R.string.all_country_code) + boardingPhoneNum.text.toString())
  }

  private fun subscribeOtpResponse() {
    loginViewModel.getOtpResponse().observe(this, Observer { it?.let { parseOtpResponse(it) } })
    loginViewModel.getSignInResponse().observe(this, Observer { it?.let { parseSignInResponse(it) } })
  }

  companion object {
    @JvmStatic
    fun newInstance() = LoginFragment()
  }
}
