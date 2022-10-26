package com.ruben.bartender.presentation.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ruben.bartender.R
import com.ruben.bartender.presentation.Destination
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.common.AppBar
import com.ruben.bartender.presentation.ui.drinkDetails.DrinkDetailsScreen
import com.ruben.bartender.presentation.ui.menu.MenuScreen

/**
 * Created by Ruben Quadros on 23/10/22
 **/
@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun HomeScreen(
    navigateToLogin: () -> Unit,
    navigateToPayment: (drinkName: String, price: String) -> Unit
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController(bottomSheetNavigator)
    val homeNavGraph = remember(navController) { HomeNavGraph(navController) }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        scrimColor = Color.Transparent,
        sheetBackgroundColor = ElBarmanTheme.colors.onPrimary,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    title = stringResource(id = R.string.all_home)
                )
            },
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                HomeBottomNavigation(
                    items = getNavigationItems(),
                    onClick = { route: String ->
                        navController.navigate(route = route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    getCurrentScreen = { currentRoute.orEmpty() }
                )
            }
        ) { innerPadding: PaddingValues ->
            AnimatedNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = NavigationItem.MENU_ROUTE
            ) {
                composable(route = NavigationItem.MENU_ROUTE) {
                    MenuScreen(
                        navigateToDetails = homeNavGraph.openDrinkDetails,
                        navigateToLogin = navigateToLogin,
                        navigateToPayment = navigateToPayment
                    )
                }

                composable(route = NavigationItem.PROFILE_ROUTE) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(ElBarmanTheme.colors.onPrimaryVariant))
                }

                //bottom sheets
                bottomSheet(
                    route = HomeBottomSheets.DrinkDetails.Route,
                    arguments = listOf(
                        navArgument(name = HomeBottomSheets.DrinkDetails.DrinkIdArg) { type = NavType.StringType }
                    )
                ) {
                    DrinkDetailsScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    HomeScreen(
        navigateToLogin = {},
        navigateToPayment = {_,_ ->}
    )
}