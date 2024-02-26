package com.jeongg.ppap.presentation.subscribe

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.jeongg.ppap.data.dto.subscribe.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.loading.PCircularProgress
import com.jeongg.ppap.presentation.component.util.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.util.noRippleClickable
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.gray5
import com.jeongg.ppap.theme.gray6

@Composable
fun SubscribeScreen(
    navController: NavController,
    viewModel: SubscribeViewModel = hiltViewModel()
){
    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item { SubscribeTitle() }
        item { SubscribeListTitle() }
        item {
            if (viewModel.isLoading.value) {
                PCircularProgress()
            } else {
                SubscribeContent(
                    subscribeList = viewModel.customSubscribes.value,
                    onDeleteClick = { subscribeId -> viewModel.deleteSubscribe(subscribeId) },
                    onUpdateClick = { path -> navController.navigate(path) },
                    onAlarmClick = { subscribe, isActive -> viewModel.updateActive(subscribe, isActive) }
                )
                SubscribeAddButton(
                    onDefaultAddClick = { navController.navigate(Screen.UnivListScreen.route) },
                    onCustomAddClick = { navController.navigate(Screen.SubscribeCustomAddScreen.route) }
                )
            }
        }
    }
}

@Composable
private fun SubscribeContent(
    subscribeList: List<SubscribeGetResponseDTO>,
    onDeleteClick: (Long) -> Unit,
    onUpdateClick: (String) -> Unit,
    onAlarmClick: (SubscribeGetResponseDTO, MutableState<Boolean>) -> Unit,
) {
    subscribeList.forEach { subscribe ->
        val route = Screen.SubscribeCustomUpdateScreen.route
        val query = "?subscribeId=${subscribe.subscribeId}&subscribeName=${subscribe.title}"
        SubscribeItem(
            subscribe = subscribe,
            onDeleteClick = { onDeleteClick(subscribe.subscribeId) },
            onUpdateClick = { onUpdateClick(route + query) },
            onAlarmClick = { onAlarmClick(subscribe, it) }
        )
    }
}

@Composable
private fun SubscribeAddButton(
    onDefaultAddClick: () -> Unit,
    onCustomAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 15.dp).fillMaxSize(),
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
            contentDescription = "화살표 그림(이동하기)",
            tint = gray6,
            modifier = Modifier.height(30.dp)
        )
    }
}

@Composable
fun SubscribeItem(
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
        isActive = isActive.value,
        isBottomSheet = isBottomSheet,
        subscribeTitle = subscribe.title,
        onDeleteClick = onDeleteClick,
        onUpdateClick = onUpdateClick,
        onAlarmClick = { onAlarmClick(isActive) }
    )
    Box(
        modifier = Modifier
            .padding(bottom = 15.dp)
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
            contentDescription = if (isActive.value) "활성화된 알림 그림" else "비활성화된 알림 그림",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(68.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubscribeBottomSheet(
    isActive: Boolean = false,
    isBottomSheet: MutableState<Boolean>,
    subscribeTitle: String = "",
    onDeleteClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {},
    onAlarmClick: () -> Unit = {}
) {
    if (isBottomSheet.value.not()) return
    val modalBottomSheetState = rememberModalBottomSheetState()
    val isDialogOpen = remember { mutableStateOf(false) }

    PDialog(
        text = "$subscribeTitle 을 구독목록에서 삭제하시겠습니까?",
        onConfirmClick = {
            onDeleteClick()
            isBottomSheet.value = false
        },
        isOpen = isDialogOpen,
    )
    ModalBottomSheet(
        onDismissRequest = { isBottomSheet.value = false },
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.large
    ) {
        ModalBottomSheetContent(isActive, onAlarmClick, isBottomSheet, onUpdateClick, isDialogOpen)
    }
}

@Composable
private fun ModalBottomSheetContent(
    isActive: Boolean = false,
    onAlarmClick: () -> Unit,
    isBottomSheet: MutableState<Boolean>,
    onUpdateClick: () -> Unit,
    isDialogOpen: MutableState<Boolean>
) {
    BottomSheetText(
        text = if (isActive) "알림 비활성화하기" else "알림 활성화하기",
        onClick = {
            onAlarmClick()
            isBottomSheet.value = false
        }
    )
    BottomSheetText(
        text = "구독 수정하기",
        onClick = {
            onUpdateClick()
            isBottomSheet.value = false
        }
    )
    BottomSheetText(
        modifier = Modifier.padding(bottom = 30.dp),
        text = "구독 삭제하기",
        onClick = {
            isDialogOpen.value = true
        }
    )
}

@Composable
private fun BottomSheetText(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {}
) {
    Text(
        text = text,
        modifier = modifier
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
