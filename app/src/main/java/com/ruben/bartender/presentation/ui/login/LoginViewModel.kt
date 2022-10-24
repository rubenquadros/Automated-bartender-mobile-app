package com.ruben.bartender.presentation.ui.login

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ruben.bartender.R
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.interactor.onboarding.LoginUseCase
import com.ruben.bartender.domain.interactor.onboarding.SendOtpUseCase
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.LoginRecord
import com.ruben.bartender.domain.record.SendOtpErrorRecord
import com.ruben.bartender.domain.record.SendOtpRecord
import com.ruben.bartender.presentation.base.BaseViewModel
import com.ruben.bartender.presentation.ui.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

/**
 * Created by ruben.quadros on 09/03/20.
 **/
@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendOtpUseCase: SendOtpUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginState, LoginSideEffect>(savedStateHandle), LifecycleObserver {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    override fun createInitialState(): LoginState = LoginState()

    fun isValidDigit(digits: String, isOtp: Boolean, isPhoneNumber: Boolean): Boolean {
        val regex = when {
            isOtp -> Constants.OTP_REGEX
            isPhoneNumber -> Constants.PHONE_NUMBER_REGEX
            else -> ""
        }
        return if (digits.isEmpty()) {
            true
        } else {
            Pattern.compile(regex).matcher(digits).matches()
        }
    }

    fun onNumberUpdated(number: String) = intent {
        reduce {
            state.copy(isNumberEntered = number.length == Constants.PHONE_NUMBER_LENGTH)
        }
    }

    fun onOtpUpdated(otp: String) = intent {
        reduce {
            state.copy(isOtpEntered = otp.length == Constants.OTP_LENGTH)
        }
    }

    fun sendOtp(number: String) = intent {
        sendOtpUseCase(SendOtpUseCase.Params(phoneNumber = "+91$number")).collect { baseRecord: BaseRecord<SendOtpRecord, SendOtpErrorRecord> ->
            when (baseRecord) {
                is BaseRecord.Loading -> {
                    reduce { state.copy(isLoading = true) }
                }
                is BaseRecord.Success -> {
                    onSendOtpSuccess(sendOtpRecord = baseRecord.body, number = number)
                }
                else -> {
                    Log.d(
                        TAG,
                        "Otp verification failed: ${(baseRecord as? BaseRecord.Error)?.error?.message}"
                    )
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(LoginSideEffect.ShowError(message = R.string.login_otp_send_fail))
                }
            }
        }
    }

    fun validateOtp(otp: String) = intent {
        login(credential = PhoneAuthProvider.getCredential(state.verificationId, otp))
    }

    fun login(credential: PhoneAuthCredential) = intent {
        loginUseCase(
            LoginUseCase.Params(
                credential = credential,
                phoneNumber = state.phoneNumber
            )
        ).collect { baseRecord: BaseRecord<LoginRecord, ErrorRecord> ->
            when (baseRecord) {
                is BaseRecord.Loading -> {
                    reduce { state.copy(isLoading = true) }
                }
                is BaseRecord.Success -> {
                    reduce { state.copy(isLoading = false) }
                    onLoginSuccess(loginRecord = baseRecord.body)
                }
                else -> {
                    Log.d(
                        TAG,
                        "Login failed: ${((baseRecord as? BaseRecord.Error)?.error as? ErrorRecord.GenericErrorRecord)?.message}"
                    )
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(LoginSideEffect.ShowError(message = R.string.login_fail))
                }
            }
        }
    }

    private fun onSendOtpSuccess(sendOtpRecord: SendOtpRecord, number: String) = intent {
        when (sendOtpRecord) {
            is SendOtpRecord.VerificationSuccessRecord -> {
                reduce { state.copy(isLoading = false, phoneNumber = number) }
                login(credential = sendOtpRecord.credential)
            }
            is SendOtpRecord.OtpVerificationId -> {
                reduce {
                    state.copy(
                        verificationId = sendOtpRecord.id,
                        isLoading = false,
                        phoneNumber = number,
                        shouldShowOtpField = true
                    )
                }
            }
        }
    }

    private fun onLoginSuccess(loginRecord: LoginRecord) = intent {
        when (loginRecord) {
            LoginRecord.LoginSuccess -> {
                postSideEffect(LoginSideEffect.LoginSuccess)
            }
            LoginRecord.NewUser -> {
                postSideEffect(LoginSideEffect.NavigateToSignUp(phoneNumber = state.phoneNumber))
            }
        }
    }
}