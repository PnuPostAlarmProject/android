package com.jeongg.ppap.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jeongg.ppap.data.subscribe.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.subscribe.CustomSubscribe
import com.jeongg.ppap.presentation.subscribe.CustomSubscribeItem
import com.jeongg.ppap.presentation.subscribe.DefaultSubscribeItem
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SubscribeComposeTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    private val checked = composeTestRule.onNodeWithContentDescription("checked: true")
    private val unchecked = composeTestRule.onNodeWithContentDescription("checked: false")
    private val emptyContent = composeTestRule.onNode(hasTestTag("empty_content"))

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jeongg.ppap", appContext.packageName)
    }
    @Test
    fun subscribe_default_checkedToggle(){
        val isSelected = true
        start_default_subscribe_item(isSelected)

        checked.assertIsDisplayed()
        unchecked.assertDoesNotExist()

        checked.performClick() // checked -> unchecked

        unchecked.assertIsDisplayed()
        checked.assertDoesNotExist()
    }
    @Test
    fun subscribe_custom_uncheckedToggle(){
        val isSelected = false
        start_custom_subscribe_item(isSelected)
        unchecked.assertIsDisplayed()
        checked.assertDoesNotExist()
    }
    @Test
    fun subscribe_emptyItem(){
        val subscribes = emptyList<SubscribeGetResponseDTO>()
        start_custom_subscribe(subscribes)

        emptyContent.assertIsDisplayed()
    }
    @Test
    fun subscribe_nonEmptyItem(){
        val subcribes = listOf(SubscribeGetResponseDTO(1, "최강 정컴", true),
                                SubscribeGetResponseDTO(2, "최강 전기", false))
        start_custom_subscribe(subcribes)

        emptyContent.assertDoesNotExist()
    }

    private fun start_default_subscribe_item(isChecked: Boolean = false){
        composeTestRule.setContent {
            DefaultSubscribeItem(isActive = isChecked)
        }
    }
    private fun start_custom_subscribe_item(isChecked: Boolean = false){
        composeTestRule.setContent {
            CustomSubscribeItem(isActive = isChecked)
        }
    }
    private fun start_custom_subscribe(subscribeList: List<SubscribeGetResponseDTO> = emptyList()){
        composeTestRule.setContent {
            CustomSubscribe(subscribes = subscribeList)
        }
    }
}