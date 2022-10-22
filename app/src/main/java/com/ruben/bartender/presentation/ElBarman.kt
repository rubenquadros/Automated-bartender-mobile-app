package com.ruben.bartender.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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
            LoginScreen()
        }

        composable(route = Destination.SIGNUP.Route) {
            SignUpScreen()
        }

        composable(route = Destination.HOME.Route) {

        }
    }
}