package com.ruben.bartender.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.home.HomeScreen
import com.ruben.bartender.presentation.ui.login.LoginScreen
import com.ruben.bartender.presentation.ui.payment.PaymentScreen
import com.ruben.bartender.presentation.ui.signup.SignUpScreen

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun ElBarman() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController(bottomSheetNavigator)
    val navGraph = remember(navController) { NavGraph(navController) }

    AnimatedNavHost(navController = navController, startDestination = Destination.Home.Route) {
        composable(route = Destination.Login.Route) {
            StatusBarColor()
            LoginScreen(
                navigateToHome = navGraph.openHome,
                navigateToSignUp = navGraph.openSignUp
            )
        }

        composable(
            route = Destination.SignUp.Route,
            arguments = listOf(
                navArgument(name = Destination.SignUp.PhoneArg) { type = NavType.StringType }
            )
        ) {
            StatusBarColor()
            SignUpScreen(
                navigateToHome = navGraph.openHome
            )
        }

        composable(route = Destination.Home.Route) {
            StatusBarColor()
            HomeScreen()
        }

        composable(
            route = Destination.Payment.Route,
            arguments = listOf(
                navArgument(name = Destination.Payment.PriceArg) { type = NavType.StringType },
                navArgument(name = Destination.Payment.DrinkNameArg) { type = NavType.StringType }
            )
        ) {
            StatusBarColor()
            PaymentScreen(navigateUp = navGraph.navigateBack)
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