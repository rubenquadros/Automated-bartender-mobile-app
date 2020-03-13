package com.ruben.domain.repository

import com.ruben.domain.model.SaveUserRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 11/03/20.
 **/
interface SignUpRepository {
  fun saveUserDetails(firstName: String, lastName: String, phoneNumber: String): Flow<SaveUserRecord?>
}