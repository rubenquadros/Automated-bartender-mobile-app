package com.ruben.bartender.payments

import android.app.Activity
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by ruben.quadros on 23/03/20.
 **/
@ExperimentalCoroutinesApi
class GooglePayClient(private val activity: Activity) {

//  private val paymentsClient = createPaymentsClient(activity)
//  private val baseCardPaymentMethod = JSONObject().apply {
//    put(PaymentConstants.TYPE, PaymentConstants.GOOGLE_PAY_TYPE)
//    put(PaymentConstants.GOOGLE_PAY_PARAMETERS, JSONObject().apply {
//      put(
//        PaymentConstants.GOOGLE_PAY_ALLOWED_CARD_NETWORKS,
//        JSONArray(listOf(PaymentConstants.VISA, PaymentConstants.MASTERCARD))
//      )
//      put(
//        PaymentConstants.GOOGLE_PAY_ALLOWED_AUTH_METHODS,
//        JSONArray(listOf(PaymentConstants.AUTH_PAN, PaymentConstants.AUTH_CRYPTOGRAM))
//      )
//    })
//  }
//  private val googlePayBaseConfiguration = JSONObject().apply {
//    put(PaymentConstants.API_VERSION, PaymentConstants.GOOGLE_PAY_API_VERSION)
//    put(PaymentConstants.API_MINOR_VERSION, PaymentConstants.GOOGLE_PAY_API_MINOR_VERSION)
//    put(PaymentConstants.GOOGLE_PAY_ALLOWED_PAYMENT_METHODS, JSONArray().put(baseCardPaymentMethod))
//  }
//
//  private fun createPaymentsClient(activity: Activity): PaymentsClient {
//    val walletOptions = Wallet.WalletOptions.Builder()
//      .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
//      .setTheme(THEME_LIGHT)
//      .build()
//    return Wallet.getPaymentsClient(activity, walletOptions)
//  }
//
//  @Suppress("NON_APPLICABLE_CALL_FOR_BUILDER_INFERENCE")
//  fun isGooglePayReady(): Flow<CheckGooglePayResponse?> {
//    return channelFlow {
//      val readyToPayRequest = IsReadyToPayRequest.fromJson(googlePayBaseConfiguration.toString())
//      paymentsClient.isReadyToPay(readyToPayRequest)
//        .addOnCompleteListener {
//          if (it.isSuccessful) {
//            val checkGooglePayResponse = CheckGooglePayResponse(0, false, "")
//            try {
//              checkGooglePayResponse.status = ApiConstants.HTTP_OK
//              checkGooglePayResponse.isAvailable = it.result
//              checkGooglePayResponse.message = ApiConstants.SUCCESS
//              channel.offer(checkGooglePayResponse)
//            } catch (e: ApiException) {
//              checkGooglePayResponse.status = ApiConstants.HTTP_OK
//              checkGooglePayResponse.isAvailable = false
//              checkGooglePayResponse.message = ApiConstants.SUCCESS
//              channel.offer(checkGooglePayResponse)
//            }
//          } else {
//            val checkGooglePayResponse = CheckGooglePayResponse(0, false, "")
//            checkGooglePayResponse.status = ApiConstants.HTTP_API_FAIL
//            checkGooglePayResponse.isAvailable = false
//            checkGooglePayResponse.message = ApiConstants.FAIL
//            channel.offer(checkGooglePayResponse)
//            channel.close(null)
//          }
//        }
//      awaitClose()
//    }
//  }
}