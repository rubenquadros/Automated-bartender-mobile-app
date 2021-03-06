package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.data.mapper.DrinkMapper
import com.ruben.domain.model.MakeDrinkRecord
import com.ruben.domain.repository.DrinkRepository
import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.utils.ApiConstants
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