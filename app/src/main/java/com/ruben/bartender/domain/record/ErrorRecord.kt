package com.ruben.bartender.domain.record

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class ErrorRecord {
    data class GenericErrorRecord(val message: String) : ErrorRecord()
    object NoBodyErrorRecord : ErrorRecord()
}
