package com.ruben.remote.model.response.paymentResponse

/**
 * Created by ruben.quadros on 19/03/20.
 **/
data class CheckGooglePayResponse(
  var status: Int,
  var isAvailable: Boolean?,
  var message: String
)