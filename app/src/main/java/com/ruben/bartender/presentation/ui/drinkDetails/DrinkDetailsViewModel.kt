package com.ruben.bartender.presentation.ui.drinkDetails

import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    BaseViewModel<DrinkDetailsState, DrinkDetailsSideEffect>(savedStateHandle) {

    override fun createInitialState(): DrinkDetailsState = DrinkDetailsState()

    override fun initData() {
        super.initData()
    }
}