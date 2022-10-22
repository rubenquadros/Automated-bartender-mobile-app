package com.ruben.bartender.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@Composable
fun LoginScreen() {
    Box(modifier = Modifier.fillMaxSize().background(ElBarmanTheme.colors.primaryVariant))
}

@Composable
private fun LoginContent(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp),
                painter = rememberAsyncImagePainter(model = R.drawable.ic_signin_phone),
                contentDescription = stringResource(id = R.string.content_desc_sign_in)
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.login_enter_number),
                style = ElBarmanTheme.typography.headingLarge
            )

            //TODO:change to textfield
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.login_enter_number),
                style = ElBarmanTheme.typography.headingLarge,
                color = ElBarmanTheme.colors.primaryVariant
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.login_otp_desc),
                style = ElBarmanTheme.typography.bodyLarge,
                color = ElBarmanTheme.colors.primaryVariant
            )

            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = ElBarmanTheme.colors.onPrimary,
                    disabledContentColor = ElBarmanTheme.colors.onPrimary,
                    disabledContainerColor = ElBarmanTheme.colors.disabled,
                    containerColor = ElBarmanTheme.colors.primaryVariant
                ),
                onClick = {  }
            ) {
                Text(text = stringResource(id = R.string.all_continue))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginContent() {
    LoginContent()
}
