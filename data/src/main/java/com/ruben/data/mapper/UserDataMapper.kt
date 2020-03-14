package com.ruben.data.mapper

import com.ruben.domain.model.SignOutRecord
import com.ruben.domain.model.UserRecord
import com.ruben.remote.model.response.signoutResponse.SignoutResponse
import com.ruben.remote.model.response.userDataResponse.UserDataResponse
import com.ruben.remote.utils.ApiConstants

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