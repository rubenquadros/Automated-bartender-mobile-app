package com.ruben.bartender.presentation.ui.common

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 26/10/22
 **/
@Composable
fun DividerView(modifier: Modifier = Modifier) {

    Divider(
        modifier = modifier,
        color = ElBarmanTheme.colors.surface
    )

}

@Preview(showBackground = true)
@Composable
private fun PreviewDividerView() {
    DividerView()
}