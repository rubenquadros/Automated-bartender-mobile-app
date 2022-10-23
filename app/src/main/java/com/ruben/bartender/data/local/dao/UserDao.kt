package com.ruben.bartender.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruben.bartender.data.local.entity.UserEntity

/**
 * Created by ruben.quadros on 12/03/20.
 **/
@Dao
interface UserDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertUser(userEntity: UserEntity)

  @Query("UPDATE `user` SET `first_name` = :firstName, `last_name` = :lastName WHERE `phone_number` = :phoneNumber")
  suspend fun updateUser(firstName: String, lastName: String, phoneNumber: String)
}