package com.ruben.bartender.data.local

import com.ruben.bartender.data.local.entity.UserEntity
import javax.inject.Inject

/**
 * Created by ruben.quadros on 12/03/20.
 **/
class DataBaseManagerImpl @Inject constructor(private val appDb: AppDatabase) : DatabaseManager {
    override suspend fun insertUser(userEntity: UserEntity) {
        appDb.userDAO().insertUser(userEntity = userEntity)
    }

    override suspend fun updateUser(userEntity: UserEntity) {
        appDb.userDAO().updateUser(
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            phoneNumber = userEntity.phoneNumber
        )
    }
}