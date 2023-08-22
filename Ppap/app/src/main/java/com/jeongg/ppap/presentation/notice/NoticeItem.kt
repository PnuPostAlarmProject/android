package com.jeongg.ppap.presentation.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.theme.bright_yellow
import com.jeongg.ppap.presentation.theme.gray3
import com.jeongg.ppap.presentation.theme.main_green
import com.jeongg.ppap.presentation.theme.main_pink

@Composable
fun NoticeItem(
    category: String = "학지시 공지",
    isBookmarked: Boolean = true,
    title: String = "2023학년도 2학기 신·편입생(학부, 대학원) 학생증 발급 신청 및 배부 안내",
    date: String = "2023.08.08"
){
    var isFilled by remember { mutableStateOf(isBookmarked) }
    val favorite = if (isFilled) R.drawable.favorite_filled else R.drawable.favorite_empty
    val color = when (category){
        "학지시 공지" -> bright_yellow
        "학교 공지" -> main_pink
        else -> main_green
    }
    Column(
        modifier = Modifier
            .clickable{  }
            .fillMaxWidth()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 30.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(color)
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(favorite),
                contentDescription = "checked: $isFilled",
                modifier = Modifier.padding(start = 12.dp)
                    .size(25.dp)
                    .align(Alignment.CenterEnd)
                    .clickable{isFilled = !isFilled},
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = date,
            style = MaterialTheme.typography.labelMedium,
            color = gray3,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
        PDivider()
    }
}