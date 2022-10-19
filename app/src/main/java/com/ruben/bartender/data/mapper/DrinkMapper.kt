package com.ruben.bartender.data.mapper

import com.ruben.bartender.data.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import com.ruben.bartender.domain.model.MakeDrinkRecord

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