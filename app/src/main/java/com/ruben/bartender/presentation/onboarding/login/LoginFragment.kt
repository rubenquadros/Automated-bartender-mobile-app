package com.ruben.bartender.presentation.onboarding.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.firebase.auth.PhoneAuthProvider
import com.ruben.bartender.R
import com.ruben.bartender.presentation.home.HomeActivity
import com.ruben.bartender.utils.ApplicationConstants
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.domain.model.OtpRecord
import com.ruben.domain.model.SignInRecord
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

private const val EVENT = "event"

@Suppress("PrivatePropertyName")
@ExperimentalCoroutinesApi
class LoginFragment : Fragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @BindView(R.id.boardingParent)
  lateinit var parentView: ConstraintLayout

  @BindView(R.id.boardingInnerParent)
  lateinit var innerParentView: ConstraintLayout

  @BindView(R.id.boardingMobileEt)
  lateinit var boardingPhoneNum: AppCompatEditText

  @BindView(R.id.boardingOtpEt)
  lateinit var boardingOtp: AppCompatEditText

  @BindView(R.id.boardingResendBtn)
  lateinit var resendBtn: AppCompatTextView

  @BindView(R.id.boardingDescriptionTv)
  lateinit var boardingDescTv: AppCompatTextView

  @BindView(R.id.boardingContinueBtn)
  lateinit var continueButton: AppCompatButton

  @BindView(R.id.boardingPb)
  lateinit var progressBar: ProgressBar

  private var event: String? = null
  private lateinit var loginViewModel: LoginViewModel
  private lateinit var counter: CountDownTimer
  private val TAG = javaClass.simpleName
  private var otpSent = false
  private var verificationID = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      event = it.getString(EVENT)
    }
  }

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

  private fun showOtpField() {
    otpSent = true
    boardingOtp.visibility = View.VISIBLE
    resendBtn.visibility = View.VISIBLE
    continueButton.text = activity!!.resources.getString(R.string.all_verify)
    startCounter()
  }

  private fun signIn(code: String) {
    val credential = PhoneAuthProvider.getCredential(verificationID, code)
    loginViewModel.signIn(credential)
  }

  private fun startCounter() {
    counter = object : CountDownTimer((ApplicationConstants.OTP_TIMER * 1000).toLong(), 1000) {
      override fun onTick(millisUntilFinished: Long) {
        val seconds = millisUntilFinished / 1000
        resendBtn.text = String.format("00:%s", String.format("%02d", seconds % 60))
      }

      override fun onFinish() {
        resendBtn.text = activity!!.resources.getString(R.string.all_resend)
        resendBtn.isClickable = true
        resendBtn.isEnabled = true
      }
    }.start()
  }

  private fun parseOtpResponse(otpRecord: OtpRecord?) {
    ApplicationUtility.stopProgress(progressBar, activity!!)
    if (otpRecord != null) {
      when {
        otpRecord.errorMessage != null   -> {
          Log.d(TAG, otpRecord.errorMessage!!)
          ApplicationUtility.showSnack(
            activity!!.resources.getString(R.string.boarding_otp_send_fail),
            parentView,
            activity!!.resources.getString(R.string.all_ok)
          )
        }
        otpRecord.verificationID != null -> {
          verificationID = otpRecord.verificationID!!
          showOtpField()
        }
        else                             -> {
          ApplicationUtility.showSnack(
            activity!!.resources.getString(R.string.all_generic_err),
            parentView,
            activity!!.resources.getString(R.string.all_ok)
          )
        }
      }
    }
  }

  private fun parseSignInResponse(signInRecord: SignInRecord?) {
    if (signInRecord != null) {
      when (signInRecord.responseCode) {
        ApplicationConstants.HTTP_OK   -> {
          counter.cancel()
          if (event == ApplicationConstants.REGISTRATION) {
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
          } else {
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
          }
        }
        ApplicationConstants.AUTH_FAIL -> {
          ApplicationUtility.showSnack(
            activity!!.resources.getString(R.string.boarding_auth_fail),
            parentView,
            activity!!.resources.getString(R.string.all_ok)
          )
        }
        ApplicationConstants.API_FAIL  -> {
          ApplicationUtility.showSnack(
            activity!!.resources.getString(R.string.all_generic_err),
            parentView,
            activity!!.resources.getString(R.string.all_ok)
          )
        }
        else                           -> {
          ApplicationUtility.showSnack(
            activity!!.resources.getString(R.string.all_generic_err),
            parentView,
            activity!!.resources.getString(R.string.all_ok)
          )
        }
      }
      Log.d(TAG, signInRecord.message!!)
    }
  }

  @OnClick(value = [R.id.boardingContinueBtn, R.id.boardingDescriptionTv, R.id.boardingInnerParent, R.id.boardingResendBtn])
  fun onClick(view: View) {
    when (view.id) {
      R.id.boardingContinueBtn   -> {
        if (otpSent) {
          signIn(boardingOtp.text.toString())
        } else {
          ApplicationUtility.showProgress(progressBar, activity!!)
          ApplicationUtility.hideKeyboard(activity!!, parentView)
          loginViewModel.sendOTP(activity!!.resources.getString(R.string.all_country_code) + boardingPhoneNum.text.toString())
        }
      }
      R.id.boardingResendBtn     -> {
        loginViewModel.sendOTP(activity!!.resources.getString(R.string.all_country_code) + boardingPhoneNum.text.toString())
      }
      R.id.boardingDescriptionTv -> {
        ApplicationUtility.hideKeyboard(activity!!, parentView)
      }
      R.id.boardingInnerParent   -> {
        ApplicationUtility.hideKeyboard(activity!!, parentView)
      }
    }
  }

  private fun subscribeOtpResponse() {
    loginViewModel.getOtpResponse().observe(this, Observer { it?.let { parseOtpResponse(it) } })
    loginViewModel.getSignInResponse()
      .observe(this, Observer { it?.let { parseSignInResponse(it) } })
  }

  companion object {
    @JvmStatic
    fun newInstance(): LoginFragment {
      val args = Bundle()
      val loginFragment = LoginFragment()
      loginFragment.arguments = args
      return loginFragment
    }
  }
}
