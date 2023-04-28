package com.mahmoudhamdyae.dailyforecast

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mahmoudhamdyae.dailyforecast.ui.MainScreen
import com.mahmoudhamdyae.dailyforecast.ui.theme.DailyForecastTheme
import com.mahmoudhamdyae.dailyforecast.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherEndToEndTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            DailyForecastTheme {
                MainScreen()
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchLocation_getWeather() {
        composeTestRule.onNodeWithStringId(R.string.empty_screen).assertIsDisplayed()
        // Enter City Name in Edit Text
        composeTestRule.onNodeWithStringId(R.string.search_edit_text_label)
            .performClick().performTextInput("Cairo")
        // Click Search Button
        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.search).performClick()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(TestTags.LOADING))

        composeTestRule.onNodeWithTag(TestTags.WEATHER_CARD).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NOT_ACCURATE_DATA).assertDoesNotExist()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchWrongLocation_getError() {
        composeTestRule.onNodeWithStringId(R.string.empty_screen).assertIsDisplayed()
        // Enter City Name in Edit Text
        composeTestRule.onNodeWithStringId(R.string.search_edit_text_label)
            .performClick().performTextInput("Cairoo")
        // Click Search Button
        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.search).performClick()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(TestTags.LOADING))

        composeTestRule.onNodeWithTag(TestTags.NOT_ACCURATE_DATA).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun search_getLoadingIndicator() {
        composeTestRule.onNodeWithStringId(R.string.empty_screen).assertIsDisplayed()
        // Enter City Name in Edit Text
        composeTestRule.onNodeWithStringId(R.string.search_edit_text_label)
            .performClick().performTextInput("Cairo")
        // Click Search Button
        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.search).performClick()

        // Assert loading is displayed then not displayed
        composeTestRule.onNodeWithTag(TestTags.LOADING).assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(TestTags.LOADING))
        composeTestRule.onNodeWithTag(TestTags.LOADING).assertDoesNotExist()
    }
}