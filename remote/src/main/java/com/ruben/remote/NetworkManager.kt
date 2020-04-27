package com.ruben.remote

import com.ruben.remote.firebase.FirebaseApi
import com.ruben.remote.rest.RestApi

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface NetworkManager {
  fun apiHandler(): RestApi
  fun firebaseApiHandler(): FirebaseApi
}