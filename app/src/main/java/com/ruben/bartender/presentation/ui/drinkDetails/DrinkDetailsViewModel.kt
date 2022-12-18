package com.ruben.bartender.presentation.ui.drinkDetails

import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.interactor.menu.GetDrinkDetailsUseCase
import com.ruben.bartender.domain.record.DrinkDetailsRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.presentation.base.BaseViewModel
import com.ruben.bartender.presentation.ui.home.HomeBottomSheets
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDrinkDetailsUseCase: GetDrinkDetailsUseCase
) : BaseViewModel<DrinkDetailsState, DrinkDetailsSideEffect>(savedStateHandle) {

    override fun createInitialState(): DrinkDetailsState = DrinkDetailsState.InitialState

    override fun initData() {
        super.initData()
        savedStateHandle.get<String>(HomeBottomSheets.DrinkDetails.DrinkIdArg)?.let {
            getDrinkDetails(drinkId = it)
        }
    }

    private fun getDrinkDetails(drinkId: String) = intent {
        getDrinkDetailsUseCase(
            GetDrinkDetailsUseCase.Params(drinkId = drinkId)
        ).collect { baseRecord: BaseRecord<DrinkDetailsRecord, ErrorRecord> ->
            when(baseRecord) {
                is BaseRecord.Loading -> {

                }
                is BaseRecord.Success -> {

                }
                else -> {

                }
            }
        }
    }
}