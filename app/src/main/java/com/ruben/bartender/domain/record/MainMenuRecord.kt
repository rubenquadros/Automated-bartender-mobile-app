package com.ruben.bartender.domain.record

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by ruben.quadros on 10/03/20.
 **/
@Parcelize
data class MainMenuRecord(
    val menuRecord: List<MenuItem>
) : Parcelable

@Parcelize
data class MenuItem(
    val name: String,
    val image: String,
    val price: String,
    val id: String
) : Parcelable