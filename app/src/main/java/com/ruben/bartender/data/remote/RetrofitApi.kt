package com.ruben.bartender.data.remote

import com.ruben.bartender.data.remote.model.request.MakeDrinkRequest
import com.ruben.bartender.data.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by ruben.quadros on 01/03/20.
 **/
@Suppress("DeferredIsResult")
interface RetrofitApi {

  @POST("make_drink")
  fun makeDrink(@Body makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse>

}