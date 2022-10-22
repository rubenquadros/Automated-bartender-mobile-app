package com.ruben.bartender.domain.record

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class LoginRecord {
    object LoginSuccess: LoginRecord()
    object NewUser: LoginRecord()
}