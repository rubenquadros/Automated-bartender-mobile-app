package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.data.mapper.DrinkMapper
import com.ruben.domain.model.MakeDrinkRecord
import com.ruben.domain.repository.DrinkRepository
import com.ruben.remote.model.request.MakeDrinkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@ExperimentalCoroutinesApi
class DrinkRepositoryImpl @Inject constructor(dataSource: DataSource) : DrinkRepository {

  private val restApi = dataSource.api().apiHandler()
  private val drinkMapper = DrinkMapper()

  override suspend fun makeDrink(drinkName: String): MakeDrinkRecord? {
    return drinkMapper.mapMakeDrinkResponse(restApi.makeDrink(MakeDrinkRequest(drinkName)).await())
  }
}