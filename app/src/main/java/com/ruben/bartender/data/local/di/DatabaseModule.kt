package com.ruben.bartender.data.local.di

import com.ruben.bartender.data.local.AppDatabase
import com.ruben.bartender.data.local.AppDatabaseFactory
import com.ruben.bartender.data.local.DataBaseManagerImpl
import com.ruben.bartender.data.local.DatabaseManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@[Module InstallIn(SingletonComponent::class)]
internal object DatabaseModule {

    @[Provides Singleton]
    fun provideAppDb(appDatabaseFactory: AppDatabaseFactory): AppDatabase {
        return appDatabaseFactory.getDatabase()
    }
}

@[Module InstallIn(SingletonComponent::class)]
internal interface DatabaseManagerModule {
    @[Binds Singleton]
    abstract fun bindDatabaseManager(databaseManager: DataBaseManagerImpl): DatabaseManager
}