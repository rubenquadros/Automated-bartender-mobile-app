package com.ruben.domain.repository

import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@Suppress("DeferredIsResult")
interface DrinkRepository {
  fun makeDrink(makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse?>
}