package com.ruben.bartender.presentation.ui.home

import androidx.navigation.NavHostController
import com.ruben.bartender.presentation.IDestination

/**
 * Created by Ruben Quadros on 24/10/22
 **/
//Bottom sheets
object HomeBottomSheets {
    object DrinkDetails: IDestination {
        const val DrinkIdArg = "drinkId"
        const val Route = "drinkDetails/{$DrinkIdArg}"
        override fun createRoute(vararg args: String): String = "drinkDetails/${args[0]}"
    }
}

class HomeNavGraph(navHostController: NavHostController) {

    val openDrinkDetails: (drinkId: String) -> Unit = { id ->
        navHostController.navigate(route = HomeBottomSheets.DrinkDetails.createRoute(id))
    }
}