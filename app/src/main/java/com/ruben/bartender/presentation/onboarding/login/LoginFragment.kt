package com.ruben.bartender.presentation.onboarding.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ruben.bartender.R
import dagger.android.support.AndroidSupportInjection

class LoginFragment : Fragment() {

  @BindView(R.id.boardingOtpEt)
  lateinit var boardingOtp: AppCompatEditText

  @BindView(R.id.boardingDescriptionTv)
  lateinit var boardingDescTv: AppCompatTextView

  @BindView(R.id.boardingContinueBtn)
  lateinit var continueButton: AppCompatButton

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

  @OnClick(value = [R.id.boardingContinueBtn])
  fun onClick() {

  }

  companion object {
    @JvmStatic
    fun newInstance() = LoginFragment()
  }
}
