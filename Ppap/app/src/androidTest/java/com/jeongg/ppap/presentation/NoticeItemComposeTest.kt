package com.jeongg.ppap.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jeongg.ppap.presentation.notice.NoticeItem
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NoticeItemComposeTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    private val checked = composeTestRule.onNodeWithContentDescription("checked: true")
    private val unchecked = composeTestRule.onNodeWithContentDescription("checked: false")

    @Test
    fun notice_checkedToggle(){
        val isSelected = true
        start_NoticeItem(isSelected)

        checked.assertIsDisplayed()
        unchecked.assertDoesNotExist()

        checked.performClick() // checked -> unchecked

        unchecked.assertIsDisplayed()
        checked.assertDoesNotExist()
    }
    @Test
    fun notice_unselectedToggle(){
        val isSelected = false
        start_NoticeItem(isSelected)

        unchecked.assertIsDisplayed()
        checked.assertDoesNotExist()

        unchecked.performClick() // checked -> unchecked

        checked.assertIsDisplayed()
        unchecked.assertDoesNotExist()
    }

    private fun start_NoticeItem(isChecked: Boolean = false){
        composeTestRule.setContent {
            NoticeItem(isBookmarked = isChecked)
        }
    }
}