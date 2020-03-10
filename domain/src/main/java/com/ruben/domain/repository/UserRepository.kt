package com.ruben.domain.repository

/**
 * Created by ruben.quadros on 10/03/20.
 **/
interface UserRepository {
  fun isLoggedIn(): Boolean
  fun isRegistered(): Boolean
}