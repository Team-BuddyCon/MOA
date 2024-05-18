package com.yapp.buddycon.utility

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel
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
