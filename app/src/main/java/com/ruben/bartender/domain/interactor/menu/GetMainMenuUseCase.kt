package com.ruben.bartender.domain.interactor.menu

import com.ruben.bartender.domain.BaseFlowUseCase
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MainMenuRecord
import com.ruben.bartender.domain.repository.MenuRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Ruben Quadros on 23/10/22
 **/
class GetMainMenuUseCase @Inject constructor(private val menuRepository: MenuRepository) :
    BaseFlowUseCase<Unit, MainMenuRecord, ErrorRecord>() {

    override suspend fun execute(request: Unit): Flow<BaseRecord<MainMenuRecord, ErrorRecord>> =
        flow {
            emit(BaseRecord.Loading)
            menuRepository.getMainMenu()
        }
}