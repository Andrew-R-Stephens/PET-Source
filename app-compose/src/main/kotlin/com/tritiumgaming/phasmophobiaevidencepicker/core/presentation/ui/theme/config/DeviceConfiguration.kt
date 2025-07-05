package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.config

import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

enum class DeviceConfiguration {

    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {

        private enum class WidthConfiguration {
            COMPACT,
            MEDIUM,
            EXPANDED,
            LARGE,
            EXTRA_LARGE
        }

        private enum class HeightConfiguration {
            COMPACT,
            MEDIUM,
            EXPANDED
        }

        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val widthSize = windowSizeClass.minWidthDp
            val heightSize = windowSizeClass.minHeightDp

            val widthClass: WidthConfiguration =
                when(widthSize) {
                    in 0 ..< WIDTH_DP_MEDIUM_LOWER_BOUND ->
                        WidthConfiguration.COMPACT
                    in WIDTH_DP_MEDIUM_LOWER_BOUND ..< WIDTH_DP_EXPANDED_LOWER_BOUND ->
                        WidthConfiguration.MEDIUM
                    in WIDTH_DP_EXPANDED_LOWER_BOUND ..< 1200 ->
                        WidthConfiguration.EXPANDED
                    in 1200 ..< 1600 ->
                        WidthConfiguration.LARGE
                    else -> WidthConfiguration.EXTRA_LARGE
                }

            /*val heightClass: HeightConfiguration =
                when(heightSize) {
                    in 0 ..< HEIGHT_DP_MEDIUM_LOWER_BOUND ->
                        HeightConfiguration.COMPACT
                    in HEIGHT_DP_MEDIUM_LOWER_BOUND ..< HEIGHT_DP_EXPANDED_LOWER_BOUND ->
                        HeightConfiguration.MEDIUM
                    else ->
                        HeightConfiguration.EXPANDED
                }*/

            return when {

                widthClass <= WidthConfiguration.COMPACT -> {
                    /*if(heightClass == HeightConfiguration.COMPACT) MOBILE_LANDSCAPE
                    else*/
                    MOBILE_PORTRAIT
                }

                widthClass <= WidthConfiguration.MEDIUM -> {
                    /*if(heightClass == HeightConfiguration.MEDIUM) TABLET_LANDSCAPE
                    else*/
                    TABLET_PORTRAIT
                }
                widthClass <= WidthConfiguration.EXPANDED -> TABLET_LANDSCAPE
                widthClass <= WidthConfiguration.LARGE -> TABLET_PORTRAIT

                else -> DESKTOP

            }
        }
    }

    /*companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val widthClass = windowSizeClass.windowWidthSizeClass
            val heightClass = windowSizeClass.windowHeightSizeClass

            return when {

                widthClass == WindowWidthSizeClass.COMPACT &&
                        heightClass == WindowHeightSizeClass.COMPACT -> MOBILE_PORTRAIT

                widthClass == WindowWidthSizeClass.COMPACT &&
                        heightClass == WindowHeightSizeClass.EXPANDED -> MOBILE_PORTRAIT

                widthClass == WindowWidthSizeClass.EXPANDED &&
                        heightClass == WindowHeightSizeClass.COMPACT -> MOBILE_LANDSCAPE

                widthClass == WindowWidthSizeClass.MEDIUM &&
                        heightClass == WindowHeightSizeClass.EXPANDED -> TABLET_PORTRAIT

                widthClass == WindowWidthSizeClass.EXPANDED &&
                        heightClass == WindowHeightSizeClass.MEDIUM -> TABLET_LANDSCAPE

                else -> DESKTOP

            }
        }
    }*/

}