package com.ruben.bartender.presentation.ui.home

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.domain.CollectionWrapper
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 23/10/22
 **/
@Composable
fun HomeBottomNavigation(
    items: CollectionWrapper<NavigationItem>,
    onClick: (route: String) -> Unit,
    getCurrentScreen: () -> String
) {
    NavigationBar(
        containerColor = ElBarmanTheme.colors.primary,
    ) {
        items.list.forEach { navigationItem: NavigationItem ->
            NavigationBarItem(
                selected = navigationItem.route == getCurrentScreen.invoke(),
                onClick = { onClick(navigationItem.route) },
                icon = {
                    Icon(
                        painter = rememberAsyncImagePainter(model = navigationItem.icon),
                        contentDescription = stringResource(id = navigationItem.contentDescription)
                    )
                },
                label = {
                    Text(text = stringResource(id = navigationItem.title))
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ElBarmanTheme.colors.onPrimary,
                    selectedTextColor = ElBarmanTheme.colors.onPrimary,
                    unselectedIconColor = ElBarmanTheme.colors.surface,
                    unselectedTextColor = ElBarmanTheme.colors.surface,
                    indicatorColor = ElBarmanTheme.colors.onPrimaryVariant
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeBottomNavigation() {
    HomeBottomNavigation(
        items = getNavigationItems(),
        onClick = {},
        getCurrentScreen = { NavigationItem.MENU_ROUTE }
    )
}