package com.ruben.bartender.data.remote.model.response.menuCategoryResponse

import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by ruben.quadros on 02/03/20.
 **/
data class CategoryResponse(
  var categoryName: MutableList<DocumentSnapshot>?
)