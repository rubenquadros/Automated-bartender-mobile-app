package com.ruben.bartender.data.remote.model.response

/**
 * Created by ruben.quadros on 12/03/20.
 **/
sealed class SaveUserDetailsResponse {
    object SaveSuccess : SaveUserDetailsResponse()
    data class SaveFail(val message: String) : SaveUserDetailsResponse()

    fun isSuccess() = this is SaveSuccess
}