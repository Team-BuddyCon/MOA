package com.yapp.moa.gifticon.nearestuse

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
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.yapp.moa.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.moa.designsystem.component.button.BuddyConButton
import com.yapp.moa.designsystem.theme.Paddings
import com.yapp.moa.domain.model.kakao.SearchPlaceModel
import com.yapp.moa.domain.model.type.GifticonStore
import com.yapp.moa.gifticon.available.LoadingStateScreen
import com.yapp.moa.gifticon.available.base.HandleDataResult
import com.yapp.moa.gifticon.detail.GifticonDetailViewModel
import com.yapp.moa.utility.getCurrentLocation
import com.yapp.moa.utility.getDrawableRes
import com.yapp.moa.utility.getLocationLabels
import com.yapp.moa.utility.stability.MapLocation
import com.yapp.moa.designsystem.R
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

    var mapLocation: MapLocation by remember { mutableStateOf(MapLocation()) }
    val gifticonDetailModel by gifticonDetailViewModel.gifticonDetailModel.collectAsStateWithLifecycle()

    val nearestUseScreenUiState by nearestUseViewModel.nearestUseScreenUiState.collectAsStateWithLifecycle()
    val uiStateFromSearchPlacesDataResult by nearestUseViewModel.uiStateFromSearchPlacesDataResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        getCurrentLocation(context) {
            mapLocation = mapLocation.copy(location = it)
        }
    }

    LaunchedEffect(mapLocation) {
        mapLocation.location?.let { location ->
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
            modifier = modifier,
            mapLocation = mapLocation,
            searchPlacesModel = (uiStateFromSearchPlacesDataResult as UiStateFromSearchPlacesDataResult.LoadMap).searchPlacesModel
        )
    }
}

@Composable
private fun NearestUseMap(
    modifier: Modifier = Modifier,
    mapLocation: MapLocation,
    searchPlacesModel: List<SearchPlaceModel> = listOf()
) {
    val context = LocalContext.current
    var map by remember { mutableStateOf<KakaoMap?>(null) }

    LaunchedEffect(map) {
        map?.labelManager?.clearAll()
        map?.labelManager?.let { manager ->
            getLocationLabels(
                labelManager = manager,
                searchPlaceModels = searchPlacesModel
            )
            mapLocation.location?.let { location ->
                getLocationLabelForUserLocation(
                    labelManager = manager,
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
        }
    }
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
                            map = null
                        }

                        override fun onMapError(error: Exception?) {
                            Timber.e("onMapError ${error?.message}")
                        }
                    },
                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) {
                            map = kakaoMap
//                            currentLocation?.let { location ->
//                                val maxZoomLevel = 21 // 카카오 맵 api 문서에 명시, 6:가장 축소된 상태 & 21:가장 확대된 상태
//                                val minZoomLevel = 6
//                                var zoomLevelWhereAllMarkerVisible: Int? = null
//
//                                val latlngList = mutableListOf(LatLng.from(location.latitude, location.longitude))
//                                searchPlacesModel.forEach { seachPlaceModel ->
//                                    latlngList.add(LatLng.from(seachPlaceModel.y.toDouble(), seachPlaceModel.x.toDouble()))
//                                }
//
//                                /** 모든 좌표가 보이는 zoom level */
//                                for (zoomLevel in maxZoomLevel downTo minZoomLevel) {
//                                    if (kakaoMap.canShowMapPoints(zoomLevel, *(latlngList.toTypedArray()))) {
//                                        zoomLevelWhereAllMarkerVisible = zoomLevel
//                                        break
//                                    }
//                                }
//
//                                zoomLevelWhereAllMarkerVisible?.let { zoomLevel ->
//                                    /** 중심 좌표 구하기 */
//                                    val latAverage = latlngList.map { it.latitude }.average()
//                                    val lonAverage = latlngList.map { it.longitude }.average()
//
//                                    val adjustedZoomLevel = if (zoomLevel >= 16) {
//                                        14
//                                    } else {
//                                        if (zoomLevel == minZoomLevel) zoomLevel else zoomLevel - 1
//                                    }
//
//                                    /** 위에서 구한 zoom level, 중심 좌표로 지도 카메라 이동 */
//                                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(LatLng.from(latAverage, lonAverage), adjustedZoomLevel))
//
//                                    /** 내 위치, 장소 리스트 마커 그리기 */
//                                    kakaoMap.labelManager?.clearAll()
//                                    kakaoMap.labelManager?.let { manager ->
//                                        /** 내 위치 표시 아이콘 - 문의하기 */
//                                        getLocationLabelForUserLocation(
//                                            labelManager = manager,
//                                            latitude = location.latitude,
//                                            longitude = location.longitude
//                                        )
//
//                                        searchPlacesModel.forEach { seachPlaceModel ->
//                                            getLocationLabelForNearestUsePlace(
//                                                labelManager = manager,
//                                                latitude = seachPlaceModel.y.toDouble(),
//                                                longitude = seachPlaceModel.x.toDouble(),
//                                                gifticonStore = seachPlaceModel.store
//                                            )
//                                        }
//                                    }
//                                }
//                            }
                        }

                        override fun getPosition(): LatLng {
                            mapLocation.location?.let { location ->
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

private fun getLocationLabelForUserLocation(
    labelManager: LabelManager,
    latitude: Double,
    longitude: Double
) {
    labelManager.layer
        ?.addLabel(
            LabelOptions.from(LatLng.from(latitude, longitude))
                .setStyles(
                    labelManager.addLabelStyles(
                        LabelStyles.from(
                            LabelStyle.from(
                                R.drawable.ic_location
                            )
                        )
                    )
                )
        )
}

private fun getLocationLabelForNearestUsePlace(
    labelManager: LabelManager,
    latitude: Double,
    longitude: Double,
    gifticonStore: String
) {
    labelManager.layer
        ?.addLabel(
            LabelOptions.from(LatLng.from(latitude, longitude))
                .setStyles(
                    labelManager.addLabelStyles(
                        LabelStyles.from(
                            LabelStyle.from(
                                GifticonStore.values().find { it.value == gifticonStore }.getDrawableRes()
                            )
                        )
                    )
                )
        )
}
