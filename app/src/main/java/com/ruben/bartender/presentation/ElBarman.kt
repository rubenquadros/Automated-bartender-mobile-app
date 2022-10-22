package com.ruben.bartender.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.login.LoginScreen
import com.ruben.bartender.presentation.ui.signup.SignUpScreen

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ElBarman(isLoggedIn: Boolean) {
    val navController = rememberAnimatedNavController()
    val navGraph = remember(navController) { NavGraph(navController) }
    val home = if (isLoggedIn) "" else Destination.LOGIN.Route

    AnimatedNavHost(navController = navController, startDestination = home) {
        composable(route = Destination.LOGIN.Route) {
            StatusBarColor()
            LoginScreen()
        }

        composable(route = Destination.SIGNUP.Route) {
            StatusBarColor()
            SignUpScreen()
        }

        composable(route = Destination.HOME.Route) {
            StatusBarColor()
        }
    }
}

@Composable
private fun StatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val systemBarsColor = ElBarmanTheme.colors.primary
    SideEffect {
        systemUiController.setSystemBarsColor(systemBarsColor)
    }
}