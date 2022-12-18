package com.ruben.bartender.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruben.bartender.data.local.dao.UserDao
import com.ruben.bartender.data.local.entity.UserEntity

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@Database(
    entities = [UserEntity::class],
    exportSchema = true,
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "elbarman.db"
    }

    abstract fun userDAO(): UserDao

}