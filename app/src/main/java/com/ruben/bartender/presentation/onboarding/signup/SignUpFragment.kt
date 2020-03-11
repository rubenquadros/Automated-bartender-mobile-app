package com.ruben.bartender.presentation.onboarding.signup

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.ruben.bartender.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by ruben.quadros on 11/03/20.
 **/

private const val PHONE_NUMBER = "phoneNumber"

class SignUpFragment : Fragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

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
