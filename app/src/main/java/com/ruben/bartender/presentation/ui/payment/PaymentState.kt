package com.ruben.bartender.presentation.ui.payment

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ruben.bartender.R
import com.ruben.bartender.presentation.ui.Constants
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 26/10/22
 **/
@Parcelize
data class PaymentState(
    val drinkName: String,
    val price: String,
    val paymentItems: List<PaymentItem> = getPaymentItems(),
    val isSuccess: Boolean = false,
    val isFail: Boolean = false
) : Parcelable


sealed class PaymentItem(
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
    @StringRes val contentDescription: Int,
    val id: String
): Parcelable {

    @Parcelize
    object GooglePay : PaymentItem(
        icon = R.drawable.ic_google_pay,
        name = R.string.payment_gpay,
        contentDescription = R.string.content_desc_gpay,
        id = Constants.GPAY_ID
    )

    @Parcelize
    object PhonePe : PaymentItem(
        icon = R.drawable.ic_phonepe,
        name = R.string.payment_phone_pe,
        contentDescription = R.string.content_desc_phone_pe,
        id = Constants.PHONE_PE_ID
    )
}

object PaymentConstants {
    const val REQUEST_CODE = 1001
    const val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    const val PHONE_PAY_PACKAGE_NAME = "com.phonepe.app"
    const val SCHEME = "upi"
    const val AUTHORITY = "pay"
    const val UPI_ID = "pa"
    const val MERCHANT_NAME = "pn"
    const val PAY_AMOUNT = "am"
    const val TRANSACTION_NOTE = "tn"
    const val CURRENCY_KEY = "cu"
    const val CURRENCY = "INR"
    const val GOOGLE_PAY_STATUS = "Status"
    const val GOOGLE_PAY_TXN_REF = "txnRef"
    const val GOOGLE_PAY_TXN_ID = "txnId"
    const val GOOGLE_PAY_SUCCESS = "SUCCESS"
}

fun getPaymentItems() = listOf(PaymentItem.GooglePay, PaymentItem.PhonePe)