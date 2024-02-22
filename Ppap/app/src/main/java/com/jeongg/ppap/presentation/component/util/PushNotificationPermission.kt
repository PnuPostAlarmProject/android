package com.jeongg.ppap.presentation.component.util

import android.Manifest
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.jeongg.ppap.data._const.DataStoreKey
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.presentation.component.PDialog


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {

    val context = LocalContext.current
    val key = DataStoreKey.NOTIFICATION_PERMISSION_KEY.name
    val isOpen = remember { mutableStateOf(PDataStore(context).getData(key).isEmpty()) }
    val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS) { isGranted ->
        if (!isGranted) makePermissionToastMessage(context)
    }

    if (!permissionState.status.isGranted) {
        PDialog(
            text = "'PPAP'에선 새로운 공지가 등록되면 푸시 알림을 통해 알려드립니다.\n\n" +
                    "앱 푸시에 수신 동의하시겠습니까?\n\n" +
                    "해당 권한은 [설정 > 알림 설정] 에서 변경하실 수 있습니다. ",
            isOpen = isOpen,
            onCancelClick = {
                makePermissionToastMessage(context)
                PDataStore(context).setData(key, "false")
            },
            onConfirmClick = {
                permissionState.launchPermissionRequest()
                PDataStore(context).setData(key, "true")
            }
        )
    }
}

private fun makePermissionToastMessage(context: Context) {
    Toast.makeText(context, "알림설정을 허용하지 않으면 알림을 받아보실 수 없습니다.", Toast.LENGTH_SHORT).show()
}
