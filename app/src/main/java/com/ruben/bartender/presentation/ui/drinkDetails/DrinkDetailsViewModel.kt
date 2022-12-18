package com.ruben.bartender.presentation.ui.drinkDetails

import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.interactor.menu.GetDrinkDetailsUseCase
import com.ruben.bartender.domain.interactor.user.GetLoginStatusUseCase
import com.ruben.bartender.domain.record.DrinkDetailsRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.presentation.base.BaseViewModel
import com.ruben.bartender.presentation.ui.home.HomeBottomSheets
import com.ruben.bartender.presentation.ui.menu.MenuSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDrinkDetailsUseCase: GetDrinkDetailsUseCase,
    private val getLoginStatusUseCase: GetLoginStatusUseCase
) : BaseViewModel<DrinkDetailsState, DrinkDetailsSideEffect>(savedStateHandle) {

    override fun createInitialState(): DrinkDetailsState = DrinkDetailsState.InitialState

    override fun initData() {
        super.initData()
        getDrinkDetails()
    }

    fun getDrinkDetails() = intent {
        val drinkId = savedStateHandle.get<String>(HomeBottomSheets.DrinkDetails.DrinkIdArg)
        drinkId?.let {
            getDrinkDetailsUseCase(
                GetDrinkDetailsUseCase.Params(drinkId = it)
            ).collect { baseRecord: BaseRecord<DrinkDetailsRecord, ErrorRecord> ->
                when(baseRecord) {
                    is BaseRecord.Loading -> {
                        reduce { DrinkDetailsState.LoadingState }
                    }
                    is BaseRecord.Success -> {
                        reduce {
                            DrinkDetailsState.DetailsState(drinkDetailsRecord = baseRecord.body)
                        }
                    }
                    else -> {
                        reduce { DrinkDetailsState.ErrorState }
                    }
                }
            }
        } ?: postSideEffect(DrinkDetailsSideEffect.DrinkIdMissing)
    }

    fun onGetDrink(drinkName: String, price: String) = intent {
        val loginStatus = getLoginStatusUseCase()
        if (loginStatus.isLoggedIn) {
            postSideEffect(DrinkDetailsSideEffect.NavigateToPayment(name = drinkName, price = price))
        } else {
            postSideEffect(DrinkDetailsSideEffect.NavigateToLogin)
        }
    }
}