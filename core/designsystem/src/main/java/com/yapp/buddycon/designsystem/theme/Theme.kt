package com.yapp.buddycon.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun BuddyConTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val buddyConTypography = BuddyConTypography(
        largeTitle = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 30.sp,
            lineHeight = 42.sp,
            letterSpacing = (-0.02).em
        ),
        title01 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = (33.6).sp,
            letterSpacing = (-0.02).em
        ),
        title02 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = (30.8).sp,
            letterSpacing = (-0.02).em
        ),
        title03 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = (25.2).sp,
            letterSpacing = (-0.02).em
        ),
        title05 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = (25.2).sp,
            letterSpacing = (-0.02).em
        ),
        subTitle = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = (22.4).sp,
            letterSpacing = (-0.02).em
        ),
        body01 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = (22.4).sp,
            letterSpacing = (-0.02).em
        ),
        body02 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = (19.6).sp,
            letterSpacing = (-0.02).em
        ),
        body03 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = (19.6).sp,
            letterSpacing = (-0.02).em
        ),
        body04 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = (16.8).sp,
            letterSpacing = (-0.02).em
        ),
        body05 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = (16.8).sp,
            letterSpacing = (-0.02).em
        ),
        body06 = TextStyle(
            color = Grey90,
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = (16.8).sp,
            letterSpacing = (-0.02).em
        )
    )

    val buddyConColors = BuddyConColors(
        primary = Pink100,
        background = White,
        lightDialog = Pink100,
        onLightDialog = White,
        darkDialog = Grey30,
        onDarkDialog = Grey70,
        snackbarBackground = Black.copy(0.4f),
        onSnackbar = White,
        topAppBarColor = White,
        onTopAppBarColor = Grey90
    )

    CompositionLocalProvider(
        LocalBuddyConTypography provides buddyConTypography,
        LocalBuddyConColors provides buddyConColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

object BuddyConTheme {
    val typography: BuddyConTypography
        @Composable
        get() = LocalBuddyConTypography.current

    val colors: BuddyConColors
        @Composable
        get() = LocalBuddyConColors.current
}
