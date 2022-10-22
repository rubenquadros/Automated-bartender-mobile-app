package com.ruben.bartender.presentation.activity

import androidx.lifecycle.SavedStateHandle
import com.ruben.bartender.domain.interactor.user.GetLoginStatusUseCase
import com.ruben.bartender.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLoginStatusUseCase: GetLoginStatusUseCase
) : BaseViewModel<MainState, Nothing>(savedStateHandle) {

    override fun createInitialState(): MainState = MainState()

    override fun initData() {
        super.initData()
        checkLoginState()
    }

    private fun checkLoginState() = intent {
        val userLoginStatus = getLoginStatusUseCase()
        reduce { state.copy(isNotReady = false, isUserLoggedIn = userLoginStatus.isLoggedIn) }
    }
}