package com.ruben.bartender.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 24/10/22
 **/
@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetry: (() -> Unit)? = null,
    buttonText: String
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .size(60.dp)
                .align(Alignment.CenterHorizontally),
            painter = rememberAsyncImagePainter(model = R.drawable.ic_error),
            contentDescription = stringResource(id = R.string.content_desc_error)
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = errorMessage,
            textAlign = TextAlign.Center,
            style = ElBarmanTheme.typography.bodyLarge,
            color = ElBarmanTheme.colors.primaryVariant
        )

        onRetry?.let {
            Button(
                modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                onClick = it,
                colors = ButtonDefaults.buttonColors(
                    contentColor = ElBarmanTheme.colors.onPrimary,
                    containerColor = ElBarmanTheme.colors.primaryVariant
                )
            ) {
                Text(text = buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorView() {
    ErrorView(
        errorMessage = "Sorry there was an error!",
        buttonText = "Retry",
        onRetry = {}
    )
}