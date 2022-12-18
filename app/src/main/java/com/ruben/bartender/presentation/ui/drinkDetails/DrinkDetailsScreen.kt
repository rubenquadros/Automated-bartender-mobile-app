package com.ruben.bartender.presentation.ui.drinkDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.ruben.bartender.R
import com.ruben.bartender.domain.record.DrinkDetailsRecord
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.common.DividerView
import com.ruben.bartender.presentation.ui.common.ErrorView
import com.ruben.bartender.presentation.ui.common.GetButton
import com.ruben.bartender.presentation.ui.common.GetButtonFull
import com.ruben.bartender.presentation.ui.common.HtmlText
import com.ruben.bartender.presentation.ui.common.LoadingView

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@Composable
fun DrinkDetailsScreen(
    drinkDetailsViewModel: DrinkDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToPayment: (drinkName: String, price: String) -> Unit
) {

    //handle side effects
    LaunchedEffect(drinkDetailsViewModel.uiSideEffect()) {
        drinkDetailsViewModel.uiSideEffect().collect { uiSideEffect ->
            when (uiSideEffect) {
                DrinkDetailsSideEffect.DrinkIdMissing -> {
                    navigateBack()
                }
                DrinkDetailsSideEffect.NavigateToLogin -> {
                    navigateToLogin()
                }
                is DrinkDetailsSideEffect.NavigateToPayment -> {
                    navigateToPayment(uiSideEffect.name, uiSideEffect.price)
                }
            }
        }
    }

    val drinkDetailsState by drinkDetailsViewModel.uiState().collectAsState()

    when (drinkDetailsState) {
        DrinkDetailsState.InitialState -> {
            //do nothing
        }

        DrinkDetailsState.LoadingState ->  {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is DrinkDetailsState.DetailsState -> {
            (drinkDetailsState as? DrinkDetailsState.DetailsState)?.let {
                DrinkDetailsContent(
                    drinkDetailsRecord = it.drinkDetailsRecord,
                    onGetDrink = drinkDetailsViewModel::onGetDrink
                )
            }
        }

        DrinkDetailsState.ErrorState -> {
            ErrorView(
                errorMessage = stringResource(id = R.string.drink_details_error),
                buttonText = stringResource(id = R.string.all_retry),
                onRetry = drinkDetailsViewModel::getDrinkDetails
            )
        }
    }
}

@Composable
private fun DrinkDetailsContent(
    modifier: Modifier = Modifier,
    drinkDetailsRecord: DrinkDetailsRecord,
    onGetDrink: (drinkName: String, price: String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = drinkDetailsRecord.name,
            style = ElBarmanTheme.typography.headingLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ElBarmanTheme.colors.primaryVariant
        )

        Image(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(drinkDetailsRecord.image)
                    .crossfade(true)
                    .transformations(CircleCropTransformation())
                    .build(),
                placeholder = rememberAsyncImagePainter(model = R.drawable.ic_menu_item_placeholder)
            ),
            contentDescription = drinkDetailsRecord.name
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .align(Alignment.CenterVertically),
                text = stringResource(id = R.string.menu_price, drinkDetailsRecord.price),
                color = ElBarmanTheme.colors.primaryVariant,
                style = ElBarmanTheme.typography.bodyLarge
            )

            GetButton(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                onClick = { onGetDrink(drinkDetailsRecord.name, drinkDetailsRecord.price) }
            )
        }

        DividerView(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = drinkDetailsRecord.description,
            color = ElBarmanTheme.colors.primaryVariant,
            style = ElBarmanTheme.typography.bodyLarge
        )

        DividerView(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            text = stringResource(id = R.string.drink_details_ingredients),
            color = ElBarmanTheme.colors.primaryVariant,
            style = ElBarmanTheme.typography.headingLarge
        )

        HtmlText(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            textColor = R.color.colorPrimaryDark,
            fontSize = 16f,
            htmlText = drinkDetailsRecord.ingredients
        )

        GetButtonFull(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
            onClick = { onGetDrink(drinkDetailsRecord.name, drinkDetailsRecord.price) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrinkDetailsContent() {
    DrinkDetailsContent(
        drinkDetailsRecord = DrinkDetailsRecord(
            name = "Daiquiri",
            price = "500",
            description = "Daiquiri has the ability to cure loneliness and sorrows. If you want to forget about your problems and feel happier, get your glass of daiquiri",
            image = "",
            ingredients = "<ul><li>&nbsp;60 ml white rum</li><li>&nbsp;30 ml fresh lime</li><li>&nbsp;22.5 ml simple syrup</li></ul>"
        ),
        onGetDrink = {_, _ ->}
    )
}