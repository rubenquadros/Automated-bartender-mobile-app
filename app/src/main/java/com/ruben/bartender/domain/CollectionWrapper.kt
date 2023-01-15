package com.ruben.bartender.domain

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Created by Ruben Quadros on 14/01/23
 **/
@Immutable
@Parcelize
data class CollectionWrapper<T>(
    val list: @RawValue Collection<T>
) : Parcelable
