package com.ruben.bartender.data.remote.model.response.onBoardingResponse

import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by ruben.quadros on 16/03/20.
 **/
data class CheckUserResponse(
  var users: MutableList<DocumentSnapshot>?
)