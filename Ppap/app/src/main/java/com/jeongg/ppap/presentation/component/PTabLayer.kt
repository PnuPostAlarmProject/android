package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.presentation.theme.main_yellow

@Composable
fun PTabLayer(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.White,
        edgePadding = 20.dp,
        containerColor = MaterialTheme.colorScheme.background,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(
                    currentTabPosition = it[selectedTabIndex],
                ),
                color = main_yellow,
            )
        },
        divider = { PDivider() },
        modifier = Modifier.padding(top = 10.dp)
    ) {
        tabs.forEachIndexed { index, text ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabClick(index) },
                text = {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleSmall,
                        color = if (selectedTabIndex == index) main_yellow else MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    }
}