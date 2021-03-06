package com.ruben.remote.rest

import com.ruben.remote.RetrofitApi
import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred

/**
 * Created by ruben.quadros on 01/03/20.
 **/
@Suppress("DeferredIsResult")
class RestApiImpl(private val retrofitApi: RetrofitApi): RestApi {
  override fun makeDrink(makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse?> {
    return retrofitApi.makeDrink(makeDrinkRequest)
  }
}