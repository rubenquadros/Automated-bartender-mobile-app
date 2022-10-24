package com.ruben.bartender.presentation.ui.drinkDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@Composable
fun DrinkDetailsScreen(
    drinkDetailsViewModel: DrinkDetailsViewModel = hiltViewModel()
) {
    DrinkDetailsContent()
}

@Composable
private fun DrinkDetailsContent() {
    Box(modifier = Modifier.height(400.dp).fillMaxWidth().background(ElBarmanTheme.colors.onPrimary))
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrinkDetailsContent() {
    DrinkDetailsContent()
}