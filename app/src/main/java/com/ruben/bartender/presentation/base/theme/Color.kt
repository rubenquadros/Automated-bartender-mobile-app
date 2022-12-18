package com.ruben.bartender.presentation.base.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

val PrimaryColor = Color(0xFF212121)
val PrimaryVariant = Color(0xFF000000)
val DisabledColor = Color(0xFF9E9E9E)
val OnPrimaryColor = Color(0xFFFFFFFF)
val OnPrimaryVariant = Color(0xFFDD2C00)
val SurfaceColor = Color(0xFFBDBDBD)

@Immutable
data class ElBarmanColors(
    val primary: Color,
    val primaryVariant: Color,
    val onPrimary: Color,
    val onPrimaryVariant: Color,
    val surface: Color,
    val disabled: Color
)

val LocalElBarmanColors = staticCompositionLocalOf {
    ElBarmanColors(
        primary = PrimaryColor,
        primaryVariant = PrimaryVariant,
        onPrimary = OnPrimaryColor,
        onPrimaryVariant = OnPrimaryVariant,
        surface = SurfaceColor,
        disabled = DisabledColor
    )
}