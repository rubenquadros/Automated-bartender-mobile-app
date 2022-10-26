package com.ruben.bartender.data.remote.rest

import com.ruben.bartender.data.remote.model.request.MakeDrinkRequest
import com.ruben.bartender.data.remote.model.response.MakeDrinkResponse

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface RestApi {
    suspend fun makeDrink(makeDrinkRequest: MakeDrinkRequest): ApiResponse<MakeDrinkResponse, Nothing>
}