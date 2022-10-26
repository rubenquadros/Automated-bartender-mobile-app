package com.ruben.bartender.data.remote.rest

import java.lang.reflect.Type
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter

/**
 * Created by Ruben Quadros on 26/10/22
 **/
class ApiResponseAdapter<SUCCESS: Any, ERROR: Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, ERROR>
) : CallAdapter<SUCCESS, Call<ApiResponse<SUCCESS, ERROR>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<SUCCESS>): Call<ApiResponse<SUCCESS, ERROR>> =
        ApiResponseCall(call, errorBodyConverter)
}