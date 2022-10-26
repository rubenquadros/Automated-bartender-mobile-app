package com.ruben.bartender.data.repository

import com.ruben.bartender.base.DispatcherProvider
import com.ruben.bartender.data.DataSource
import com.ruben.bartender.data.remote.model.request.MakeDrinkRequest
import com.ruben.bartender.data.repository.mapper.toMakeDrinkBaseRecord
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MakeDrinkRecord
import com.ruben.bartender.domain.repository.DrinkRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext

/**
 * Created by ruben.quadros on 05/03/20.
 **/
class DrinkRepositoryImpl @Inject constructor(
    dataSource: DataSource,
    private val dispatcherProvider: DispatcherProvider
) : DrinkRepository {

    private val restApi = dataSource.api().apiHandler()

    override suspend fun makeDrink(drinkName: String): BaseRecord<MakeDrinkRecord, ErrorRecord> {
        val response = restApi.makeDrink(MakeDrinkRequest(drinkName))
        return withContext(dispatcherProvider.default) {
            response.toMakeDrinkBaseRecord()
        }
    }
}