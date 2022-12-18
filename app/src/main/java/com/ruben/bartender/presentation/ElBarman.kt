package com.ruben.bartender.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
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
            SignUpScreen(
                navigateToHome = navGraph.openHome
            )
        }

        composable(route = Destination.Home.Route) {
            HomeScreen(
                navigateToLogin = navGraph.openLogin,
                navigateToPayment = navGraph.openPayment
            )
        }

        composable(
            route = Destination.Payment.Route,
            arguments = listOf(
                navArgument(name = Destination.Payment.PriceArg) { type = NavType.StringType },
                navArgument(name = Destination.Payment.DrinkNameArg) { type = NavType.StringType }
            )
        ) {
            PaymentScreen(navigateUp = navGraph.navigateBack)
        }
    }
}