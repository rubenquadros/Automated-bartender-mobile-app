package com.ruben.remote.model.response.basicMenuResponse

import com.google.firebase.firestore.DocumentSnapshot

data class BasicMenuResponse(
  var documents: MutableList<DocumentSnapshot>?
)