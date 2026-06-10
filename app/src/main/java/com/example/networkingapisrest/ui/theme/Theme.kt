package com.example.networkingapisrest.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary = VerdeAcento,
    onPrimary = FondoOscuro,
    secondary = VerdeAcento,
    onSecondary = FondoOscuro,
    background = FondoOscuro,
    onBackground = VerdeAcento,
    surface = FondoTarjeta,
    onSurface = VerdeAcento,
    surfaceVariant = FondoTarjeta,
    onSurfaceVariant = VerdeAcento,
    outline = VerdeAcento,
    outlineVariant = VerdeApagado
)

@Composable
fun NetworkingApisRestTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}