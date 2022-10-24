package com.ruben.bartender.presentation

import androidx.navigation.NavHostController

/**
 * Created by Ruben Quadros on 22/10/22
 **/
class NavGraph(navHostController: NavHostController) {
    val navigateBack: () -> Unit = {
        navHostController.navigateUp()
    }

    val openSignUp: (number: String) -> Unit = { number ->
        navHostController.navigate(route = Destination.SIGNUP.createRoute(number))
    }

    val openHome: () -> Unit = {
        navHostController.navigate(route = Destination.HOME.Route)
    }
}