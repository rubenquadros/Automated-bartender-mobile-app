package com.ruben.bartender.data.mapper

import com.ruben.bartender.data.remote.model.response.signoutResponse.SignoutResponse
import com.ruben.bartender.data.remote.model.response.userDataResponse.UserDataResponse
import com.ruben.bartender.data.remote.utils.ApiConstants
import com.ruben.bartender.domain.model.SignOutRecord
import com.ruben.bartender.domain.model.UserRecord

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

  fun mapSignOutResponse(signoutResponse: SignoutResponse?): SignOutRecord? {
    return if(signoutResponse != null) {
      val signOutRecord = SignOutRecord(0, "")
      signOutRecord.responseCode = signoutResponse.status
      signOutRecord.message = ApiConstants.SUCCESS
      return signOutRecord
    }else {
      null
    }
  }
}