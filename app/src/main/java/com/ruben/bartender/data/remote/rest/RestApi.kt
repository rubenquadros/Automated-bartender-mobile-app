package com.ruben.bartender.data.remote.rest

import com.ruben.bartender.data.remote.model.request.MakeDrinkRequest
import com.ruben.bartender.data.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred

/**
 * Created by ruben.quadros on 29/02/20.
 **/
@Suppress("DeferredIsResult")
interface RestApi {
  fun makeDrink(makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse?>
}