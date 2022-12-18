package com.ruben.bartender.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 18/12/22
 **/
@Composable
fun GetButton(
    modifier: Modifier,
    onClick: () -> Unit
) {

    Text(
        modifier = modifier
            .background(
                color = ElBarmanTheme.colors.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clickable {
                onClick()
            },
        text = stringResource(id = R.string.all_get_drink),
        style = ElBarmanTheme.typography.bodyMedium,
        color = ElBarmanTheme.colors.onPrimary
    )
}

@Composable
fun GetButtonFull(
    modifier: Modifier,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = ElBarmanTheme.colors.onPrimary,
            containerColor = ElBarmanTheme.colors.primary
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.all_get_drink),
            style = ElBarmanTheme.typography.bodyMedium,
        )
    }

}