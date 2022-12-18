package com.ruben.bartender.data.local

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@Singleton
class AppDatabaseFactory @Inject constructor(@ApplicationContext private val context: Context) {

    fun getDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration().build()
    }
}