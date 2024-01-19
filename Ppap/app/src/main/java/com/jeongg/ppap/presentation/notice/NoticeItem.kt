package com.jeongg.ppap.presentation.notice

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.theme.gray3

@Composable
fun NoticeItem(
    isBookmarked: Boolean = true,
    title: String = "2023학년도 2학기 신·편입생(학부, 대학원) 학생증 발급 신청 및 배부 안내",
    date: String = "2023.08.08",
    link: String = "",
    onScrap: (Boolean) -> Unit = {}
){
    val urlHandler = LocalUriHandler.current
    var isFilled by remember { mutableStateOf(isBookmarked) }
    val favorite = if (isFilled) R.drawable.heart_filled else R.drawable.heart_empty
    Column(
        modifier = Modifier
            .clickable{ urlHandler.openUri(link) }
            .fillMaxWidth()
            .padding(top = 15.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                modifier = Modifier.padding(end = 30.dp),
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(favorite),
                contentDescription = "checked: $isFilled",
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterEnd)
                    .clickable{
                        onScrap(isFilled)
                        isFilled = !isFilled
                    }
            )
        }
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