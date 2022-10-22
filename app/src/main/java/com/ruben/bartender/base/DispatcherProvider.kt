package com.ruben.bartender.base

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Ruben Quadros on 22/10/22
 **/
interface DispatcherProvider {

    val main: CoroutineDispatcher

    val io: CoroutineDispatcher

    val default: CoroutineDispatcher

    val mainImmediate: CoroutineDispatcher

    val unconfined: CoroutineDispatcher

}

@Singleton
class DispatcherProviderImpl @Inject constructor(): DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val mainImmediate: CoroutineDispatcher
        get() = Dispatchers.Main.immediate
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}

val dispatcherProvider: DispatcherProvider by lazy {
    DispatcherProviderImpl()
}