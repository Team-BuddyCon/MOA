package com.yapp.buddycon.gifticon.nearestuse

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel
import com.yapp.buddycon.gifticon.available.LoadingStateScreen
import com.yapp.buddycon.gifticon.available.base.HandleDataResult
import com.yapp.buddycon.gifticon.detail.GifticonDetailViewModel
import timber.log.Timber

@Composable
fun NearestUseScreen(
    gifticonDetailViewModel: GifticonDetailViewModel,
    gifticonId: Int?,
    onBack: () -> Unit
) {
    checkNotNull(gifticonId)

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.map),
                onBack = onBack
            )
        },
        floatingActionButton = {
            BuddyConButton(
                modifier = Modifier
                    .padding(horizontal = Paddings.xlarge)
                    .fillMaxWidth(),
                text = stringResource(R.string.nearst_use_check_complete)
            ) { onBack() }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        NearestUseContent(
            gifticonDetailViewModel = gifticonDetailViewModel,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            gifticonId = gifticonId
        )
    }
}

@Composable
private fun NearestUseContent(
    nearestUseViewModel: NearestUseViewModel = hiltViewModel(),
    gifticonDetailViewModel: GifticonDetailViewModel,
    modifier: Modifier = Modifier,
    gifticonId: Int
) {
    val context = LocalContext.current

    var currentLocation by remember { mutableStateOf<Location?>(null) }
    val gifticonDetailModel by gifticonDetailViewModel.gifticonDetailModel.collectAsStateWithLifecycle()

    val nearestUseScreenUiState by nearestUseViewModel.nearestUseScreenUiState.collectAsStateWithLifecycle()
    val uiStateFromSearchPlacesDataResult by nearestUseViewModel.uiStateFromSearchPlacesDataResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        getCurrentLocation(context) {
            currentLocation = it
        }
    }

    LaunchedEffect(currentLocation) {
        currentLocation?.let { location ->
            nearestUseViewModel.searchPlacesByKeywordWithDataResult(
                query = gifticonDetailModel.gifticonStore.value,
                x = location.longitude.toString(),
                y = location.latitude.toString()
            )
        }
    }

    HandleDataResult(
        dataResultStateFlow = nearestUseViewModel.searchPlacesDataResult,
        onSuccess = {
            nearestUseViewModel.updateUiStateFromSearchPlacesDataResult(UiStateFromSearchPlacesDataResult.LoadMap(it.data))
        },
        onFailure = {
            nearestUseViewModel.updateNearestScreenUiState(NearestUseScreenUiState.Failure)
        },
        onLoading = {
            nearestUseViewModel.updateNearestScreenUiState(NearestUseScreenUiState.Loading)
        }
    )

    if (nearestUseScreenUiState is NearestUseScreenUiState.Loading) {
        LoadingStateScreen()
    }

    if (uiStateFromSearchPlacesDataResult is UiStateFromSearchPlacesDataResult.LoadMap) {
        NearestUseMap(
            currentLocation = currentLocation,
            searchPlacesModel = (uiStateFromSearchPlacesDataResult as UiStateFromSearchPlacesDataResult.LoadMap).searchPlacesModel,
            modifier = modifier
        )
    }
}

@Composable
private fun NearestUseMap(
    currentLocation: Location?,
    searchPlacesModel: List<SearchPlaceModel> = listOf(),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                MapView(context)
            },
            update = { it ->
                it.start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() {
                        }

                        override fun onMapError(error: Exception?) {
                            Timber.e("onMapError ${error?.message}")
                        }
                    },
                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) {
                            currentLocation?.let { location ->
                                val maxZoomLevel = 21 // 카카오 맵 api 문서에 명시, 6:가장 축소된 상태 & 21:가장 확대된 상태
                                val minZoomLevel = 6
                                var zoomLevelWhereAllMarkerVisible: Int? = null

                                val latlngList = mutableListOf(LatLng.from(location.latitude, location.longitude))
                                searchPlacesModel.forEach { seachPlaceModel ->
                                    latlngList.add(LatLng.from(seachPlaceModel.y.toDouble(), seachPlaceModel.x.toDouble()))
                                }

                                /** 모든 좌표가 보이는 zoom level */
                                for (zoomLevel in maxZoomLevel downTo minZoomLevel) {
                                    if (kakaoMap.canShowMapPoints(zoomLevel, *(latlngList.toTypedArray()))) {
                                        zoomLevelWhereAllMarkerVisible = zoomLevel
                                        break
                                    }
                                }

                                zoomLevelWhereAllMarkerVisible?.let { zoomLevel ->
                                    /** 중심 좌표 구하기 */
                                    val latAverage = latlngList.map { it.latitude }.average()
                                    val lonAverage = latlngList.map { it.longitude }.average()

                                    val adjustedZoomLevel = if (zoomLevel >= 16) {
                                        14
                                    } else {
                                        if (zoomLevel == minZoomLevel) zoomLevel else zoomLevel - 1
                                    }

                                    /** 위에서 구한 zoom level, 중심 좌표로 지도 카메라 이동 */
                                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(LatLng.from(latAverage, lonAverage), adjustedZoomLevel))

                                    /** 내 위치, 장소 리스트 마커 그리기 */
                                    kakaoMap.labelManager?.let { manager ->
                                        getLocationLabel(
                                            labelManager = manager,
                                            latitude = location.latitude,
                                            longitude = location.longitude
                                        )

                                        searchPlacesModel.forEach { seachPlaceModel ->
                                            getLocationLabel(
                                                labelManager = manager,
                                                latitude = seachPlaceModel.y.toDouble(),
                                                longitude = seachPlaceModel.x.toDouble()
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        override fun getPosition(): LatLng {
                            currentLocation?.let { location ->
                                return LatLng.from(location.latitude, location.longitude)
                            } ?: kotlin.run {
                                return super.getPosition()
                            }
                        }
                    }
                )
            }
        )
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(
    context: Context,
    onSuccess: (Location) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener {
        onSuccess(it)
    }
}

private fun getLocationLabel(
    labelManager: LabelManager,
    latitude: Double,
    longitude: Double
) {
    labelManager.layer
        ?.addLabel(
            LabelOptions.from(LatLng.from(latitude, longitude))
                .setStyles(
                    labelManager.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.ic_location)))
                )
        )
}
