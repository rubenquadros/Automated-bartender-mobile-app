package com.ruben.bartender.data.local

import com.ruben.bartender.data.local.entity.UserEntity

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface DatabaseManager {
  suspend fun insertUser(userEntity: UserEntity)
  suspend fun updateUser(userEntity: UserEntity)
}