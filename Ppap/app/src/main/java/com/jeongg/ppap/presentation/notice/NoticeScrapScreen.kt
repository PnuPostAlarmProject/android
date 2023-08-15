package com.jeongg.ppap.presentation.notice

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTitle

@Composable
fun NoticeScrapScreen(
    navController: NavController
){
    PTitle(
        title = stringResource(R.string.notice_scrap_title)
    ){
        LazyColumn {
            repeat(10){
                item { NoticeItem() }
            }
            item {
                PEmptyContent(modifier = Modifier.padding(vertical = 70.dp))
            }
        }

    }
}