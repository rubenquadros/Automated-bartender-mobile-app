package com.ruben.remote.payment

import com.ruben.remote.model.request.CheckGooglePayRequest
import com.ruben.remote.model.response.paymentResponse.CheckGooglePayResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 19/03/20.
 **/
interface ExternalPayment {

  fun isGooglePayReady(checkGooglePayRequest: CheckGooglePayRequest): Flow<CheckGooglePayResponse?>

}