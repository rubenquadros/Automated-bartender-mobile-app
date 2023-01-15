package com.ruben.bartender.presentation.ui.payment

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.common.AppBar
import com.ruben.bartender.presentation.ui.common.DividerView
import com.ruben.bartender.presentation.ui.common.ErrorView

/**
 * Created by Ruben Quadros on 26/10/22
 **/
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun PaymentScreen(
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {

    val context = LocalContext.current

    val paymentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { activityResult: ActivityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK && activityResult.data != null) {
                val intent = activityResult.data
                val status = intent?.getStringExtra(PaymentConstants.TRANSACTION_STATUS)
                if (status == PaymentConstants.TRANSACTION_SUCCESS) {
                    paymentViewModel.onPaymentSuccess()
                } else {
                    paymentViewModel.onPaymentFailed()
                }
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

    val paymentState by paymentViewModel.uiState().collectAsStateWithLifecycle()

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
            isPaymentFailed = { paymentState.isPaymentFail },
            isSuccess = { paymentState.isDrinkReady },
            onPaymentOptionClicked = paymentViewModel::onPaymentClick,
            dismissPaymentFailDialog = paymentViewModel::resetFailState,
            dismissSuccessDialog = {}
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
    onPaymentOptionClicked: (id: String) -> Unit,
    isSuccess: () -> Boolean,
    isPaymentFailed: () -> Boolean,
    dismissPaymentFailDialog: () -> Unit,
    dismissSuccessDialog: () -> Unit
) {
    Box(modifier = modifier) {
        PaymentUI(
            topPaddingValue = topPaddingValue,
            drinkName = drinkName,
            drinkPrice = drinkPrice,
            paymentItems = paymentItems,
            onPaymentOptionClicked = onPaymentOptionClicked
        )

        if (isSuccess()) {
            //TODO: show success UI
        }

        if (isPaymentFailed()) {
            Dialog(
                properties = DialogProperties(
                    dismissOnClickOutside = false
                ),
                onDismissRequest = dismissPaymentFailDialog
            ) {
                ErrorView(
                    modifier = Modifier.wrapContentSize().background(color = ElBarmanTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp)),
                    errorMessage = stringResource(id = R.string.payment_fail),
                    onRetry = dismissPaymentFailDialog,
                    buttonText = stringResource(id = R.string.all_okay)
                )
            }
        }
    }
}

@Composable
fun PaymentUI(
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
        Column(modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 16.dp)) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                painter = rememberAsyncImagePainter(model = R.drawable.ic_currency_rupee),
                contentDescription = stringResource(id = R.string.content_desc_rupee)
            )

            DividerView(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))

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
                        append(" ${stringResource(R.string.menu_price, drinkPrice())}")
                    }
                },
                style = ElBarmanTheme.typography.bodyLarge,
                color = ElBarmanTheme.colors.primaryVariant
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.payment_desc),
                style = ElBarmanTheme.typography.bodyLarge,
                color = ElBarmanTheme.colors.primaryVariant
            )

            DividerView(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))

            paymentItems().forEach { paymentItem: PaymentItem ->
                Box(modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = ElBarmanTheme.colors.surface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        onPaymentOptionClicked(paymentItem.id)
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 10.dp)
                            .fillMaxWidth()
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewPaymentContent() {
    PaymentContent(
        topPaddingValue = 0.dp,
        drinkName = { "Rum & Coke" },
        drinkPrice = { "290" },
        paymentItems = { getPaymentItems() },
        onPaymentOptionClicked = {},
        isSuccess = { false },
        isPaymentFailed = { false },
        dismissPaymentFailDialog = {},
        dismissSuccessDialog = {}
    )
}