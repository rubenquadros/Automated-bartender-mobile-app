package com.ruben.domain.repository

import com.ruben.domain.model.SignOutRecord
import com.ruben.domain.model.UserRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 10/03/20.
 **/
interface UserRepository {
  fun isLoggedIn(): Boolean
  fun isRegistered(): Boolean
  fun phoneNumber(): String?
  fun getUserData(phoneNumber: String): Flow<UserRecord?>
  fun logout(): Flow<SignOutRecord?>
}