package com.ruben.data.mapper

import com.ruben.domain.model.MakeDrinkRecord
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class DrinkMapper {
  fun mapMakeDrinkResponse(makeDrinkResponse: MakeDrinkResponse?): MakeDrinkRecord? {
    return if(makeDrinkResponse != null) {
      val makeDrinkRecord = MakeDrinkRecord(0, "")
      makeDrinkRecord.responseCode = makeDrinkResponse.status
      makeDrinkRecord.responseMessage = makeDrinkResponse.message
      makeDrinkRecord
    }else {
      null
    }
  }
}