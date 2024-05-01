package com.yapp.buddycon.utility

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.domain.model.type.GifticonStore

// 내 위치 정보
@SuppressLint("MissingPermission")
fun getCurrentLocation(
    context: Context,
    onSuccess: (Location) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener {
        onSuccess(it)
    }
}

// 지도 위 마커 추가
fun getLocationLabel(
    labelManager: LabelManager,
    latitude: Double,
    longitude: Double,
    store: GifticonStore
) {
    // TODO 별도 함수로 분리
    val drawbleRes = when (store) {
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
    labelManager.layer
        ?.addLabel(
            LabelOptions.from(LatLng.from(latitude, longitude))
                .setStyles(
                    labelManager.addLabelStyles(LabelStyles.from(LabelStyle.from(drawbleRes)))
                )
        )
}
