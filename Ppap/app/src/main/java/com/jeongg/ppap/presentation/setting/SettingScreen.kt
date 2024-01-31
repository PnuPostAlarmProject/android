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
import com.jeongg.ppap.data.util.DARK_THEME
import com.jeongg.ppap.data.util.LIGHT_THEME
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.noRippleClickable
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
        text = "구독",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun SettingUserInfo(
    email: String = ""
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SettingGrayText("가입 정보")
        SettingText(text = email)
    }
    PDivider()
}

@Composable
private fun SettingService(
    version: String = "",
    theme: String = "",
    onAlarmClick: () -> Unit = {},
    onThemeClick: (String) -> Unit = {}
) {
    val urlHandler = LocalUriHandler.current
    Column(
        modifier = Modifier.padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingGrayText(text = "서비스")
        SettingServiceItem(
            text = "공지 사항",
            onClick = { urlHandler.openUri("https://taeho1234.notion.site/ddd5759a400f4c35b146bbc16d64c8cf?pvs=4") }
        )
        SettingServiceItem(
            text = "FAQ",
            onClick = { urlHandler.openUri("https://taeho1234.notion.site/FAQ-25f5b65310cf45e7a6ba1d5b77c5ab53?pvs=4") }
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
        modifier = Modifier.padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
        modifier = Modifier.padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingGrayText(text = "기타")
        SettingServiceItem(
            text = "개인 정보 처리 방침",
            onClick = { urlHandler.openUri("https://taeho1234.notion.site/70367ab597ff46a18548126da46186c9?pvs=4") }
        )
        SettingServiceItem(
            text = "서비스 약관",
            onClick = { urlHandler.openUri("https://taeho1234.notion.site/7e7411fb884d4a269baa429474a96ed7?pvs=4") }
        )
        SettingServiceItem(
            text = "오픈 소스 라이브러리",
            onClick = { urlHandler.openUri("https://taeho1234.notion.site/05ae7cb193b4483c94ae4b4acfb86b80?pvs=4") }
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
    theme: String = "",
    onThemeClick: (String) -> Unit
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
            SettingGrayText(text = theme)
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
    onThemeClick: (String) -> Unit
) {
    if (isBottomSheetOpen.value.not()) return
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { isBottomSheetOpen.value = false },
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.large
    ) {
        BottomSheetText(
            text = "밝은 테마",
            onClick = { onThemeClick(LIGHT_THEME) }
        )
        BottomSheetText(
            text = "어두운 테마",
            onClick = { onThemeClick(DARK_THEME) }
        )
        BottomSheetText(
            text = "시스템 설정",
            onClick = { onThemeClick("") }
        )
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
            modifier = Modifier.align(Alignment.CenterEnd),
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