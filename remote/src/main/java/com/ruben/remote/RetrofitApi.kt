package com.ruben.remote

import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by ruben.quadros on 01/03/20.
 **/
@Suppress("DeferredIsResult")
interface RetrofitApi {

  @POST("make_drink")
  fun makeDrink(@Body makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse>

/*  @GET("v2/5e61039133000064cf97bed3")
  fun makeDrink():Deferred<MakeDrinkResponse?>*/

}