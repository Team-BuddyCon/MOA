package com.yapp.buddycon.utility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import timber.log.Timber

// 외부 저장소 읽기 권한 확인
fun checkReadExternalStorage(
    context: Context
): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    ) == PackageManager.PERMISSION_GRANTED
}

// 외부 저장소 읽기 권한 요청
@Composable
fun RequestReadExternalStoragePermission(
    onGranted: () -> Unit = {},
    onDeny: () -> Unit = {}
) {
    val context = LocalContext.current

    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Timber.d("RequestReadExternalStoragePermission isGranted")
            onGranted()
        } else {
            Timber.e("RequestReadExternalStoragePermission Denied")
            onDeny()
        }
    }
    LaunchedEffect(Unit) {
        if (!checkReadExternalStorage(context)) {
            permissionsLauncher.launch(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
            )
        }
    }
}

// 현재 위치 권한 확인
fun checkLocationPermission(
    context: Context
): Boolean {
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    return permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
}

// 시스템 위치 권한 요청
@Composable
fun RequestLocationPermission(
    onGranted: () -> Unit = {},
    onDeny: () -> Unit = {}
) {
    val context = LocalContext.current
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.values.all { it }
        if (isGranted) {
            Timber.d("RequestLocationPermission isGranted")
            onGranted()
        } else {
            Timber.d("RequestLocationPermission Denied")
            onDeny()
        }
    }

    LaunchedEffect(Unit) {
        if (permissions.any { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_DENIED }) {
            permissionsLauncher.launch(permissions)
        }
    }
}
