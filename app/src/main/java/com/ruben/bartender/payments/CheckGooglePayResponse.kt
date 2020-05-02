package com.ruben.bartender.payments

/**
 * Created by ruben.quadros on 23/03/20.
 **/
data class CheckGooglePayResponse(
  var status: Int,
  var isAvailable: Boolean?,
  var message: String
)