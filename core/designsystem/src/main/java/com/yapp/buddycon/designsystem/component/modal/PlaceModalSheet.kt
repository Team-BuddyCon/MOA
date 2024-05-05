package com.yapp.buddycon.designsystem.component.modal

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.utils.DividerHorizontal
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey90
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel

private val PlaceModalSheetHeight = 182.dp
private val PlaceExpandedModelSheetHeight = 228.dp
private val PlaceModalSheetRadius = 24.dp
private val PlaceModalSheetHeaderHorizontalPadding = 7.dp
private val PlaceModalSheetHeaderIconSize = 60.dp
private val PlaceModalSheetButtonHeight = 54.dp
private val PlaceModalSheetButtonRadius = 12.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    searchPlaceModel: SearchPlaceModel,
    onCopyAddress: (String) -> Unit = {},
    onKaKaoMapClick: () -> Unit = {},
    onGoogleMpaClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    var isClicked by remember { mutableStateOf(false) }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isClicked) PlaceExpandedModelSheetHeight else PlaceModalSheetHeight),
        shape = RoundedCornerShape(
            topStart = PlaceModalSheetRadius,
            topEnd = PlaceModalSheetRadius
        ),
        containerColor = BuddyConTheme.colors.modalColor
    ) {
        if (isClicked) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        val naverMapUri = Uri
                            .Builder()
                            .scheme("nmap")
                            .authority("route")
                            .appendPath("public")
                            .appendQueryParameter("appname", "com.yapp.buddycon")
                            .appendQueryParameter("dlat", searchPlaceModel.y.toString())
                            .appendQueryParameter("dlng", searchPlaceModel.x.toString())
                            .appendQueryParameter("dname", searchPlaceModel.place_name)
                            .build()

                        runDeepLink(
                            context = context,
                            uri = naverMapUri,
                            marketLink = "market://details?id=com.nhn.android.nmap"
                        )
                    }.padding(horizontal = Paddings.xlarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_naver_map),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(R.string.naver_map),
                    modifier = Modifier.padding(start = Paddings.xlarge),
                    style = BuddyConTheme.typography.body02
                )
            }
            DividerHorizontal(modifier = Modifier.fillMaxWidth())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        val kakaoMapUri = Uri
                            .Builder()
                            .scheme("kakaomap")
                            .authority("route")
                            .appendQueryParameter("ep", "${searchPlaceModel.y},${searchPlaceModel.x}")
                            .appendQueryParameter("by", "PUBLICTRANSIT")
                            .build()

                        runDeepLink(
                            context = context,
                            uri = kakaoMapUri,
                            marketLink = "market://details?id=net.daum.android.map"
                        )
                    }
                    .padding(horizontal = Paddings.xlarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_kakao_map),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(R.string.kakao_map),
                    modifier = Modifier.padding(start = Paddings.xlarge),
                    style = BuddyConTheme.typography.body02
                )
            }
            DividerHorizontal(modifier = Modifier.fillMaxWidth())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        val googleMapUri = Uri
                            .Builder()
                            .scheme("geo")
                            .appendPath("${searchPlaceModel.y},${searchPlaceModel.x}")
                            .appendQueryParameter("q", "${searchPlaceModel.place_name}")
                            .build()

                        runDeepLink(
                            context = context,
                            uri = googleMapUri,
                            marketLink = "market://details?id=com.google.android.apps.maps",
                            packageName = "com.google.android.apps.maps"
                        )
                    }
                    .padding(horizontal = Paddings.xlarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_google_map),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(R.string.google_map),
                    modifier = Modifier.padding(start = Paddings.xlarge),
                    style = BuddyConTheme.typography.body02
                )
            }
            DividerHorizontal(modifier = Modifier.fillMaxWidth())
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Paddings.xlarge,
                        end = Paddings.xlarge,
                        bottom = Paddings.xlarge
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PlaceModalSheetHeaderHorizontalPadding)
                ) {
                    Text(
                        text = searchPlaceModel.place_name,
                        style = BuddyConTheme.typography.subTitle
                    )
                    Row(
                        modifier = Modifier.padding(top = Paddings.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.modal_sheet_place_distance),
                            style = BuddyConTheme.typography.body04
                        )
                        Text(
                            text = String.format(
                                stringResource(R.string.modal_sheet_place_distance_format),
                                searchPlaceModel.distance / 1000.0
                            ),
                            modifier = Modifier.padding(start = Paddings.xlarge),
                            style = BuddyConTheme.typography.body04
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Paddings.xlarge)
                ) {
                    Button(
                        onClick = { isClicked = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(PlaceModalSheetButtonHeight),
                        shape = RoundedCornerShape(PlaceModalSheetButtonRadius),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BuddyConTheme.colors.primary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.modal_sheet_place_find_location),
                            style = BuddyConTheme.typography.subTitle.copy(
                                color = BuddyConTheme.colors.onPrimary
                            )
                        )
                    }
                    Button(
                        onClick = { onCopyAddress(searchPlaceModel.place_name) },
                        modifier = Modifier
                            .padding(start = Paddings.medium)
                            .weight(1f)
                            .height(PlaceModalSheetButtonHeight),
                        shape = RoundedCornerShape(PlaceModalSheetButtonRadius),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Grey90
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.modal_sheet_place_copy_address),
                            style = BuddyConTheme.typography.subTitle.copy(
                                color = BuddyConTheme.colors.onPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun runDeepLink(
    context: Context,
    uri: Uri,
    marketLink: String,
    packageName: String? = null
) {
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addCategory(Intent.CATEGORY_BROWSABLE)

    packageName?.let { name ->
        intent.setPackage(name)
    }

    val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.packageManager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong()))
    } else {
        context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    }

    if (list.isEmpty()) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(marketLink)))
    } else {
        context.startActivity(intent)
    }
}
