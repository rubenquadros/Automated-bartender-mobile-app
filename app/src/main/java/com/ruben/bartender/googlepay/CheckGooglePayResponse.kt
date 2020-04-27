package com.ruben.bartender.googlepay

/**
 * Created by ruben.quadros on 23/03/20.
 **/
data class CheckGooglePayResponse(
  var status: Int,
  var isAvailable: Boolean?,
  var message: String
)