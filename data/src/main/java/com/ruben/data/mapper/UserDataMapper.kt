package com.ruben.data.mapper

import com.ruben.domain.model.UserRecord
import com.ruben.remote.model.response.userDataResponse.UserDataResponse

/**
 * Created by ruben.quadros on 13/03/20.
 **/
class UserDataMapper() {

  fun mapUserData(userDataResponse: UserDataResponse?): UserRecord? {
    return if(userDataResponse != null) {
      val userRecord = UserRecord("", "", "")
      userRecord.firstName = userDataResponse.userData!!["firstName"].toString()
      userRecord.lastName = userDataResponse.userData!!["lastName"].toString()
      userRecord.phoneNumber = userDataResponse.userData!!["phoneNumber"].toString()
      return userRecord
    }else {
      null
    }
  }
}