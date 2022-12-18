package com.ruben.bartender.data.remote

import com.ruben.bartender.data.remote.firebase.FirebaseApi
import com.ruben.bartender.data.remote.rest.RestApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class NetworkManagerImpl @Inject constructor(
    private val firebaseApi: FirebaseApi,
    private val restApi: RestApi
) : NetworkManager {

    override fun apiHandler(): RestApi {
        return restApi
    }

    override fun firebaseApiHandler(): FirebaseApi {
        return firebaseApi
    }
}