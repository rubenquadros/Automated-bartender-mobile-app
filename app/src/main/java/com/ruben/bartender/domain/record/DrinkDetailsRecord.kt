package com.ruben.bartender.domain.record

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@Parcelize
data class DrinkDetailsRecord(
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val ingredients: String
) : Parcelable
