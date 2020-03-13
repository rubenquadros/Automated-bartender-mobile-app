package com.ruben.remote.model.response.userDataResponse

import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by ruben.quadros on 13/03/20.
 **/
data class UserDataResponse(
  var userData: MutableMap<String, Any>?
)