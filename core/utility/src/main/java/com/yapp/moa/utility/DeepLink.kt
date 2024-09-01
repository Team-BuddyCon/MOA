package com.yapp.moa.utility

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings

fun navigateToSetting(
    context: Context,
    packageName: String
) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$packageName")
    context.startActivity(intent)
}

fun navigateToNaverMap(
    context: Context,
    dlat: String,
    dlng: String,
    dname: String
) {
    val uri = Uri
        .Builder()
        .scheme("nmap")
        .authority("route")
        .appendPath("public")
        .appendQueryParameter("appname", "com.yapp.moa")
        .appendQueryParameter("dlat", dlat)
        .appendQueryParameter("dlng", dlng)
        .appendQueryParameter("dname", dname)
        .build()

    val marketUrl = "market://details?id=com.nhn.android.nmap"
    runDeepLink(
        context = context,
        uri = uri,
        marketUrl = marketUrl
    )
}

fun navigateToKakaoMap(
    context: Context,
    dlat: String,
    dlng: String
) {
    val uri = Uri
        .Builder()
        .scheme("kakaomap")
        .authority("route")
        .appendQueryParameter("ep", "$dlat,$dlng")
        .appendQueryParameter("by", "PUBLICTRANSIT")
        .build()

    val marketUrl = "market://details?id=net.daum.android.map"
    runDeepLink(
        context = context,
        uri = uri,
        marketUrl = marketUrl
    )
}

fun navigateToGoogleMap(
    context: Context,
    dlat: String,
    dlng: String,
    dname: String
) {
    val uri = Uri
        .Builder()
        .scheme("geo")
        .appendPath("$dlat,$dlng")
        .appendQueryParameter("q", "$dname")
        .build()

    val marketUrl = "market://details?id=com.google.android.apps.maps"
    runDeepLink(
        context = context,
        uri = uri,
        marketUrl = marketUrl,
        packageName = "com.google.android.apps.maps"
    )
}

private fun runDeepLink(
    context: Context,
    uri: Uri,
    marketUrl: String,
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
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)))
        } catch (e: ActivityNotFoundException) {
            // 구글 플레이 스토어가 없는 경우 -> CTS 받지 못한 경우
            e.printStackTrace()
        }
    } else {
        context.startActivity(intent)
    }
}
