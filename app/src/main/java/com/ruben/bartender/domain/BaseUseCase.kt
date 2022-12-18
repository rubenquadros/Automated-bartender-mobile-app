package com.ruben.bartender.domain

import com.ruben.bartender.base.DispatcherProvider
import com.ruben.bartender.base.dispatcherProvider
import com.ruben.bartender.base.logException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

/**
 * Created by Ruben Quadros on 22/10/22
 **/
abstract class BaseFlowUseCase<REQUEST, RECORD, ERROR>(private val dispatcher: DispatcherProvider = dispatcherProvider) {

    abstract suspend fun execute(request: REQUEST): Flow<BaseRecord<RECORD, ERROR>>

    suspend operator fun invoke(request: REQUEST): Flow<BaseRecord<RECORD, ERROR>> {
        return withContext(dispatcher.io) {
            execute(request = request)
        }.catch {
            logException(it)
            emit(BaseRecord.UnknownError)
        }
    }
}

abstract class BasePreferenceUseCase<RECORD>(private val dispatcher: DispatcherProvider = dispatcherProvider) {

    abstract suspend fun execute(): RECORD

    suspend operator fun invoke(): RECORD {
        return withContext(dispatcher.io) {
            execute()
        }
    }
}