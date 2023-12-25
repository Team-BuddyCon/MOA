package com.yapp.buddycon.designsystem.component.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings

private val BuddyConFAButtonIconSize = 24.dp
private val BuddyConFAButtonIconDescription = "BuddyConFAButtonIcon"

@Composable
fun FloatingActionButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(Paddings.xlarge),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = BuddyConTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
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
