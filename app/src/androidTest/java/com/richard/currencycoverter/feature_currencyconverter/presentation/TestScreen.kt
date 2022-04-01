package com.richard.currencycoverter.feature_currencyconverter.presentation

import android.util.Log
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.richard.currencycoverter.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(AppModule::class)
@HiltAndroidTest
class TestScreen {
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        hiltTestRule.inject()

        composeTestRule.setContent {
            ConverterScreen()
        }
    }

    @Test
    fun testInputExist(){
            composeTestRule.onNode(
                hasTestTag("amount")
            ).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun acceptsInouts(){
       val input =  composeTestRule.onNode(
            hasTestTag("amount")
        )
        input.performTextInput("2")
        input.assertTextContains("2")
    }

    @Test
    fun hasDropDownOpener(){
        val view = composeTestRule.onNode(
            hasTestTag("dropdownStart")
        )
            .assertExists()
    }

    @Test
    fun MainDropDornExist(){
        val view = composeTestRule.onNode(
            hasTestTag("dropdownStart")
        ).assertExists()
    }

    @Test
    fun gridCurrencyListExist(){
        val view = composeTestRule.onNode(
            hasTestTag("grid")
        ).assertExists()

    }

    @Test
    fun grindCurrencyListIsClickAble(){
        val view = composeTestRule.onAllNodes(
            hasTestTag("singleCurrency")
        ).get(0).assertHasClickAction()
    }
}