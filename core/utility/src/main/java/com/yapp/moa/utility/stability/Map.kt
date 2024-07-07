package com.yapp.moa.utility.stability

import android.location.Location
import androidx.compose.runtime.Stable
import com.yapp.moa.domain.model.kakao.SearchPlaceModel

@Stable
data class MapLocation(
    val location: Location? = null
)

@Stable
data class MapSearchPlace(
    val searchPlaceModels: List<SearchPlaceModel> = listOf()
)
