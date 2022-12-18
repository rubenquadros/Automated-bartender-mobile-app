package com.ruben.bartender.data.preference

/**
 * Created by ruben.quadros on 11/03/20.
 **/
interface PreferenceManager {
  suspend fun isUserLoggedIn(): Boolean
  suspend fun setUserLoggedIn(isLoggedIn: Boolean)
}