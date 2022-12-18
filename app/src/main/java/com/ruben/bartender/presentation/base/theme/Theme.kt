package com.ruben.bartender.presentation.base.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ElBarmanTheme(
    content: @Composable () -> Unit
) {

    val localElBarmanColors = ElBarmanColors(
        primary = PrimaryColor,
        primaryVariant = PrimaryVariant,
        onPrimary = OnPrimaryColor,
        onPrimaryVariant = OnPrimaryVariant,
        surface = SurfaceColor,
        disabled = DisabledColor
    )

    val localElBarmanTypography = ElBarmanTypography(
        titleLarge = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 29.76.sp
        ),
        titleMedium = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.28.sp
        ),
        titleSmall = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.8.sp
        ),
        headingLarge = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 22.32.sp
        ),
        headingSmall = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 19.84.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 19.84.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 17.36.sp
        ),
        bodySmall = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 13.64.sp
        ),
        labelMedium = TextStyle(
            fontFamily = Baskerville,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 22.32.sp
        )
    )

    CompositionLocalProvider(
        LocalElBarmanColors provides localElBarmanColors,
        LocalElBarmanTypography provides localElBarmanTypography
    ) {
        MaterialTheme(content = content)
    }
}

object ElBarmanTheme {
    val colors: ElBarmanColors
    @Composable
    @ReadOnlyComposable
    get() = LocalElBarmanColors.current

    val typography: ElBarmanTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalElBarmanTypography.current
}