package com.yapp.buddycon.utility.stability

import android.location.Location
import androidx.compose.runtime.Stable
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel

@Stable
data class MapLocation(
    val location: Location? = null
)

@Stable
data class MapSearchPlace(
    val searchPlaceModels: List<SearchPlaceModel> = listOf()
)
