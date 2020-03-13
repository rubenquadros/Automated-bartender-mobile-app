package com.ruben.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ruben.cache.dao.UserDao
import com.ruben.cache.entity.User

/**
 * Created by ruben.quadros on 12/03/20.
 **/
@Database(
  entities = [User::class],
  version = Migrations.DB_VERSION,
  exportSchema = true
)
abstract class ConsumerDB: RoomDatabase() {

  abstract fun userDAO(): UserDao

  companion object {

    private const val DB_NAME = "consumer.db"

    @Volatile
    private var INSTANCE: ConsumerDB? = null

    fun getInstance(context: Context): ConsumerDB =
      INSTANCE ?: buildDatabase(context).also { INSTANCE = it }


    private fun buildDatabase(context: Context) = Room.databaseBuilder(
      context.applicationContext, ConsumerDB::class.java, DB_NAME
    ).fallbackToDestructiveMigration().build()
  }
}