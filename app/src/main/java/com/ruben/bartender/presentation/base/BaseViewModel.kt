package com.ruben.bartender.presentation.base

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

/**
 * Created by Ruben Quadros on 22/10/22
 **/
abstract class BaseViewModel<STATE : Parcelable, SIDE_EFFECT : Any>(
    protected val savedStateHandle: SavedStateHandle
) : ContainerHost<STATE, SIDE_EFFECT>, ViewModel() {

    override val container: Container<STATE, SIDE_EFFECT> by lazy {
        container(
            initialState = createInitialState(),
            savedStateHandle = savedStateHandle,
            onCreate = { initData() }
        )
    }

    abstract fun createInitialState(): STATE

    open fun initData() = intent { }

    fun uiState() = container.stateFlow

    fun uiSideEffect() = container.sideEffectFlow

}