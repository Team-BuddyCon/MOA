package com.yapp.buddycon.gifticon.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBackAndEdit
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.custom.FullGifticonImage
import com.yapp.buddycon.designsystem.component.tag.DDayTag
import com.yapp.buddycon.designsystem.theme.Black
import com.yapp.buddycon.designsystem.theme.Paddings
import java.text.SimpleDateFormat

@Composable
fun GifticonDetailScreen(
    gifticonId: Int?
) {
    checkNotNull(gifticonId)

    Scaffold(
        topBar = {
            TopAppBarWithBackAndEdit(
                title = stringResource(R.string.gifticon)
            )
        }
    ) { paddingValues ->
        GifticonDetailContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            gifticonId = gifticonId
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
private fun GifticonDetailContent(
    modifier: Modifier = Modifier,
    gifticonDetailViewModel: GifticonDetailViewModel = hiltViewModel(),
    gifticonId: Int
) {
    val scrollState = rememberScrollState()
    var isImageExpanded by remember { mutableStateOf(false) }
    val gifticonDetailModel by gifticonDetailViewModel.gifticonDetailModel.collectAsStateWithLifecycle()

    LaunchedEffect(gifticonDetailViewModel) {
        gifticonDetailViewModel.requestGifticonDetail(gifticonId)
    }

    FullGifticonImage(
        imageUri = gifticonDetailModel.imageUrl,
        isExpanded = isImageExpanded,
        onExpandChanged = { isImageExpanded = it }
    )

    Column(modifier.verticalScroll(scrollState)) {
        Box(
            modifier = Modifier
                .padding(top = Paddings.medium)
                .padding(horizontal = Paddings.xlarge)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20.dp))
        ) {
            AsyncImage(
                model = gifticonDetailModel.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            if (gifticonDetailModel.expireDate.isNotEmpty()) {
                DDayTag(
                    modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                    dateMillis = SimpleDateFormat("yyyy-MM-dd").parse(gifticonDetailModel.expireDate).time
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 12.dp)
                    .size(40.dp)
                    .background(Black.copy(0.4f), CircleShape)
                    .clickable { isImageExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}
