package com.ruben.bartender.domain.interactor.drink

import com.ruben.bartender.domain.BaseFlowUseCase
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MakeDrinkRecord
import com.ruben.bartender.domain.repository.DrinkRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by ruben.quadros on 05/03/20.
 **/
class MakeDrinkUseCase @Inject constructor(private val drinkRepository: DrinkRepository) :
    BaseFlowUseCase<MakeDrinkUseCase.Params, MakeDrinkRecord, ErrorRecord>() {

    override suspend fun execute(request: Params): Flow<BaseRecord<MakeDrinkRecord, ErrorRecord>> =
        flow {
            emit(BaseRecord.Loading)
            emit(drinkRepository.makeDrink(drinkName = request.drinkName))
        }

    data class Params(
        val drinkName: String
    )

}