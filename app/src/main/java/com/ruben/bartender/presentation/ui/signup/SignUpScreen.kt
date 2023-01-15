package com.ruben.bartender.presentation.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.ruben.bartender.R
import com.ruben.bartender.presentation.base.theme.ElBarmanTheme
import com.ruben.bartender.presentation.ui.common.AppBar
import com.ruben.bartender.presentation.ui.common.LoadingView

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val context = LocalContext.current

    //handle side effects
    LaunchedEffect(signUpViewModel.uiSideEffect()) {
        signUpViewModel.uiSideEffect().collect { uiSideEffect ->
            when (uiSideEffect) {
                SignUpSideEffect.SignUpSuccess -> {
                    navigateToHome()
                }
                is SignUpSideEffect.ShowError -> {
                    val result = snackbarHostState.showSnackbar(
                        message = context.resources.getString(uiSideEffect.message),
                        actionLabel = context.resources.getString(uiSideEffect.action),
                        duration = SnackbarDuration.Indefinite,
                        withDismissAction = true
                    )
                    if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                        navigateToHome()
                    }
                }
            }
        }
    }

    val signUpState by signUpViewModel.uiState().collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppBar(
                title = stringResource(id = R.string.signup_title)
            )
        }
    ) { paddingValues ->
        SignUpContent(
            topPaddingValue = paddingValues.calculateTopPadding(),
            isValidName = signUpViewModel::isValidLetter,
            onLastNameUpdated = signUpViewModel::onLastNameUpdated,
            onFirstNameUpdated = signUpViewModel::onFirstNameUpdated,
            isButtonEnabled = {
                signUpState.isFirstNameEntered && signUpState.isLastNameEntered
            },
            isLoading = { signUpState.isLoading },
            onSkipClick = signUpViewModel::navigateToHome,
            onSaveClick = signUpViewModel::saveUser
        )
    }
}

@Composable
private fun SignUpContent(
    modifier: Modifier = Modifier,
    topPaddingValue: Dp,
    isValidName: (name: String) -> Boolean,
    onFirstNameUpdated: (name: String) -> Unit,
    onLastNameUpdated: (name: String) -> Unit,
    isButtonEnabled: () -> Boolean,
    isLoading: () -> Boolean,
    onSaveClick: (firstName: String, lastName: String) -> Unit,
    onSkipClick: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        SignUpUI(
            topPaddingValue = topPaddingValue,
            isValidName = isValidName,
            onFirstNameUpdated = onFirstNameUpdated,
            onLastNameUpdated = onLastNameUpdated,
            isButtonEnabled = isButtonEnabled,
            onSaveClick = onSaveClick,
            onSkipClick = onSkipClick
        )

        if (isLoading()) {
            LoadingView(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpUI(
    modifier: Modifier = Modifier,
    topPaddingValue: Dp,
    isValidName: (name: String) -> Boolean,
    onFirstNameUpdated: (name: String) -> Unit,
    onLastNameUpdated: (name: String) -> Unit,
    isButtonEnabled: () -> Boolean,
    onSkipClick: () -> Unit,
    onSaveClick: (firstName: String, lastName: String) -> Unit
) {

    var firstNameState by remember {
        mutableStateOf("")
    }

    var lastNameState by remember {
        mutableStateOf("")
    }

    Card(
        modifier = modifier
            .padding(vertical = topPaddingValue + 16.dp, horizontal = 24.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = ElBarmanTheme.colors.onPrimary
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                painter = rememberAsyncImagePainter(model = R.drawable.ic_signup_profile),
                contentDescription = stringResource(id = R.string.content_desc_sign_up)
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.signup_desc),
                style = ElBarmanTheme.typography.headingLarge
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
                value = firstNameState,
                onValueChange = {
                    if (isValidName(it)) {
                        onFirstNameUpdated(it)
                        firstNameState = it
                    }
                },
                placeholder = {
                    Text(
                        modifier = Modifier.background(Color.Transparent),
                        text = stringResource(id = R.string.signup_first_name_hint)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
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
                value = lastNameState,
                onValueChange = {
                    if (it.isNotEmpty()) {
                        if (isValidName(it)) {
                            onLastNameUpdated(it)
                            lastNameState = it
                        }
                    } else {
                        onLastNameUpdated(it)
                        lastNameState = it
                    }
                },
                placeholder = {
                    Text(
                        modifier = Modifier.background(Color.Transparent),
                        text = stringResource(id = R.string.signup_last_name_hint)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
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
                onClick = { onSaveClick(firstNameState, lastNameState) }
            ) {
                Text(text = stringResource(id = R.string.all_get_started))
            }

            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = ElBarmanTheme.colors.primary,
                    disabledContentColor = ElBarmanTheme.colors.onPrimary,
                    disabledContainerColor = ElBarmanTheme.colors.disabled,
                    containerColor = ElBarmanTheme.colors.onPrimary.copy(alpha = 0.8f)
                ),
                onClick = onSkipClick,
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp)
            ) {
                Text(text = stringResource(id = R.string.signup_do_it_later))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpContent() {
    SignUpContent(
        isButtonEnabled = { true },
        onFirstNameUpdated = {},
        onLastNameUpdated = {},
        topPaddingValue = 0.dp,
        isValidName = { true },
        isLoading = { false },
        onSkipClick = {},
        onSaveClick = { _, _ -> }
    )
}