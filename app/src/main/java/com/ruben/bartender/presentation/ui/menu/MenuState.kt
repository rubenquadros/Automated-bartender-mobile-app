package com.ruben.bartender.presentation.ui.menu

import android.os.Parcelable
import com.ruben.bartender.domain.record.MainMenuRecord
import kotlinx.parcelize.Parcelize

/**
 * Created by Ruben Quadros on 23/10/22
 **/

sealed class MenuState : Parcelable {

    @Parcelize
    object InitialState : MenuState()

    @Parcelize
    object LoadingState : MenuState()

    @Parcelize
    data class MainMenuState(
        val mainMenu: MainMenuRecord = MainMenuRecord(emptyList()),
        val shouldShowDetails: Boolean = false
    ) : MenuState()

    @Parcelize
    object ErrorState : MenuState()
}
