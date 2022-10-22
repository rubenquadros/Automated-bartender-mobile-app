package com.ruben.bartender.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 23/10/22
 **/
@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center
) {
    Box(modifier = modifier, contentAlignment = alignment) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("progress_bar"),
            color = ElBarmanTheme.colors.onPrimaryVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingView() {
    LoadingView(modifier = Modifier.fillMaxSize())
}