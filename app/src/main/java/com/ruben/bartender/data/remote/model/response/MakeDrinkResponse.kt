package com.ruben.bartender.data.remote.model.response

import com.squareup.moshi.Json

/**
 * Created by ruben.quadros on 05/03/20.
 **/
data class MakeDrinkResponse(
  @Json(name = "status")
  val status: Int,
  @Json(name = "message")
  val message: String
)