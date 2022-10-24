package com.ruben.bartender.presentation.ui.signup

import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.R
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.interactor.onboarding.SignUpUseCase
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.presentation.Destination
import com.ruben.bartender.presentation.base.BaseViewModel
import com.ruben.bartender.presentation.ui.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

/**
 * Created by ruben.quadros on 11/03/20.
 **/
@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<SignUpState, SignUpSideEffect>(savedStateHandle) {

    override fun createInitialState(): SignUpState =
        SignUpState(
            phoneNumber = savedStateHandle.get<String>(Destination.SignUp.PhoneArg).orEmpty()
        )

    fun onFirstNameUpdated(name: String) = intent {
        reduce {
            state.copy(isFirstNameEntered = name.isNotEmpty())
        }
    }

    fun onLastNameUpdated(name: String) = intent {
        reduce {
            state.copy(isLastNameEntered = name.isNotEmpty())
        }
    }

    fun isValidLetter(letters: String): Boolean {
        return letters.matches(Constants.LETTERS_REGEX.toRegex())
    }

    fun navigateToHome() = intent {
        postSideEffect(SignUpSideEffect.SignUpSuccess)
    }

    fun saveUser(firstName: String, lastName: String) = intent {
        signUpUseCase(
            SignUpUseCase.Params(
                phoneNumber = state.phoneNumber,
                firstName = firstName,
                lastName = lastName
            )
        ).collect { baseRecord: BaseRecord<Nothing, ErrorRecord> ->
            when (baseRecord) {
                is BaseRecord.Loading -> {
                    reduce { state.copy(isLoading = true) }
                }
                is BaseRecord.SuccessNoBody -> {
                    reduce { state.copy(isLoading = false) }
                    navigateToHome()
                }
                else -> {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(
                        SignUpSideEffect.ShowError(
                            message = R.string.signup_save_failed,
                            action = R.string.all_ok
                        )
                    )
                }
            }
        }
    }
}