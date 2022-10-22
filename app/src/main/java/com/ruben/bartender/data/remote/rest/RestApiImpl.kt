package com.ruben.bartender.data.remote.rest

import com.ruben.bartender.data.remote.RetrofitApi
import com.ruben.bartender.data.remote.model.request.MakeDrinkRequest
import com.ruben.bartender.data.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import javax.inject.Inject
import kotlinx.coroutines.Deferred

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class RestApiImpl @Inject constructor(private val retrofitApi: RetrofitApi): RestApi {
  override fun makeDrink(makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse?> {
    return retrofitApi.makeDrink(makeDrinkRequest)
  }
}