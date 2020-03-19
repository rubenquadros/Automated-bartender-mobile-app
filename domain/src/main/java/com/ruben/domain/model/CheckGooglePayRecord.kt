package com.ruben.domain.model

/**
 * Created by ruben.quadros on 19/03/20.
 **/
data class CheckGooglePayRecord(
  var responseCode: Int,
  var isAvailable: Boolean?,
  var responseMessage: String
)