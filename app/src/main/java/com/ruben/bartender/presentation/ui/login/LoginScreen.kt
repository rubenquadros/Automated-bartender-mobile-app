package com.ruben.bartender.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.common.AppBar
import com.ruben.bartender.presentation.ui.common.LoadingView
import org.orbitmvi.orbit.compose.collectAsState

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToSignUp: (number: String) -> Unit
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val context = LocalContext.current

    //handle side effects
    LaunchedEffect(loginViewModel.uiSideEffect()) {
        loginViewModel.uiSideEffect().collect { uiSideEffect ->
            when (uiSideEffect) {
                LoginSideEffect.LoginSuccess -> {
                    navigateToHome()
                }
                is LoginSideEffect.NavigateToSignUp -> {
                    navigateToSignUp(uiSideEffect.phoneNumber)
                }
                is LoginSideEffect.ShowError -> {
                    snackbarHostState.showSnackbar(context.resources.getString(uiSideEffect.message))
                }
            }
        }
    }

    val loginState by loginViewModel.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppBar(
                title = stringResource(id = R.string.login_title)
            )
        }
    ) { paddingValues ->
        LoginContent(
            topPaddingValue = paddingValues.calculateTopPadding(),
            onNumberEntered = loginViewModel::sendOtp,
            onOtpEntered = loginViewModel::validateOtp,
            onOtpUpdated = loginViewModel::onOtpUpdated,
            onNumberUpdated = loginViewModel::onNumberUpdated,
            isNumberEntered = { loginState.isNumberEntered },
            isOtpEntered = { loginState.isOtpEntered },
            isButtonEnabled = {
                when {
                    loginState.isNumberEntered && loginState.isOtpEntered.not() -> true
                    loginState.shouldShowOtpField -> {
                        loginState.isNumberEntered && loginState.isOtpEntered
                    }
                    else -> false
                }
            },
            isValidDigit = loginViewModel::isValidDigit,
            isLoading = { loginState.isLoading },
            shouldShowOtpField = { loginState.shouldShowOtpField }
        )
    }
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    topPaddingValue: Dp,
    onNumberEntered: (number: String) -> Unit,
    onOtpEntered: (otp: String) -> Unit,
    onNumberUpdated: (number: String) -> Unit,
    onOtpUpdated: (otp: String) -> Unit,
    isNumberEntered: () -> Boolean,
    isOtpEntered: () -> Boolean,
    isButtonEnabled: () -> Boolean,
    isValidDigit: (digits: String, isOtp: Boolean, isPhoneNumber: Boolean) -> Boolean,
    isLoading: () -> Boolean,
    shouldShowOtpField: () -> Boolean
) {
    Box(modifier = modifier.fillMaxSize()) {
        LoginUI(
            topPaddingValue = topPaddingValue,
            onNumberEntered = onNumberEntered,
            onOtpEntered = onOtpEntered,
            onNumberUpdated = onNumberUpdated,
            onOtpUpdated = onOtpUpdated,
            isNumberEntered = isNumberEntered,
            isOtpEntered = isOtpEntered,
            isButtonEnabled = isButtonEnabled,
            isValidDigit = isValidDigit,
            shouldShowOtpField = shouldShowOtpField
        )

        if (isLoading()) {
            LoadingView(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginUI(
    modifier: Modifier = Modifier,
    topPaddingValue: Dp,
    onNumberEntered: (number: String) -> Unit,
    onOtpEntered: (otp: String) -> Unit,
    onNumberUpdated: (number: String) -> Unit,
    onOtpUpdated: (otp: String) -> Unit,
    isNumberEntered: () -> Boolean,
    shouldShowOtpField: () -> Boolean,
    isOtpEntered: () -> Boolean,
    isButtonEnabled: () -> Boolean,
    isValidDigit: (digits: String, isOtp: Boolean, isPhoneNumber: Boolean) -> Boolean
) {
    var phoneNumberState by remember {
        mutableStateOf("")
    }

    var otpState by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
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
                    painter = rememberAsyncImagePainter(model = R.drawable.ic_signin_phone),
                    contentDescription = stringResource(id = R.string.content_desc_sign_in)
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.login_enter_number),
                    style = ElBarmanTheme.typography.headingSmall
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textStyle = ElBarmanTheme.typography.headingLarge,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = ElBarmanTheme.colors.primaryVariant,
                        cursorColor = ElBarmanTheme.colors.onPrimaryVariant,
                        focusedBorderColor = ElBarmanTheme.colors.primaryVariant,
                        unfocusedBorderColor = ElBarmanTheme.colors.surface,
                        placeholderColor = ElBarmanTheme.colors.disabled,
                        containerColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(16.dp),
                    value = phoneNumberState,
                    onValueChange = {
                        if (isValidDigit(it, false, true)) {
                            phoneNumberState = it
                            onNumberUpdated(it)
                        }
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.background(Color.Transparent),
                            text = stringResource(id = R.string.login_number_hint)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onNumberEntered(phoneNumberState)
                        }
                    )
                )

                if (shouldShowOtpField()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.login_enter_otp),
                        style = ElBarmanTheme.typography.headingSmall
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textStyle = ElBarmanTheme.typography.headingLarge,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = ElBarmanTheme.colors.primaryVariant,
                            cursorColor = ElBarmanTheme.colors.onPrimaryVariant,
                            focusedBorderColor = ElBarmanTheme.colors.primaryVariant,
                            unfocusedBorderColor = ElBarmanTheme.colors.surface,
                            placeholderColor = ElBarmanTheme.colors.disabled,
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(16.dp),
                        value = otpState,
                        onValueChange = {
                            if (isValidDigit(it, true, false)) {
                                otpState = it
                                onOtpUpdated(it)
                            }
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.background(Color.Transparent),
                                text = stringResource(id = R.string.login_otp_hint)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onOtpEntered(otpState)
                            }
                        )
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.login_otp_desc),
                    style = ElBarmanTheme.typography.bodyMedium,
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
                    enabled = isButtonEnabled(),
                    onClick = {
                        when {
                            isNumberEntered() && isOtpEntered().not() -> {
                                onNumberEntered(phoneNumberState)
                            }
                            isNumberEntered() && isOtpEntered() -> {
                                onOtpEntered(otpState)
                            }
                            else -> {
                                //do nothing
                            }
                        }
                    }
                ) {
                    Text(
                        text = if (shouldShowOtpField()) {
                            stringResource(id = R.string.all_verify)
                        } else {
                            stringResource(id = R.string.all_continue)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginContent() {
    LoginContent(
        onNumberEntered = {},
        onOtpEntered = {},
        onOtpUpdated = {},
        onNumberUpdated = {},
        topPaddingValue = 0.dp,
        isNumberEntered = { true },
        isOtpEntered = { true },
        isButtonEnabled =  { true },
        isValidDigit = { _, _, _ -> true },
        isLoading = { false },
        shouldShowOtpField = { true }
    )
}
