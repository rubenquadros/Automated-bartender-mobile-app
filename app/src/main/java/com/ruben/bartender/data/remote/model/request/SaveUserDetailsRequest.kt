package com.ruben.bartender.data.remote.model.request

/**
 * Created by ruben.quadros on 12/03/20.
 **/
data class SaveUserDetailsRequest(
  val firstName: String,
  val lastName: String,
  val phoneNumber: String
)