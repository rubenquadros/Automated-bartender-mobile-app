package com.ruben.data.mapper

import com.ruben.domain.model.CheckGooglePayRecord
import com.ruben.remote.model.response.paymentResponse.CheckGooglePayResponse

/**
 * Created by ruben.quadros on 19/03/20.
 **/
class PaymentMapper {

  fun mapCheckGooglePayResponse(checkGooglePayResponse: CheckGooglePayResponse?): CheckGooglePayRecord? {
    return if(checkGooglePayResponse != null) {
      val checkGooglePayRecord = CheckGooglePayRecord(0, false, "")
      checkGooglePayRecord.responseCode  = checkGooglePayResponse.status
      checkGooglePayRecord.isAvailable = checkGooglePayResponse.isAvailable
      checkGooglePayRecord.responseMessage = checkGooglePayResponse.message
      return checkGooglePayRecord
    }else {
      null
    }
  }
}