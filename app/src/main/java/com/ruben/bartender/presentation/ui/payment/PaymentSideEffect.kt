package com.ruben.bartender.presentation.ui.payment

import android.net.Uri

/**
 * Created by Ruben Quadros on 26/10/22
 **/
sealed class PaymentSideEffect {
    data class StartPayment(
        val uri: Uri,
        val packageName: String
    ) : PaymentSideEffect()

    object PaymentDone: PaymentSideEffect()
}
