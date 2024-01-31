package com.jeongg.ppap.presentation.subscribe

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.noRippleClickable
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.gray1
import com.jeongg.ppap.theme.gray5
import com.jeongg.ppap.theme.gray6

@Composable
fun SubscribeScreen(
    navController: NavController,
    viewModel: SubscribeViewModel = hiltViewModel()
){
    val subscribeList = viewModel.customSubscribes.value
    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(20.dp)
    ){
        item { SubscribeTitle() }
        item { SubscribeListTitle() }
        items(subscribeList) { subscribe ->
            val route = Screen.SubscribeAddScreen.route + "?subscribeId=${subscribe.subscribeId}"
            SubscribeItem(
                subscribe = subscribe,
                onDeleteClick = { viewModel.deleteSubscribe(subscribe.subscribeId) },
                onUpdateClick = { navController.navigate(route) },
                onAlarmClick = { viewModel.updateActive(subscribe, it) }
            )
        }
        item {
            SubscribeAddButton(
                onDefaultAddClick = { navController.navigate(Screen.SubscribeAddScreen.route) },
                onCustomAddClick = { navController.navigate(Screen.SubscribeAddScreen.route) }
            )
        }
    }
}

@Composable
private fun SubscribeAddButton(
    onDefaultAddClick: () -> Unit,
    onCustomAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ){
        Image(
            painter = painterResource(R.drawable.apple_transparent),
            contentDescription = "apple character",
            modifier = Modifier.width(140.dp)
        )
        PButton(
            text = "구독 추가하기",
            onClick = onDefaultAddClick
        )
        CustomSubscribeAdd(onCustomAddClick)
    }
}

@Composable
private fun CustomSubscribeAdd(
    onCustomAddClick: () -> Unit
) {
    Row(
        modifier = Modifier.noRippleClickable(onClick = onCustomAddClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "원하는 게시판이 등록되어있지 않다면 \n직접 등록해보세요!",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            color = gray5,
            modifier = Modifier.padding(end = 3.dp)
        )
        Icon(
            painter = painterResource(R.drawable.arrow),
            contentDescription = "navigate arrow",
            tint = gray6,
            modifier = Modifier.height(30.dp)
        )
    }
}

@Composable
private fun SubscribeItem(
    subscribe: SubscribeGetResponseDTO,
    onDeleteClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {},
    onAlarmClick: (MutableState<Boolean>) -> Unit = {}
) {
    val isActive = rememberSaveable { mutableStateOf(subscribe.isActive) }
    val img = if (isActive.value) R.drawable.subscribe_button
              else R.drawable.unsubscribe_button
    val isBottomSheet = remember { mutableStateOf(false) }

    SubscribeBottomSheet(
        isBottomSheet = isBottomSheet,
        subscribeTitle = subscribe.title,
        onDeleteClick = onDeleteClick,
        onUpdateClick = onUpdateClick,
        onAlarmClick = { onAlarmClick(isActive) }
    )
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large)
            .noRippleClickable { isBottomSheet.value = true }
            .fillMaxWidth()
            .padding(13.dp)
    ){
        Text(
            text = subscribe.title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 78.dp)
        )
        Image(
            painter = painterResource(img),
            contentDescription = "active / unactive alarm",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(68.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubscribeBottomSheet(
    isBottomSheet: MutableState<Boolean>,
    subscribeTitle: String = "",
    canUpdate: Boolean = true,
    onDeleteClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {},
    onAlarmClick: () -> Unit = {}
) {
    if (isBottomSheet.value.not()) return
    val modalBottomSheetState = rememberModalBottomSheetState()
    val isDialogOpen = remember { mutableStateOf(false) }

    PDialog(
        text = "$subscribeTitle 을 구독목록에서 삭제하시겠습니까?",
        onConfirmClick = onDeleteClick,
        isOpen = isDialogOpen,
    )
    ModalBottomSheet(
        onDismissRequest = { isBottomSheet.value = false },
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.large
    ) {
        ModalBottomSheetContent(onAlarmClick, isBottomSheet, canUpdate, onUpdateClick, isDialogOpen)
    }
}

@Composable
private fun ModalBottomSheetContent(
    onAlarmClick: () -> Unit,
    isBottomSheet: MutableState<Boolean>,
    canUpdate: Boolean,
    onUpdateClick: () -> Unit,
    isDialogOpen: MutableState<Boolean>
) {
    BottomSheetText(
        text = "알림 설정 변경하기",
        onClick = {
            onAlarmClick()
            isBottomSheet.value = false
        }
    )
    if (canUpdate) {
        BottomSheetText(
            text = "구독 수정하기",
            onClick = {
                isBottomSheet.value = false
                onUpdateClick()
            }
        )
    }
    BottomSheetText(
        text = "구독 삭제하기",
        onClick = { isDialogOpen.value = true }
    )
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
private fun SubscribeListTitle() {
    Text(
        text = "나의 구독 목록",
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
private fun SubscribeTitle() {
    Text(
        text = "구독",
        style = MaterialTheme.typography.titleLarge
    )
}