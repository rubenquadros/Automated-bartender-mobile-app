package com.ruben.domain.interactor

import com.ruben.domain.repository.DrinkRepository
import com.ruben.remote.model.request.MakeDrinkRequest
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import kotlinx.coroutines.Deferred
import javax.inject.Inject

/**
 * Created by ruben.quadros on 05/03/20.
 **/
@Suppress("DeferredIsResult")
class MakeDrinkUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {

  fun makeDrink(makeDrinkRequest: MakeDrinkRequest): Deferred<MakeDrinkResponse?> {
    return drinkRepository.makeDrink(makeDrinkRequest)
  }
}