package com.ruben.remote.rest

import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@Suppress("DeferredIsResult")
interface RestApi {
  fun makeDrink(makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse?>
}