package com.ruben.bartender.domain.record

import android.os.Parcelable
import com.ruben.bartender.domain.CollectionWrapper
import kotlinx.parcelize.Parcelize

/**
 * Created by ruben.quadros on 10/03/20.
 **/
@Parcelize
data class MainMenuRecord(
    val menuRecordWrapper: CollectionWrapper<MenuItem>
) : Parcelable {

    fun getCollection(): List<MenuItem> = menuRecordWrapper.list as List<MenuItem>
}

@Parcelize
data class MenuItem(
    val name: String,
    val image: String,
    val price: String,
    val id: String
) : Parcelable