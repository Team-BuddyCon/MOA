package com.yapp.buddycon.designsystem.component.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.Paddings

@Composable
fun FullGifticonImage(
    imageUri: Any?,
    isExpanded: Boolean = false,
    onExpandChanged: (Boolean) -> Unit = {}
) {
    if (isExpanded) {
        Dialog(
            onDismissRequest = { onExpandChanged(false) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 27.dp, horizontal = Paddings.xlarge)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_white_close),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(24.dp)
                        .clickable { onExpandChanged(false) },
                    tint = Color.Unspecified
                )
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = Paddings.medium)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}
