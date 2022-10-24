package com.ruben.bartender.presentation.ui.drinkDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@Parcelize
data class DrinkDetailsState(
    val test: Boolean = false
): Parcelable
