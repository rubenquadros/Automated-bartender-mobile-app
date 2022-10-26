package com.ruben.bartender.data.remote.rest

import java.io.IOException
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

/**
 * Created by Ruben Quadros on 26/10/22
 **/
class ApiResponseCall<SUCCESS: Any, ERROR: Any>(
    private val delegate: Call<SUCCESS>,
    private val errorConverter: Converter<ResponseBody, ERROR>
) : Call<ApiResponse<SUCCESS, ERROR>> {

    override fun enqueue(callback: Callback<ApiResponse<SUCCESS, ERROR>>) {
        return delegate.enqueue(object : Callback<SUCCESS> {
            override fun onResponse(call: Call<SUCCESS>, response: Response<SUCCESS>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.SuccessNoBody)
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> runCatching { errorConverter.convert(error) }.getOrNull()
                    }

                    if (errorBody != null) {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.Error(error = errorBody, code = code))
                        )
                    } else {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.UnknownError)
                        )
                    }
                }
            }

            override fun onFailure(call: Call<SUCCESS>, t: Throwable) {
                val apiResponse = when (t) {
                    is IOException -> ApiResponse.NetworkError(t)
                    else -> ApiResponse.UnknownError
                }
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }
        })
    }

    override fun clone(): Call<ApiResponse<SUCCESS, ERROR>> =
        ApiResponseCall(delegate.clone(), errorConverter)

    override fun execute(): Response<ApiResponse<SUCCESS, ERROR>> {
        throw UnsupportedOperationException("ApiResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}