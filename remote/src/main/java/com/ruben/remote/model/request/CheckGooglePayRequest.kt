package com.ruben.remote.model.request

/**
 * Created by ruben.quadros on 19/03/20.
 **/
data class CheckGooglePayRequest(
  var type: String,
  var allowedCardNetworks: List<String>,
  var allowedAuthMethods: List<String>,
  var apiVersion: Int,
  var apiVersionMinor: Int
)