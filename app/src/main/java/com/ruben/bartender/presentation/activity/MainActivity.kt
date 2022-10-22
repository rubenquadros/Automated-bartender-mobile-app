package com.ruben.bartender.presentation.activity

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ruben.bartender.presentation.ElBarman
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private var isNotReady = true
    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        //observe state
        observeState()

        splashScreen.setKeepOnScreenCondition {
            isNotReady
        }

        setContent {
            ElBarmanTheme {
                ElBarman(isLoggedIn = isLoggedIn)
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState().collect {
                    isNotReady = it.isNotReady
                    isLoggedIn = it.isUserLoggedIn
                }
            }
        }
    }
}