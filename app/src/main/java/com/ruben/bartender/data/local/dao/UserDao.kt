package com.ruben.bartender.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ruben.bartender.data.local.entity.UserEntity

/**
 * Created by ruben.quadros on 12/03/20.
 **/
@Dao
interface UserDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveUser(user: UserEntity)
}