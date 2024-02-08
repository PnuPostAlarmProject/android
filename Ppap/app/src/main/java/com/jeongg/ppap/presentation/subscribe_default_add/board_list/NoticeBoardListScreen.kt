package com.jeongg.ppap.presentation.subscribe_default_add.board_list

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.data.dto.UnivNoticeBoardDTO
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.util.checkAndRequestLocationPermissions
import com.jeongg.ppap.presentation.component.util.noRippleClickable
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.subscribe_default_add.SubscribeAddCardTheme
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource
import com.jeongg.ppap.theme.gray3
import com.jeongg.ppap.theme.main_yellow

@Composable
fun NoticeBoardListScreen(
    navController: NavController,
    viewModel: NoticeBoardViewModel = hiltViewModel()
){
    val boardList = viewModel.noticeBoard.value
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "알림이 비활성화되어 알림을 받을 수 없어요.", Toast.LENGTH_SHORT).show()
        }
    }
    SubscribeAddCardTheme(
        eventFlow = viewModel.eventFlow,
        text = viewModel.title.value,
        onNavigate = { navController.navigate(Screen.SubscribeScreen.route) },
        errorMessage = viewModel.errorMessage.value,
        isContentEmpty = boardList.isEmpty()
    ) {
        boardList.forEachIndexed { index, board ->
            NoticeBoardContent(
                isSelected = viewModel.isSelected(index),
                onClick = { viewModel.noticeClick(index) },
                board = board
            )
        }
        PButton(
            text = "추가하기",
            onClick = {
                checkAndRequestLocationPermissions(context, launcher)
                viewModel.addSubscribe()
            },
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun NoticeBoardContent(
    isSelected: Boolean,
    onClick: () -> Unit,
    board: UnivNoticeBoardDTO
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            interactionSource = NoRippleInteractionSource,
            colors = RadioButtonColors(
                selectedColor = main_yellow,
                unselectedColor = gray3,
                disabledSelectedColor = main_yellow,
                disabledUnselectedColor = gray3
            )
        )
        Text(
            text = board.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}