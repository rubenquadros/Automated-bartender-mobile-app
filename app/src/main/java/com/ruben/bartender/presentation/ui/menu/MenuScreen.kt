package com.ruben.bartender.presentation.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ruben.bartender.R
import com.ruben.bartender.domain.record.MainMenuRecord
import com.ruben.bartender.domain.record.MenuItem
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.common.ErrorView
import com.ruben.bartender.presentation.ui.common.GetButton
import com.ruben.bartender.presentation.ui.common.LoadingView

/**
 * Created by Ruben Quadros on 23/10/22
 **/
@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = hiltViewModel(),
    navigateToDetails: (drinkId: String) -> Unit,
    navigateToLogin: () -> Unit,
    navigateToPayment: (drinkName: String, price: String) -> Unit
) {

    //handle side effects
    LaunchedEffect(menuViewModel.uiSideEffect()) {
        menuViewModel.uiSideEffect().collect { uiSideEffect ->
            when (uiSideEffect) {
                MenuSideEffect.NavigateToLogin -> {
                    navigateToLogin()
                }
                is MenuSideEffect.NavigateToPayment -> {
                    navigateToPayment(uiSideEffect.name, uiSideEffect.price)
                }
            }
        }
    }

    val menuState by menuViewModel.uiState().collectAsState()

    when (menuState) {
        MenuState.InitialState -> {
            //do nothing
        }

        is MenuState.LoadingState -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is MenuState.MainMenuState -> {
            (menuState as? MenuState.MainMenuState)?.let {
                MenuContent(
                    mainMenuRecord = it.mainMenu,
                    onItemClick = navigateToDetails,
                    onGetDrinkClick = menuViewModel::onGetDrink
                )
            }
        }

        MenuState.ErrorState -> {
            ErrorView(
                modifier = Modifier.fillMaxSize(),
                errorMessage = stringResource(id = R.string.menu_error),
                buttonText = stringResource(id = R.string.all_retry),
                onRetry = menuViewModel::getBarMenu
            )
        }
    }
}

@Composable
private fun MenuContent(
    modifier: Modifier = Modifier,
    mainMenuRecord: MainMenuRecord,
    onItemClick: (drinkId: String) -> Unit,
    onGetDrinkClick: (drinkName: String, price: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(count = 2)
    ) {
        items(items = mainMenuRecord.menuRecord, key = { item: MenuItem -> item.id }) {
            MenuItemUI(menuItem = it, onItemClick = onItemClick, onGetDrinkClick = onGetDrinkClick)
        }
    }
}

@Composable
private fun MenuItemUI(
    menuItem: MenuItem,
    onItemClick: (drinkId: String) -> Unit,
    onGetDrinkClick: (drinkName: String, price: String) -> Unit
) {
    Card(
        modifier = menuItemModifier.clickable {
            onItemClick(menuItem.id)
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        colors = CardDefaults.cardColors(containerColor = ElBarmanTheme.colors.onPrimary)
    ) {
        Column {
            Image(
                modifier = menuItemImageModifier,
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(menuItem.image)
                        .crossfade(true)
                        .build(),
                    placeholder = rememberAsyncImagePainter(model = R.drawable.ic_menu_item_placeholder)
                ),
                contentDescription = menuItem.name
            )

            Text(
                modifier = menuItemNameModifier,
                text = menuItem.name,
                style = ElBarmanTheme.typography.headingSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = ElBarmanTheme.colors.primaryVariant
            )

            Row(
                modifier = menuItemPriceRowModifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = stringResource(id = R.string.menu_price, menuItem.price),
                    color = ElBarmanTheme.colors.primaryVariant,
                    style = ElBarmanTheme.typography.bodyMedium
                )

                GetButton(
                    modifier = Modifier,
                    onClick = { onGetDrinkClick(menuItem.name, menuItem.price) }
                )
            }
        }
    }
}

private val menuItemModifier = Modifier
    .padding(8.dp)
    .fillMaxWidth()

private val menuItemImageModifier = Modifier
    .height(150.dp)
    .fillMaxWidth()

private val menuItemNameModifier = Modifier.padding(8.dp)

private val menuItemPriceRowModifier = Modifier
    .padding(8.dp)
    .fillMaxWidth()

@Preview(showBackground = true)
@Composable
private fun PreviewMenuItem() {
    MenuItemUI(
        menuItem = MenuItem(name = "ScrewDriver", image = "", price = "400", id = "abc"),
        onItemClick = {},
        onGetDrinkClick = {_, _ ->}
    )
}