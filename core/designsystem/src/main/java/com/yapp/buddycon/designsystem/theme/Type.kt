package com.yapp.buddycon.designsystem.theme

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.02).em
    ),
    titleMedium = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = (33.6).sp,
        letterSpacing = (-0.02).em
    ),
    titleSmall = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = (25.2).sp,
        letterSpacing = (-0.02).em
    ),
    bodyLarge = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = (22.4).sp,
        letterSpacing = (-0.02).em
    ),
    bodyMedium = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = (19.6).sp,
        letterSpacing = (-0.02).em
    ),
    bodySmall = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = (16.8).sp,
        letterSpacing = (-0.02).em
    )
)

val Typography.title01: TextStyle
    @Composable get() = titleMedium

val Typography.title02: TextStyle
    @Composable get() = titleMedium.copy(
        fontSize = 22.sp,
        lineHeight = (30.8).sp
    )

val Typography.title03: TextStyle
    @Composable get() = titleMedium.copy(
        fontSize = 18.sp,
        lineHeight = (25.2).sp
    )

val Typography.title05: TextStyle
    @Composable get() = titleSmall

val Typography.subTitle: TextStyle
    @Composable get() = titleMedium.copy(
        fontSize = 16.sp,
        lineHeight = (22.4).sp
    )

val Typography.body01: TextStyle
    @Composable get() = bodyLarge

val Typography.body02: TextStyle
    @Composable get() = bodyMedium

val Typography.body03: TextStyle
    @Composable get() = bodyLarge.copy(
        fontSize = 14.sp,
        lineHeight = (19.6).sp
    )

val Typography.body04: TextStyle
    @Composable get() = bodyMedium.copy(
        fontSize = 12.sp,
        lineHeight = (14.32).sp
    )

val Typography.body05: TextStyle
    @Composable get() = bodyLarge.copy(
        fontSize = 12.sp,
        lineHeight = (16.8).sp
    )

val Typography.body06: TextStyle
    @Composable get() = bodySmall
