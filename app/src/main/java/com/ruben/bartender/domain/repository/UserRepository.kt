package com.ruben.bartender.domain.repository

import com.ruben.bartender.domain.record.SignOutRecord
import com.ruben.bartender.domain.record.UserLoginStatus
import com.ruben.bartender.domain.record.UserRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 10/03/20.
 **/
interface UserRepository {
  suspend fun isLoggedIn(): UserLoginStatus
  fun isRegistered(): Boolean
  fun phoneNumber(): String?
  fun getUserData(phoneNumber: String): Flow<UserRecord?>
  fun logout(): Flow<SignOutRecord?>
}