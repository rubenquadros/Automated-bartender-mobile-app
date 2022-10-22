package com.ruben.bartender.data.remote.model.response.onBoardingResponse

/**
 * Created by ruben.quadros on 10/03/20.
 **/
sealed class LoginResponse {
    data class LoginFail(val message: String) : LoginResponse()
    object NewUser : LoginResponse()
    object LoginSuccess : LoginResponse()
}