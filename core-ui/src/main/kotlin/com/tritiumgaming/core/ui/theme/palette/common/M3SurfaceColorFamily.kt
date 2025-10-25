package com.tritiumgaming.core.ui.theme.palette.common

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class M3SurfaceColorFamily(
    // --- Base Surface Roles ---
    /** The default color for component surfaces like Cards, Sheets, and Menus. */
    val surface: Color = Color.Unspecified,
    /** The color for text and icons placed on top of 'surface'. */
    val onSurface: Color = Color.Unspecified,

    // --- Surface Variant Role ---
    /** A subtler surface color, for components like Chips or Search Bars that need to stand out from the main surface. */
    val surfaceVariant: Color = Color.Unspecified,
    /** The color for text and icons placed on top of 'surfaceVariant'. */
    val onSurfaceVariant: Color = Color.Unspecified,

    // --- Tonal Surface Roles ---
    /** The color for the background of the entire app. */
    val background: Color = Color.Unspecified,
    /** The color for text and icons placed on top of 'background'. */
    val onBackground: Color = Color.Unspecified,

    // --- Emphasized Surface Tones ---
    /** A dimmed surface color, often used for scrims or de-emphasized backgrounds. */
    val surfaceDim: Color = Color.Unspecified,
    /** A brighter surface color for affording higher emphasis. */
    val surfaceBright: Color = Color.Unspecified,

    // --- Surface Container Tones (for layering) ---
    /** The background color with the least emphasis, the "deepest" layer. */
    val surfaceContainerLowest: Color = Color.Unspecified,
    /** A background color with low emphasis. */
    val surfaceContainerLow: Color = Color.Unspecified,
    /** The default background color for containers, with medium emphasis. */
    val surfaceContainer: Color = Color.Unspecified,
    /** A background color with high emphasis. */
    val surfaceContainerHigh: Color = Color.Unspecified,
    /** The background color with the highest emphasis, offering the most contrast. */
    val surfaceContainerHighest: Color = Color.Unspecified,

    // --- Other Important Roles ---
    /** The color for decorative elements like dividers and outlines. */
    val outline: Color = Color.Unspecified,
    /** A subtle outline variant for less emphasis. */
    val outlineVariant: Color = Color.Unspecified
)