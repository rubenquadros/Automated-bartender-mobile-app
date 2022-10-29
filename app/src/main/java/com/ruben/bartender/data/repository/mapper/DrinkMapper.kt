package com.ruben.bartender.data.repository.mapper

import com.ruben.bartender.data.remote.model.response.MakeDrinkResponse
import com.ruben.bartender.data.remote.rest.ApiResponse
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MakeDrinkRecord

/**
 * Created by ruben.quadros on 10/03/20.
 **/
fun ApiResponse<MakeDrinkResponse, Void>.toMakeDrinkBaseRecord(): BaseRecord<MakeDrinkRecord, ErrorRecord> {
    return if (this is ApiResponse.Success) {
        BaseRecord.Success(
            body =
            MakeDrinkRecord(
                responseCode = this.body.status,
                responseMessage = this.body.message
            )
        )
    } else {
        BaseRecord.Error(error = ErrorRecord.NoBodyErrorRecord)
    }
}