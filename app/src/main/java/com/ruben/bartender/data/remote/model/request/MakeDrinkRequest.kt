package com.ruben.bartender.data.remote.model.request

import com.squareup.moshi.Json

/**
 * Created by ruben.quadros on 05/03/20.
 **/
data class MakeDrinkRequest(
  @Json(name = "name")
  val name: String
)