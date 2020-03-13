package com.ruben.bartender.presentation.onboarding.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ruben.bartender.R
import com.ruben.bartender.presentation.home.HomeActivity
import com.ruben.bartender.utils.ApplicationConstants
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.domain.model.SaveUserRecord
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 11/03/20.
 **/

private const val PHONE_NUMBER = "phoneNumber"

@ExperimentalCoroutinesApi
@Suppress("DEPRECATION", "PrivatePropertyName")
class SignUpFragment : Fragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @BindView(R.id.signup_parent)
  lateinit var signUpParent: ConstraintLayout

  @BindView(R.id.signupFirstNameEt)
  lateinit var signupFirstNameEt: AppCompatEditText

  @BindView(R.id.signupLastNameEt)
  lateinit var signupLastNameEt: AppCompatEditText

  @BindView(R.id.signupStartBtn)
  lateinit var startBtn: AppCompatButton

  @BindView(R.id.signupProgressBar)
  lateinit var progressBar: ProgressBar

  private val TAG = javaClass.simpleName
  private lateinit var signUpViewModel: SignUpViewModel
  private var phoneNumber: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      phoneNumber = it.getString(PHONE_NUMBER)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
    ButterKnife.bind(this, view)
    return view
  }

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    signUpViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)
    observeSaveUserResponse()
    observerInputs()
  }

  private fun parseSaveUserResponse(saveUserRecord: SaveUserRecord?) {
    ApplicationUtility.stopProgress(progressBar, activity!!)
    if (saveUserRecord != null) {
      when (saveUserRecord.responseCode) {
        ApplicationConstants.HTTP_OK  -> {
          val intent = Intent(activity, HomeActivity::class.java)
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
          startActivity(intent)
        }
        ApplicationConstants.API_FAIL -> {
          ApplicationUtility.showSnack(
            resources.getString(R.string.signup_save_failed),
            signUpParent,
            resources.getString(R.string.all_ok)
          )
        }
        else                          -> {
          ApplicationUtility.showSnack(
            resources.getString(R.string.all_generic_err),
            signUpParent,
            resources.getString(R.string.all_ok)
          )
        }
      }
      Log.d(TAG, saveUserRecord.message)
    } else {
      ApplicationUtility.showSnack(
        resources.getString(R.string.all_generic_err),
        signUpParent,
        resources.getString(R.string.all_ok)
      )
    }
  }

  private fun observeSaveUserResponse() {
    signUpViewModel.getSaveUserResponse()
      .observe(this, Observer { it?.let { parseSaveUserResponse(it) } })
  }

  private fun observerInputs() {
    signupFirstNameEt.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(signupLastNameEt.text.toString())) {
          startBtn.isEnabled = true
          startBtn.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        } else {
          startBtn.isEnabled = false
          startBtn.setBackgroundColor(resources.getColor(R.color.disabledColor))
        }
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }
    })

    signupLastNameEt.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(signupFirstNameEt.text.toString())) {
          startBtn.isEnabled = true
          startBtn.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        } else {
          startBtn.isEnabled = false
          startBtn.setBackgroundColor(resources.getColor(R.color.disabledColor))
        }
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }
    })
  }

  @OnClick(value = [R.id.signup_inner_parent, R.id.signupStartBtn])
  fun onClick(view: View) {
    when (view.id) {
      R.id.signup_inner_parent -> {
        ApplicationUtility.hideKeyboard(activity!!, signUpParent)
      }
      R.id.signupStartBtn      -> {
        ApplicationUtility.showProgress(progressBar, activity!!)
        signUpViewModel.saveUser(signupFirstNameEt.text?.trim().toString(), signupLastNameEt.text?.trim().toString(), phoneNumber!!)
      }
    }
  }

  companion object {
    @JvmStatic
    fun newInstance(phoneNum: String) =
      SignUpFragment().apply {
        arguments = Bundle().apply {
          putString(PHONE_NUMBER, phoneNum)
        }
      }
  }
}
