package com.ruben.bartender.data.remote

import com.ruben.bartender.data.remote.firebase.FirebaseApi
import com.ruben.bartender.data.remote.rest.RestApi

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface NetworkManager {
  fun apiHandler(): RestApi
  fun firebaseApiHandler(): FirebaseApi
}