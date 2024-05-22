package com.yapp.buddycon.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val SNACKBAR_WIDTH = 270.dp
private val SNACKBAR_HEIGHT = 48.dp
private val SNACKBAR_RADIUS = 62.dp
private val SNACKBAR_OUTTER_BOTTOM_PADDING = 16.dp
private val SNACKBAR_INNER_HORIZONTAL_PADDING = 20.dp
private val SNACKBAR_INNER_VERTICAL_PADDING = 14.dp
private val SNACKBAR_DISMISS_ICON_DESCRIPTION = "SNACKBAR_DISMISS"

@Composable
fun BuddyConSnackbar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    contentAlignment: Alignment = Alignment.BottomCenter
) {
    SnackbarHost(
        hostState = snackbarHostState
    ) { snackbarData ->
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = contentAlignment
        ) {
            Spacer(
                modifier = Modifier
                    .padding(bottom = SNACKBAR_OUTTER_BOTTOM_PADDING)
                    .size(SNACKBAR_WIDTH, SNACKBAR_HEIGHT)
                    .background(
                        color = BuddyConTheme.colors.snackbarBackground,
                        shape = RoundedCornerShape(SNACKBAR_RADIUS)
                    )
                    .blur(10.dp)
            )
            Row(
                modifier = Modifier
                    .padding(bottom = SNACKBAR_OUTTER_BOTTOM_PADDING)
                    .size(SNACKBAR_WIDTH, SNACKBAR_HEIGHT)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(SNACKBAR_RADIUS)
                    )
                    .padding(
                        horizontal = SNACKBAR_INNER_HORIZONTAL_PADDING,
                        vertical = SNACKBAR_INNER_VERTICAL_PADDING
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = snackbarData.visuals.message,
                    modifier = Modifier.weight(1f),
                    style = BuddyConTheme.typography.body03.copy(
                        color = BuddyConTheme.colors.onSnackbar
                    )
                )
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = SNACKBAR_DISMISS_ICON_DESCRIPTION,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { snackbarData.dismiss() },
                    tint = White
                )
            }
        }
    }
}

fun showBuddyConSnackBar(
    message: String,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onComplete: suspend () -> Unit = {}
) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
        onComplete()
    }
}

@Preview
@Composable
private fun BuddyConSnackbarPreview() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    BuddyConTheme {
        BuddyConSnackbar(snackbarHostState = snackbarHostState)
        showBuddyConSnackBar(
            message = "기프티콘 정보를 등록해 주세요",
            scope = scope,
            snackbarHostState = snackbarHostState
        )
    }
}
