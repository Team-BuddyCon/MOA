package com.yapp.moa.designsystem.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.R

private val BuddyConFAButtonSize = 56.dp
private val BuddyConFAButtonIconSize = 24.dp
private val BuddyConFAButtonIconDescription = "BuddyConFAButtonIcon"

@Composable
fun FloatingActionButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(BuddyConFAButtonSize),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = BuddyConTheme.colors.primary
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = BuddyConFAButtonIconDescription,
            modifier = Modifier.size(BuddyConFAButtonIconSize),
            tint = BuddyConTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
private fun BuddyConFAButtonPreview() {
    BuddyConTheme {
        FloatingActionButton {}
    }
}
