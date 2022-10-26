package com.ruben.bartender.presentation.ui.drinkDetails

import android.os.Parcelable
import com.ruben.bartender.domain.record.DrinkDetailsRecord
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 24/10/22
 **/
sealed class DrinkDetailsState : Parcelable {

    @Parcelize
    object InitialState : DrinkDetailsState()

    @Parcelize
    object LoadingState : DrinkDetailsState()

    @Parcelize
    data class DetailsState(
        val drinkDetailsRecord: DrinkDetailsRecord
    ) : DrinkDetailsState()

    @Parcelize
    data class ErrorState(
        val message: String
    ) : DrinkDetailsState()
}
