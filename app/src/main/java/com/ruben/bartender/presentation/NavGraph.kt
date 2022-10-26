package com.ruben.bartender.presentation

import androidx.navigation.NavHostController

/**
 * Created by Ruben Quadros on 22/10/22
 **/
class NavGraph(navHostController: NavHostController) {
    val navigateBack: () -> Unit = {
        navHostController.navigateUp()
    }

    val openLogin: () -> Unit = {
        navHostController.navigate(route = Destination.Login.Route)
    }

    val openSignUp: (number: String) -> Unit = { number ->
        navHostController.navigate(route = Destination.SignUp.createRoute(number))
    }

    val openHome: () -> Unit = {
        navHostController.navigate(route = Destination.Home.Route)
    }

    val openPayment: (drinkName: String, price: String) -> Unit = { name, price ->
        navHostController.navigate(route = Destination.Payment.createRoute(name, price))
    }
}