package com.ruben.bartender.data.remote.rest

import java.io.IOException

/**
 * Created by Ruben Quadros on 26/10/22
 **/
sealed class ApiResponse<out RESPONSE, out ERROR> {

    data class Success<RESPONSE>(val body: RESPONSE) : ApiResponse<RESPONSE, Nothing>()

    object SuccessNoBody: ApiResponse<Nothing, Nothing>()

    data class Error<ERROR>(val error: ERROR, val code: Int) : ApiResponse<Nothing, ERROR>()

    data class NetworkError(val error: IOException?): ApiResponse<Nothing, Nothing>()

    object UnknownError : ApiResponse<Nothing, Nothing>()
}