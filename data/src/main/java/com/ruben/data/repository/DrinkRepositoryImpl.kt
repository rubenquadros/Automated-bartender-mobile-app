package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.domain.repository.DrinkRepository
import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred
import javax.inject.Inject

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@Suppress("DeferredIsResult")
class DrinkRepositoryImpl @Inject constructor(dataSource: DataSource) : DrinkRepository {

  private val restApi = dataSource.api().apiHandler()

  override fun makeDrink(makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse?> {
    return restApi.makeDrink(makeDrinkRequest)
  }
}