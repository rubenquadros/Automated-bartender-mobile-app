package com.ruben.bartender.presentation.onboarding.signup

import androidx.lifecycle.ViewModel
import com.ruben.domain.interactor.boarding.SignUpUseCase
import javax.inject.Inject

/**
 * Created by ruben.quadros on 11/03/20.
 **/
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase): ViewModel() {
}