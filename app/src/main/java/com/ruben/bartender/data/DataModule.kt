package com.ruben.bartender.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@[Module InstallIn(SingletonComponent::class)]
internal interface DataModule {

    @[Binds Singleton]
    fun bindDataSource(dataSource: DataSourceImpl): DataSource
}