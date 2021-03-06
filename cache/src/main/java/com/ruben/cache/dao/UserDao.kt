package com.ruben.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ruben.cache.entity.User

/**
 * Created by ruben.quadros on 12/03/20.
 **/
@Dao
interface UserDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveUser(user: User)
}