package com.ruben.bartender.presentation.ui.payment

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.BuildConfig
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.interactor.drink.MakeDrinkUseCase
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MakeDrinkRecord
import com.ruben.bartender.presentation.Destination
import com.ruben.bartender.presentation.base.BaseViewModel
import com.ruben.bartender.presentation.ui.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

/**
 * Created by Ruben Quadros on 26/10/22
 **/
@HiltViewModel
class PaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val makeDrinkUseCase: MakeDrinkUseCase
) : BaseViewModel<PaymentState, PaymentSideEffect>(savedStateHandle) {

    override fun createInitialState(): PaymentState = PaymentState(
        drinkName = savedStateHandle.get<String>(Destination.Payment.DrinkNameArg).orEmpty(),
        price = savedStateHandle.get<String>(Destination.Payment.PriceArg).orEmpty()
    )

    fun onPaymentClick(paymentId: String) = intent {
        onPaymentSuccess()
//        val uri = Uri.Builder()
//            .scheme(PaymentConstants.SCHEME)
//            .authority(PaymentConstants.AUTHORITY)
//            .appendQueryParameter(PaymentConstants.UPI_ID, BuildConfig.UPI_ID)
//            .appendQueryParameter(PaymentConstants.MERCHANT_NAME, BuildConfig.MERCHANT_NAME)
//            .appendQueryParameter(PaymentConstants.CURRENCY_KEY, PaymentConstants.CURRENCY)
//            .appendQueryParameter(PaymentConstants.PAY_AMOUNT, state.price)
//            .appendQueryParameter(PaymentConstants.TRANSACTION_NOTE, state.drinkName)
//            .build()
//
//        val packageName = if (paymentId == Constants.GPAY_ID) {
//            PaymentConstants.GOOGLE_PAY_PACKAGE_NAME
//        } else {
//            PaymentConstants.PHONE_PAY_PACKAGE_NAME
//        }
//
//        postSideEffect(
//            PaymentSideEffect.StartPayment(
//                uri = uri,
//                packageName = packageName
//            )
//        )
    }

    fun onPaymentFailed() = intent {
        reduce { state.copy(isPaymentFail = true) }
    }

    fun onPaymentSuccess() = intent {
        makeDrinkUseCase(
            MakeDrinkUseCase.Params(drinkName = state.drinkName)
        ).collect { baseRecord: BaseRecord<MakeDrinkRecord, ErrorRecord> ->
            reduce {
                when(baseRecord) {
                    is BaseRecord.Loading -> {
                        state.copy(isLoading = true)
                    }
                    is BaseRecord.Success -> {
                        state.copy(isLoading = false, isDrinkReady = true)
                    }
                    else -> {
                        state.copy(isLoading = false, isMakeDrinkError = true)
                    }
                }
            }
        }
    }

    fun resetFailState() = intent {
        reduce { state.copy(isPaymentFail = false) }
    }
}