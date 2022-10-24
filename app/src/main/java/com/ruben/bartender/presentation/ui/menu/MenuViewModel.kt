package com.ruben.bartender.presentation.ui.menu

import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.interactor.menu.GetMainMenuUseCase
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MainMenuRecord
import com.ruben.bartender.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

/**
 * Created by Ruben Quadros on 23/10/22
 **/
@HiltViewModel
class MenuViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMainMenuUseCase: GetMainMenuUseCase
) : BaseViewModel<MenuState, MenuSideEffect>(savedStateHandle) {

    override fun createInitialState(): MenuState = MenuState.InitialState

    override fun initData() {
        super.initData()
        getBarMenu()
    }

    private fun getBarMenu() = intent {
        getMainMenuUseCase(Unit).collect { baseRecord: BaseRecord<MainMenuRecord, ErrorRecord> ->
            when(baseRecord) {
                is BaseRecord.Loading -> {
                    reduce { MenuState.LoadingState }
                }
                is BaseRecord.Success -> {
                    reduce {
                        if (baseRecord.body.menuRecord.isNotEmpty()) {
                            MenuState.MainMenuState(mainMenu = baseRecord.body)
                        } else {
                            MenuState.ErrorState
                        }
                    }
                }
                else -> {
                    reduce { MenuState.ErrorState }
                }
            }
        }
    }
}