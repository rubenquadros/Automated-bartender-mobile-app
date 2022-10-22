package com.ruben.bartender.presentation.activity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@Parcelize
data class MainState(
    val isNotReady: Boolean = true,
    val isUserLoggedIn: Boolean = false
):Parcelable
