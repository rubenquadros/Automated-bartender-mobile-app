package com.ruben.bartender.presentation.ui.payment

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.common.AppBar

/**
 * Created by Ruben Quadros on 26/10/22
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {

    val context = LocalContext.current

    val paymentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                //TODO:check for success
            } else {
                paymentViewModel.onPaymentFailed()
            }
        }
    )

    //handle side effects
    LaunchedEffect(paymentViewModel.uiSideEffect()) {
        paymentViewModel.uiSideEffect().collect { uiSideEffect ->
            when (uiSideEffect) {
                is PaymentSideEffect.StartPayment -> {
                    val intent = Intent().apply {
                        data = uiSideEffect.uri
                        setPackage(uiSideEffect.packageName)
                    }
                    paymentLauncher.launch(
                        IntentSenderRequest.Builder(
                            PendingIntent.getActivity(
                                context,
                                PaymentConstants.REQUEST_CODE,
                                intent,
                                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                            )
                        ).build()
                    )
                }
                is PaymentSideEffect.PaymentDone -> {
                    navigateUp()
                }
            }
        }
    }

    val paymentState by paymentViewModel.uiState().collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = stringResource(id = R.string.payment_title),
                onBackPress = navigateUp
            )
        }
    ) { paddingValues: PaddingValues ->
        PaymentContent(
            topPaddingValue = paddingValues.calculateTopPadding(),
            drinkName = { paymentState.drinkName },
            drinkPrice = { paymentState.price },
            paymentItems = { paymentState.paymentItems },
            onPaymentOptionClicked = paymentViewModel::onPaymentClick
        )
    }
}

@Composable
private fun PaymentContent(
    modifier: Modifier = Modifier,
    topPaddingValue: Dp,
    drinkName: () -> String,
    drinkPrice: () -> String,
    paymentItems: () -> List<PaymentItem>,
    onPaymentOptionClicked: (id: String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = topPaddingValue + 16.dp, horizontal = 24.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = ElBarmanTheme.colors.onPrimary
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                painter = rememberAsyncImagePainter(model = R.drawable.ic_currency_rupee),
                contentDescription = stringResource(id = R.string.content_desc_rupee)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.payment_drink))
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(" ${drinkName()}")
                    }
                },
                style = ElBarmanTheme.typography.bodyLarge,
                color = ElBarmanTheme.colors.primaryVariant
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.payment_total))
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(" ${drinkPrice()}")
                    }
                },
                style = ElBarmanTheme.typography.bodyLarge,
                color = ElBarmanTheme.colors.primaryVariant
            )

            paymentItems().forEach { paymentItem: PaymentItem ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            onPaymentOptionClicked(paymentItem.id)
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(model = paymentItem.icon),
                        contentDescription = stringResource(id = paymentItem.contentDescription)
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f, fill = true)
                            .align(Alignment.CenterVertically),
                        text = stringResource(id = paymentItem.name)
                    )

                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        painter = rememberAsyncImagePainter(model = R.drawable.ic_arrow_right),
                        contentDescription = stringResource(id = R.string.content_desc_right_arrow)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPaymentContent() {
    PaymentContent(
        topPaddingValue = 0.dp,
        drinkName = { "Rum & Coke" },
        drinkPrice = { "290" },
        paymentItems = { getPaymentItems() },
        onPaymentOptionClicked = {}
    )
}