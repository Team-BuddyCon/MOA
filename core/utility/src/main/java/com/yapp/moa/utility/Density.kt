package com.yapp.moa.utility

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Float.toDp() = with(LocalDensity.current) { this@toDp.toDp() }

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }
