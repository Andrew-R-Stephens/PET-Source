package com.tritiumgaming.feature.customdifficulty.ui

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import com.tritiumgaming.core.common.config.DeviceConfiguration

@Composable
fun CustomDifficultyScreen(

) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            PortraitContent(
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            LandscapeContent(
            )
        }
    }

}

@Composable
private fun PortraitContent() {

}

@Composable
private fun LandscapeContent() {

}