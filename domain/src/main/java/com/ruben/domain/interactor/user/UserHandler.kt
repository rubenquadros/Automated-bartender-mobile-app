package com.ruben.domain.interactor.user

/**
 * Created by ruben.quadros on 10/03/20.
 **/
interface UserHandler {
  fun isLoggedIn(): Boolean
  fun isRegistered(): Boolean
}