package com.ruben.bartender.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackPress: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = ElBarmanTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = ElBarmanTheme.colors.primary,
            titleContentColor = ElBarmanTheme.colors.onPrimary
        ),
        navigationIcon = {
            if (onBackPress != null) {
                IconButton(onClick = onBackPress) {
                    Image(
                        painter = rememberAsyncImagePainter(model = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(id = R.string.content_desc_back_button)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewAppBar() {
    AppBar(
        title = stringResource(id = R.string.all_home),
        onBackPress = {}
    )
}