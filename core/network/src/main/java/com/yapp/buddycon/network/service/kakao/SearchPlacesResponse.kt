package com.yapp.buddycon.network.service.kakao

import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel

data class SearchPlacesResponse(
    @SerializedName("documents")
    val places: List<SearchPlaceResponse>
)

data class SearchPlaceResponse(
    val address_name: String,
    val category_group_code: String,
    val category_group_name: String,
    val category_name: String,
    val distance: Int,
    val id: String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: String,
    val y: String
) {
    fun toModel(store: String) = SearchPlaceModel(
        address_name = address_name,
        category_group_code = category_group_code,
        category_group_name = category_group_name,
        category_name = category_name,
        distance = distance,
        id = id,
        phone = phone,
        place_name = place_name,
        place_url = place_url,
        road_address_name = road_address_name,
        x = x,
        y = y,
        store = store
    )
}
