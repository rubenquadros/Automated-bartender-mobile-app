package com.ruben.bartender.domain

/**
 * Created by Ruben Quadros on 22/10/22
 **/
sealed class BaseRecord<out RECORD, out ERROR> {

    object Loading : BaseRecord<Nothing, Nothing>()

    data class Success<RECORD>(val body: RECORD) : BaseRecord<RECORD, Nothing>()

    object SuccessNoBody : BaseRecord<Nothing, Nothing>()

    data class Error<ERROR>(val error: ERROR) : BaseRecord<Nothing, ERROR>()

    object UnknownError : BaseRecord<Nothing, Nothing>()
}