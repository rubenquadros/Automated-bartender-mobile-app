package com.ruben.bartender.presentation.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ruben.bartender.R
import com.ruben.bartender.domain.CollectionWrapper

/**
 * Created by Ruben Quadros on 23/10/22
 **/
sealed class NavigationItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int,
    val route: String
) {

    companion object {
        const val MENU_ROUTE = "home/menu"
        const val PROFILE_ROUTE = "landing/profile"
    }

    object Menu : NavigationItem(
        title = R.string.bottom_navigation_title_menu,
        icon = R.drawable.ic_menu_book,
        contentDescription = R.string.content_desc_menu,
        route = MENU_ROUTE
    )

    object Profile : NavigationItem(
        title = R.string.bottom_navigation_title_profile,
        icon = R.drawable.ic_account,
        contentDescription = R.string.content_desc_profile,
        route = PROFILE_ROUTE
    )
}

fun getNavigationItems() = CollectionWrapper(
    listOf(
        NavigationItem.Menu,
        NavigationItem.Profile
    )
)