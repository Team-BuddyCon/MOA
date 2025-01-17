package com.yapp.moa.utility

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.yapp.moa.designsystem.R
import com.yapp.moa.domain.model.kakao.SearchPlaceModel
import com.yapp.moa.domain.model.type.GifticonStore

private var fusedLocationClient: FusedLocationProviderClient? = null

// 내 위치 정보
@SuppressLint("MissingPermission")
fun getCurrentLocation(
    context: Context,
    onSuccess: (Location) -> Unit
) {
    // Client disconnected before listeners could be cleaned up* (android.os.DeadObjectException 수정)
    if (fusedLocationClient == null) {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 60000
        locationRequest.fastestInterval = 50000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations) {
                    if (location != null) {
                        setLocation(location) {
                            onSuccess(it)
                        }
                    }
                }
            }
        }

        // 마지막 위치 정보가 없는 경우 Location이 null로 반환되는 이슈로 명시적으로 위치를 Update 하도록 수정
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient?.requestLocationUpdates(locationRequest, callback, context.mainLooper)
    } else {
        fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
            setLocation(location) {
                onSuccess(it)
            }
        }
    }
}

fun setLocation(
    location: Location,
    onSuccess: (Location) -> Unit
) {
    val latitude = location.latitude
    val longitude = location.longitude

    val southKoreaMinLat = 33.0
    val southKoreaMaxLat = 39.0
    val southKoreaMinLon = 124.0
    val southKoreaMaxLon = 132.0

    if (latitude !in southKoreaMinLat..southKoreaMaxLat || longitude !in southKoreaMinLon..southKoreaMaxLon) {
        val correctedLocation = Location(location).apply {
            this.latitude = 37.402005
            this.longitude = 127.108621
        }
        onSuccess(correctedLocation)
    } else {
        onSuccess(location)
    }
}

// 지도 위 마커 추가 및 리턴
fun getLocationLabels(
    labelManager: LabelManager,
    searchPlaceModels: List<SearchPlaceModel>
): Map<SearchPlaceModel, Label?> {
    return searchPlaceModels.map { model ->
        model to labelManager.layer?.addLabel(
            LabelOptions.from(LatLng.from(model.y.toDouble(), model.x.toDouble()))
                .setStyles(
                    labelManager.addLabelStyles(
                        LabelStyles.from(
                            LabelStyle.from(
                                GifticonStore.values().find { it.value == model.store }.getDrawableRes()
                            )
                        )
                    )
                )
        ).apply {
            this?.isClickable = true
        }
    }.toMap()
}

// TODO 마커 변경되면 추후 수정 예정
fun scaleToLabel(
    label: Label,
    scale: Float
) {
    label.scaleTo(scale, scale)
}

fun GifticonStore?.getDrawableRes() = when (this) {
    GifticonStore.STARBUCKS,
    GifticonStore.TWOSOME_PLACE,
    GifticonStore.ANGELINUS,
    GifticonStore.MEGA_COFFEE,
    GifticonStore.COFFEE_BEAN,
    GifticonStore.GONG_CHA,
    GifticonStore.BASKIN_ROBBINS -> {
        R.drawable.ic_coffee
    }

    GifticonStore.MACDONALD -> {
        R.drawable.ic_fastfood
    }

    else -> {
        R.drawable.ic_store
    }
}

// TODO 마커 변경 시 변경 예정
fun GifticonStore?.getExpandedDrawableRes() = when (this) {
    GifticonStore.STARBUCKS,
    GifticonStore.TWOSOME_PLACE,
    GifticonStore.ANGELINUS,
    GifticonStore.MEGA_COFFEE,
    GifticonStore.COFFEE_BEAN,
    GifticonStore.GONG_CHA,
    GifticonStore.BASKIN_ROBBINS -> {
        R.drawable.ic_coffee
    }

    GifticonStore.MACDONALD -> {
        R.drawable.ic_fastfood
    }

    else -> {
        R.drawable.ic_store
    }
}
