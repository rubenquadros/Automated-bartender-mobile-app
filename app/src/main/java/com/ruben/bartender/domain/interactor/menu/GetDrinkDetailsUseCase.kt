package com.ruben.bartender.domain.interactor.menu

import com.ruben.bartender.domain.BaseFlowUseCase
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.DrinkDetailsRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.repository.MenuRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Ruben Quadros on 24/10/22
 **/
class GetDrinkDetailsUseCase @Inject constructor(private val menuRepository: MenuRepository) :
    BaseFlowUseCase<GetDrinkDetailsUseCase.Params, DrinkDetailsRecord, ErrorRecord>() {

    override suspend fun execute(request: Params): Flow<BaseRecord<DrinkDetailsRecord, ErrorRecord>> = flow {
        emit(BaseRecord.Loading)
        emit(menuRepository.getDrinkDetails(drinkId = request.drinkId))
    }

    data class Params(val drinkId: String)
}