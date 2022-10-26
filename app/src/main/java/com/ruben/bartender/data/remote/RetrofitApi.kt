package com.ruben.bartender.data.remote

import com.ruben.bartender.data.remote.model.request.MakeDrinkRequest
import com.ruben.bartender.data.remote.model.response.MakeDrinkResponse
import com.ruben.bartender.data.remote.rest.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by ruben.quadros on 01/03/20.
 **/
interface RetrofitApi {

  @POST("/make_drink")
  suspend fun makeDrink(@Body makeDrinkRequest: MakeDrinkRequest): ApiResponse<MakeDrinkResponse, Nothing>

}