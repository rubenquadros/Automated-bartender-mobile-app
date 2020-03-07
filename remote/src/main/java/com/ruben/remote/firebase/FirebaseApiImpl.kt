package com.ruben.remote.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.basicMenuResponse.Document
import com.ruben.remote.model.response.menuCategoryResponse.CategoryName
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
import com.ruben.remote.utils.ApiConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import java.util.ArrayList

/**
 * Created by ruben.quadros on 01/03/20.
 **/
@Suppress("PrivatePropertyName", "NON_APPLICABLE_CALL_FOR_BUILDER_INFERENCE")
@ExperimentalCoroutinesApi
class FirebaseApiImpl : FirebaseApi {

  private val firestoreDB = FirebaseFirestore.getInstance()
  private val TAG = javaClass.simpleName

  override  fun getBasicMenu(): Flow<BasicMenuResponse?> {
    val basicMenuResponse = BasicMenuResponse(ArrayList())
    return channelFlow {
      firestoreDB.collection(ApiConstants.MENU_COLLECTION).document(ApiConstants.BASIC_MENU_DOC)
        .collection(ApiConstants.BASIC_MENU_COLLECTION)
        .get()
        .addOnSuccessListener { result ->
          for(document in result) {
            val menuDocument = Document("", "", "")
            menuDocument.name = document.data["name"].toString()
            menuDocument.description = document.data["description"].toString()
            menuDocument.image = document.data["image"].toString()
            basicMenuResponse.documents.add(menuDocument)
          }
          channel.offer(basicMenuResponse)
        }
        .addOnFailureListener {
          Log.d(TAG, it.toString())
          channel.close(it)
        }
      awaitClose()
    }
  }

  override fun getMenuCategories(): Flow<CategoryResponse?> {
    val categoryResponse = CategoryResponse(ArrayList())
    return channelFlow {
      firestoreDB.collection(ApiConstants.MENU_COLLECTION).get()
        .addOnSuccessListener { result ->
          for(document in result) {
            val categoryName = CategoryName("")
            categoryName.name = document.id
            categoryResponse.categoryName?.add(categoryName)
            Log.d(TAG, document.data.toString())
          }
          channel.offer(categoryResponse)
        }
        .addOnFailureListener{
          Log.d(TAG, it.toString())
          channel.close()
        }
      awaitClose()
    }
  }
}