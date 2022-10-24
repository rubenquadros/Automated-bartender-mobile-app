package com.ruben.bartender.base

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@[Module InstallIn(SingletonComponent::class)]
internal interface CoroutinesModule {

    @[Binds Singleton]
    fun bindDispatcherProvider(dispatcherProvider: DispatcherProviderImpl): DispatcherProvider
}