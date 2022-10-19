package com.ruben.bartender.data.repository

import com.ruben.bartender.data.DataSource
import com.ruben.bartender.domain.model.MakeDrinkRecord
import com.ruben.bartender.domain.repository.DrinkRepository
import com.ruben.bartender.data.mapper.DrinkMapper
import com.ruben.bartender.data.remote.model.request.MakeDrinkRequest
import com.ruben.bartender.data.remote.utils.ApiConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception
import java.net.ConnectException
import javax.inject.Inject

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@ExperimentalCoroutinesApi
class DrinkRepositoryImpl @Inject constructor(dataSource: DataSource) : DrinkRepository {

  private val restApi = dataSource.api().apiHandler()
  private val drinkMapper = DrinkMapper()

  override suspend fun makeDrink(drinkName: String): MakeDrinkRecord? {
    return try {
      drinkMapper.mapMakeDrinkResponse(
        restApi.makeDrink(MakeDrinkRequest(drinkName)).await()
      )
    }catch (e: ConnectException) {
      val makeDrinkRecord = MakeDrinkRecord(0, "")
      makeDrinkRecord.responseCode = ApiConstants.HTTP_CON_ERROR
      makeDrinkRecord.responseMessage = ApiConstants.CON_ERROR
      makeDrinkRecord
    }catch (e: Exception) {
      val makeDrinkRecord = MakeDrinkRecord(0, "")
      makeDrinkRecord.responseCode = ApiConstants.HTTP_API_FAIL
      makeDrinkRecord.responseMessage = ApiConstants.FAIL
      makeDrinkRecord
    }
  }
}