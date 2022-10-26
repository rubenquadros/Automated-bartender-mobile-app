package com.ruben.bartender.presentation.ui.payment

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.BuildConfig
import com.ruben.bartender.presentation.Destination
import com.ruben.bartender.presentation.base.BaseViewModel
import com.ruben.bartender.presentation.ui.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

/**
 * Created by Ruben Quadros on 26/10/22
 **/
@HiltViewModel
class PaymentViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    BaseViewModel<PaymentState, PaymentSideEffect>(savedStateHandle) {

    override fun createInitialState(): PaymentState = PaymentState(
        drinkName = savedStateHandle.get<String>(Destination.Payment.DrinkNameArg).orEmpty(),
        price = savedStateHandle.get<String>(Destination.Payment.PriceArg).orEmpty()
    )

    fun onPaymentClick(paymentId: String) = intent {
        val uri = Uri.Builder()
            .scheme(PaymentConstants.SCHEME)
            .authority(PaymentConstants.AUTHORITY)
            .appendQueryParameter(PaymentConstants.UPI_ID, BuildConfig.UPI_ID)
            .appendQueryParameter(PaymentConstants.MERCHANT_NAME, BuildConfig.MERCHANT_NAME)
            .appendQueryParameter(PaymentConstants.CURRENCY_KEY, PaymentConstants.CURRENCY)
            .appendQueryParameter(PaymentConstants.PAY_AMOUNT, state.price)
            .appendQueryParameter(PaymentConstants.TRANSACTION_NOTE, state.drinkName)
            .build()

        val packageName = if (paymentId == Constants.GPAY_ID) {
            PaymentConstants.GOOGLE_PAY_PACKAGE_NAME
        } else {
            PaymentConstants.PHONE_PAY_PACKAGE_NAME
        }

        postSideEffect(
            PaymentSideEffect.StartPayment(
                uri = uri,
                packageName = packageName
            )
        )
    }

    fun onPaymentFailed() {

    }
}