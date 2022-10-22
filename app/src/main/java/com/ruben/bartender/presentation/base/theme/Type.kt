package com.ruben.bartender.presentation.base.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.ruben.bartender.R
import javax.annotation.concurrent.Immutable

@OptIn(ExperimentalTextApi::class)
private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalTextApi::class)
private val fontName = GoogleFont("Libre Baskerville")

@OptIn(ExperimentalTextApi::class)
val Baskerville = FontFamily(
    Font(googleFont = fontName, fontProvider = provider, style = FontStyle.Normal, weight = FontWeight.Normal),
    Font(resId = R.font.libre_baskerville),
    Font(googleFont = fontName, fontProvider = provider, style = FontStyle.Normal, weight = FontWeight.Bold),
    Font(resId = R.font.libre_baskerville, weight = FontWeight.Bold)
)

@Immutable
data class ElBarmanTypography(
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val headingLarge: TextStyle,
    val headingSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelMedium: TextStyle
)

val LocalElBarmanTypography = staticCompositionLocalOf {
    ElBarmanTypography(
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        headingLarge = TextStyle.Default,
        headingSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodySmall = TextStyle.Default,
        labelMedium = TextStyle.Default
    )
}