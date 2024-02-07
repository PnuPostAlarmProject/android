package com.jeongg.ppap.presentation.setting

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.data._const.AppTheme
import com.jeongg.ppap.data._const.NotionLink
import com.jeongg.ppap.presentation.component.util.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.util.noRippleClickable
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.gray5
import com.jeongg.ppap.theme.gray6

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel()
){
    val context = LocalContext.current
    LaunchedEffectEvent(
        eventFlow = viewModel.eventFlow,
        onNavigate = { navController.navigate(Screen.LoginScreen.route) }
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = Dimens.ScreenPadding
    ){
        item { SettingTitle() }
        item { SettingUserInfo(email = viewModel.email.value) }
        item {
            SettingService(
                version = viewModel.version,
                theme = viewModel.theme.value,
                onAlarmClick = { viewModel.presentNotificationSetting(context) },
                onThemeClick = { viewModel.changeAppTheme(it) }
            )
        }
        item {
            SettingMyPage(
                onLogOutClick = { viewModel.logout() },
                onWithdrawlClick = { viewModel.withdrawl() }
            )
        }
        item { SettingExtra() }
        item { SettingCopyRight() }
    }
}

@Composable
private fun SettingTitle() {
    Text(
        text = "설정",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun SettingUserInfo(
    email: String = ""
) {
    Column(
        modifier = Modifier.padding(vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SettingGrayText("가입 정보")
        SettingText(text = email)
    }
    PDivider()
}

@Composable
private fun SettingService(
    version: String = "",
    theme: AppTheme = AppTheme.DYNAMIC_THEME,
    onAlarmClick: () -> Unit = {},
    onThemeClick: (AppTheme) -> Unit = {}
) {
    val urlHandler = LocalUriHandler.current
    Column(
        modifier = Modifier.padding(vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SettingGrayText(text = "서비스")
        SettingServiceItem(
            text = "공지 사항",
            onClick = { urlHandler.openUri(NotionLink.NOTICE.link) }
        )
        SettingServiceItem(
            text = "FAQ",
            onClick = { urlHandler.openUri(NotionLink.FAQ.link) }
        )
        SettingVersion(version)
        SettingAlarm(onAlarmClick)
        SettingTheme(theme, onThemeClick)
    }
    PDivider()
}

@Composable
private fun SettingMyPage(
    onLogOutClick: () -> Unit = {},
    onWithdrawlClick: () -> Unit = {}
) {
    val isOpen = remember { mutableStateOf(false) }
    PDialog(
        text = "정말로 회원탈퇴를 진행하시겠습니까?",
        isOpen = isOpen,
        onConfirmClick = onWithdrawlClick
    )
    Column(
        modifier = Modifier.padding(vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SettingGrayText(text = "마이페이지")
        SettingServiceItem(
            text = "로그아웃",
            onClick = onLogOutClick
        )
        SettingServiceItem(
            text = "회원탈퇴",
            onClick = { isOpen.value = true }
        )
    }
    PDivider()
}

@Composable
private fun SettingExtra() {
    val urlHandler = LocalUriHandler.current
    Column(
        modifier = Modifier.padding(vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SettingGrayText(text = "기타")
        SettingServiceItem(
            text = "개인 정보 처리 방침",
            onClick = { urlHandler.openUri(NotionLink.PERSONAL_INFORMATION.link) }
        )
        SettingServiceItem(
            text = "서비스 약관",
            onClick = { urlHandler.openUri(NotionLink.SERVICE.link) }
        )
        SettingServiceItem(
            text = "오픈 소스 라이브러리",
            onClick = { urlHandler.openUri(NotionLink.OPEN_SOURCE.link) }
        )
    }
}

@Composable
private fun SettingCopyRight() {
    Text(
        text = "© ppap all rights reserved",
        color = gray5,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
@Composable
private fun SettingTheme(
    theme: AppTheme = AppTheme.DYNAMIC_THEME,
    onThemeClick: (AppTheme) -> Unit
) {
    val context = LocalContext.current
    val isBottomSheetOpen = remember { mutableStateOf(false) }

    SettingThemeBottomSheet(
        isBottomSheetOpen = isBottomSheetOpen,
        onThemeClick = {
            if (it != theme){
                onThemeClick(it)
                Toast.makeText(context, "변경된 테마를 적용하고 싶다면\n앱을 종료한 후 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
            }
            isBottomSheetOpen.value = false
        }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable{ isBottomSheetOpen.value = true }
    ){
        Column(
            modifier = Modifier.padding(end = 33.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            SettingText(text = "테마")
            SettingGrayText(text = theme.text)
        }
        SettingArrowIcon(
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingThemeBottomSheet(
    isBottomSheetOpen: MutableState<Boolean>,
    onThemeClick: (AppTheme) -> Unit
) {
    if (isBottomSheetOpen.value.not()) return
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { isBottomSheetOpen.value = false },
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.large
    ) {
        AppTheme.values().forEach { theme ->
            BottomSheetText(
                text = theme.text,
                onClick = { onThemeClick(theme) }
            )
        }
    }
}

@Composable
private fun BottomSheetText(
    text: String = "",
    onClick: () -> Unit = {}
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(onClick)
            .padding(20.dp),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
    PDivider()
}
@Composable
private fun SettingAlarm(
    onAlarmClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(onAlarmClick)
    ){
        Column(
           modifier = Modifier.padding(end = 33.dp),
           verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
           SettingText(text = "알림 설정")
           SettingGrayText(text = "디바이스 내 알림 설정")
        }
        Icon(
            painter = painterResource(id = R.drawable.setting),
            contentDescription = "navigation alarm setting",
            tint = gray6,
            modifier = Modifier.align(Alignment.CenterEnd).height(30.dp)
        )
    }
}

@Composable
private fun SettingVersion(
    version: String = "",
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingText(text = "버전")
        SettingText(
            modifier = Modifier. align(Alignment.CenterEnd),
            text = "v$version"
        )
    }
}

@Composable
private fun SettingServiceItem(
    text: String = "",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        SettingText(
            modifier = Modifier.padding(end = 33.dp),
            text = text
        )
        SettingArrowIcon(
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun SettingArrowIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.arrow),
        contentDescription = "navigation arrow",
        tint = gray6,
        modifier = modifier.height(20.dp)
    )
}

@Composable
private fun SettingGrayText(
    text: String = ""
){
    Text(
        text = text,
        color = gray5,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun SettingText(
    modifier: Modifier = Modifier,
    text: String = ""
){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}