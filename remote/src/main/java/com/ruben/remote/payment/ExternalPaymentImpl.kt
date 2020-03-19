package com.ruben.remote.payment

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import com.ruben.remote.model.request.CheckGooglePayRequest
import com.ruben.remote.model.response.paymentResponse.CheckGooglePayResponse
import com.ruben.remote.utils.ApiConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by ruben.quadros on 19/03/20.
 **/
@Suppress("NON_APPLICABLE_CALL_FOR_BUILDER_INFERENCE")
@ExperimentalCoroutinesApi
class ExternalPaymentImpl(private val paymentsClient: PaymentsClient): ExternalPayment {
  override fun isGooglePayReady(checkGooglePayRequest: CheckGooglePayRequest): Flow<CheckGooglePayResponse?> {
    return channelFlow {
      val baseCardPaymentMethod = JSONObject().apply {
        put(ApiConstants.GOOGLE_PAY_TYPE, checkGooglePayRequest.type)
        put(ApiConstants.GOOGLE_PAY_PARAMETERS, JSONObject().apply {
          put(ApiConstants.GOOGLE_PAY_ALLOWED_CARD_NETWORKS, JSONArray(checkGooglePayRequest.allowedCardNetworks))
          put(ApiConstants.GOOGLE_PAY_ALLOWED_AUTH_METHODS, JSONArray(checkGooglePayRequest.allowedAuthMethods))
        })
      }

      val googlePayBaseConfiguration = JSONObject().apply {
        put(ApiConstants.GOOGLE_PAY_API_VERSION, checkGooglePayRequest.apiVersion)
        put(ApiConstants.GOOGLE_PAY_API_MINOR_VERSION, checkGooglePayRequest.apiVersionMinor)
        put(ApiConstants.GOOGLE_PAY_ALLOWED_PAYMENT_METHODS, JSONArray().put(baseCardPaymentMethod))
      }

      val readyToPayRequest = IsReadyToPayRequest.fromJson(googlePayBaseConfiguration.toString())
      paymentsClient.isReadyToPay(readyToPayRequest)
        .addOnCompleteListener {
          if(it.isSuccessful) {
            val checkGooglePayResponse = CheckGooglePayResponse(0, false, "")
            try {
              checkGooglePayResponse.status = ApiConstants.HTTP_OK
              checkGooglePayResponse.isAvailable = it.result
              checkGooglePayResponse.message = ApiConstants.SUCCESS
              channel.offer(checkGooglePayResponse)
            }catch (e: ApiException) {
              checkGooglePayResponse.status = ApiConstants.HTTP_OK
              checkGooglePayResponse.isAvailable = false
              checkGooglePayResponse.message = ApiConstants.SUCCESS
              channel.offer(checkGooglePayResponse)
            }
          }else {
            val checkGooglePayResponse = CheckGooglePayResponse(0, false, "")
            checkGooglePayResponse.status = ApiConstants.HTTP_API_FAIL
            checkGooglePayResponse.isAvailable = false
            checkGooglePayResponse.message = ApiConstants.FAIL
            channel.offer(checkGooglePayResponse)
            channel.close(null)
          }
        }
      awaitClose()
    }
  }
}