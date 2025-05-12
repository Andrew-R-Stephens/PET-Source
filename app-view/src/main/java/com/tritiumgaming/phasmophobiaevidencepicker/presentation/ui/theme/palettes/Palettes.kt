package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.ExtendedTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_10_commissioner
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_1_recruit
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_2_investigator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_3_pvtinvestigator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_4_detective
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_5_technician
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_6_specialist
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_7_analyst
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_8_agent
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_9_operator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_artist
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_cc
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_default
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_dev
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_discord
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_easter
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_halloween23
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_holiday22
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_holiday23
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_strategemhero
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_translator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.badge_winner
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.black
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.black_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.black_M50
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M125
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M150
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M175
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M175_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M200
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M200_A58
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M250
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M650
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M750
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M850
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M90
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M900
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.blue_M950
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M150
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M150_A10
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M150_A58
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M150_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M25
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M525
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M550
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M650
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M825
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M850
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M875
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.green_M950
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_background
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_error
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_errorContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_inverseOnSurface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_inversePrimary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_inverseSurface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onBackground
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onError
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onErrorContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onPrimary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onPrimaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onSecondary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onSecondaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onSurface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onSurfaceVariant
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onTertiary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_onTertiaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_outline
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_primary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_primaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_secondary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_secondaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_surface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_surfaceTint
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_surfaceVariant
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_tertiary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_dark_tertiaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_background
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_error
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_errorContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_inverseOnSurface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_inversePrimary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_inverseSurface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onBackground
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onError
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onErrorContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onPrimary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onPrimaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onSecondary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onSecondaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onSurface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onSurfaceVariant
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onTertiary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_onTertiaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_outline
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_primary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_primaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_secondary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_secondaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_surface
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_surfaceTint
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_surfaceVariant
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_tertiary
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.md_theme_light_tertiaryContainer
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M05
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M10
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M150
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M20
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M250
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M300
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M300_A58
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M300_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M400
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M50
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M500
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M50_A40
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M510
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M525
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M85
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M900
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.orange_M950
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_A10
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M150
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M250
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M300
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M300_A58
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M300_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M450
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M475
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M500
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M50_A40
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M525
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M550
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M825
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M850
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M875
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M900
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.purple_M950
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_A40
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M150
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M225
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M250
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M300
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M300_A58
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M300_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M350
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M375
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M500
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M850
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.red_M950
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_10_commissioner
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_1_recruit
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_2_investigator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_3_pvtinvestigator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_4_detective
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_5_technician
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_6_specialist
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_7_analyst
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_8_agent
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_9_operator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_artist
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_cc
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_default
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_deuteranomaly
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_dev
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_discord
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_easter
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_halloween23
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_holiday22
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_holiday23
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_monochromacy
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_protanomaly
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_strategemhero
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_translator
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_tritanomaly
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_whiteboard
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.title_winner
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_A58
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_A75
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M10
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M100
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M20
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M200
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M25
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M300
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M400
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M50
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M500
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M600
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M750
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.white_M850
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow_A10
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow_A40
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow_M100
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow_M25
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow_M300
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow_M500
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.yellow_M50_A10

val DefaultLightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint
)

val DefaultDarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint
)

private val ExtendedDefaultLight = ExtendedPalette()
// private val ExtendedDefaultDark = ExtendedColorScheme()

private val PET_Base = ExtendedDefaultLight.copy(

    extrasFamily = ExtrasFamily(
        title = title_default,
        badge = badge_default,
        isLightMode = false
    ),

    coreFamily = CoreColorFamily(
        primary = red_M350,
        color_1 = white,
        color_2 = white,
        color_3 = white,
        color_4 = white,
        color_5 = white,
        color_6 = white,
        color_7 = white,
        color_8 = white,
    ),

    codexFamily = CodexColorFamily(
        codex1 = Color(0xFF161C1C),
        codex2 = Color(0xFF151B1B),
        codex3 = Color(0xFF99AEB3),
        codex4 = Color(0xFFFFB43D),
        codex5 = Color(0xFF2D3635),
        codex6_gridBackground = Color(0x40161C1C),
        codex7_gridStroke = Color(0x40FFFFFF),
    ),

    background = M3ColorFamily(
        color = black
    ),
    surface = M3ColorFamily(
        color = black,
        onColor = Color(0xFF191616),
    ),

    switchTheme = SwitchColorFamily(
        trackInactive = Color(0xFF191616),
        trackActive = red_M300,
        thumbInactive = red_M300, // white_M200,
        thumbActive = Color(0xFF191616)
    ),

    textFamily = TextColorFamily(
        primary = red_M250,
        secondary = red_M300,
        tertiary = red_M350,
        body = white,
        emphasis = red_M250,
    ),

    //foregroundColor = white,
    //middlegroundColor = white_M400,
    windowBackgroundColor = black,
    windowStrokeColor = white,
    navigationFooterIconAlt = white_M200,
    backgroundColor_mapviewOverlay = black_A75,
    splashTextColor = red_M350,
    light_active = red,
    light_inactive = red_A40,
    buttonColor = red_A40,
    light_off = white_M600,
    sanityPieStartColor = white,
    sanityPieEndColor = red,
    sanityHeadSkullColor = black,
    sanityBorderColor = white,

    progressBarColorStart = red_M350,
    progressBarColorEnd = red_M350,
    progressBarColorThumbGradientStart = white,
    progressBarColorThumbGradientEnd = black,
    progressBarColorThumbOutline = Color(0xFFCCCCCC),

    inboxNotification = red_M300,
    selectedColor = red,
    selectedColor2 = red_A40,
    unselectedColor = white_M600,
    strikethroughColor = red_M300,
    circleColor = green,
    positiveSelColor = green,
    neutralSelColor = white_M100,
    negativeSelColor = red_M350,
    mapPoiFillColor = red_M300_A75,
    mapRoomBorderColor = red_M300_A58,
    mapRoomFillColor = yellow_A10,
    buttonFilterOnPrimary = white,
)

private val Colorblind = PET_Base.copy(
    extrasFamily = PET_Base.extrasFamily.copy(),
    themeFamily = PET_Base.themeFamily.copy(),
    coreFamily = PET_Base.coreFamily.copy(),
    primary = PET_Base.primary.copy(),
    secondary = PET_Base.secondary.copy(),
    tertiary = PET_Base.tertiary.copy(),
    surface = PET_Base.surface.copy(),
    codexFamily = PET_Base.codexFamily.copy(),
    switchTheme = PET_Base.switchTheme.copy(),
    textFamily = PET_Base.textFamily.copy(),
)

private val Non_Colorblind = Colorblind.copy(
    extrasFamily = Colorblind.extrasFamily.copy(),
    themeFamily = Colorblind.themeFamily.copy(),
    coreFamily = Colorblind.coreFamily.copy(),
    primary = Colorblind.primary.copy(),
    secondary = Colorblind.secondary.copy(),
    tertiary = Colorblind.tertiary.copy(),
    surface = Colorblind.surface.copy(),
    codexFamily = Colorblind.codexFamily.copy(),
    switchTheme = Colorblind.switchTheme.copy(),
    textFamily = Colorblind.textFamily.copy(),
)

val ClassicPalette = Non_Colorblind.copy(
    extrasFamily = Non_Colorblind.extrasFamily.copy(),
    themeFamily = Non_Colorblind.themeFamily.copy(),
    coreFamily = Non_Colorblind.coreFamily.copy(),
    primary = Non_Colorblind.primary.copy(),
    secondary = Non_Colorblind.secondary.copy(),
    tertiary = Non_Colorblind.tertiary.copy(),
    surface = Non_Colorblind.surface.copy(),
    codexFamily = Non_Colorblind.codexFamily.copy(),
    switchTheme = Non_Colorblind.switchTheme.copy(),
    textFamily = Non_Colorblind.textFamily.copy(),
)

private val Skin = ClassicPalette.copy(
    extrasFamily = ClassicPalette.extrasFamily.copy(),
    themeFamily = ClassicPalette.themeFamily.copy(),
    coreFamily = ClassicPalette.coreFamily.copy(),
    primary = ClassicPalette.primary.copy(),
    secondary = ClassicPalette.secondary.copy(),
    tertiary = ClassicPalette.tertiary.copy(),
    surface = ClassicPalette.surface.copy(),
    codexFamily = ClassicPalette.codexFamily.copy(),
    switchTheme = ClassicPalette.switchTheme.copy(),
    textFamily = ClassicPalette.textFamily.copy()
)

private val Prestige = Skin.copy(
    extrasFamily = Skin.extrasFamily.copy(),
    themeFamily = Skin.themeFamily.copy(),
    coreFamily = Skin.coreFamily.copy(),
    primary = Skin.primary.copy(),
    secondary = Skin.secondary.copy(),
    tertiary = Skin.tertiary.copy(),
    surface = Skin.surface.copy(),
    codexFamily = Skin.codexFamily.copy(),
    switchTheme = Skin.switchTheme.copy(),
    textFamily = Skin.textFamily.copy()
)
private val Event = Skin.copy(
    extrasFamily = Skin.extrasFamily.copy(),
    themeFamily = Skin.themeFamily.copy(),
    coreFamily = Skin.coreFamily.copy(),
    primary = Skin.primary.copy(),
    secondary = Skin.secondary.copy(),
    tertiary = Skin.tertiary.copy(),
    surface = Skin.surface.copy(),
    codexFamily = Skin.codexFamily.copy(),
    switchTheme = Skin.switchTheme.copy(),
    textFamily = Skin.textFamily.copy()
)
private val Community = Skin.copy(
    extrasFamily = Skin.extrasFamily.copy(),
    themeFamily = Skin.themeFamily.copy(),
    coreFamily = Skin.coreFamily.copy(),
    primary = Skin.primary.copy(),
    secondary = Skin.secondary.copy(),
    tertiary = Skin.tertiary.copy(),
    surface = Skin.surface.copy(),
    codexFamily = Skin.codexFamily.copy(),
    switchTheme = Skin.switchTheme.copy(),
    textFamily = Skin.textFamily.copy()
)
private val Special = Skin.copy(
    extrasFamily = Skin.extrasFamily.copy(),
    themeFamily = Skin.themeFamily.copy(),
    coreFamily = Skin.coreFamily.copy(),
    primary = Skin.primary.copy(),
    secondary = Skin.secondary.copy(),
    tertiary = Skin.tertiary.copy(),
    surface = Skin.surface.copy(),
    codexFamily = Skin.codexFamily.copy(),
    switchTheme = Skin.switchTheme.copy(),
    textFamily = Skin.textFamily.copy()
)

val Monochromacy = Colorblind.copy(

    extrasFamily = Colorblind.extrasFamily.copy(
        title = title_monochromacy
    ),

    themeFamily = PET_Base.themeFamily.copy(),
    primary = PET_Base.primary.copy(),
    secondary = PET_Base.secondary.copy(),
    tertiary = PET_Base.tertiary.copy(),
    background = PET_Base.background.copy(),
    surface = PET_Base.surface.copy(),
    codexFamily = PET_Base.codexFamily.copy(),

    coreFamily = Colorblind.coreFamily.copy(
        primary = white_M100
    ),

    textFamily = Colorblind.textFamily.copy(
        primary = white,
        secondary = white,
        tertiary = white,
        body = white,
        emphasis = white,
    ),

    switchTheme = Colorblind.switchTheme.copy(
        thumbActive = black,
        trackActive = white_M100,
        thumbInactive = white_M100,
        trackInactive = black
    ),

    light_active = white,
    light_inactive = white_M400,
    sanityPieStartColor = white,
    sanityPieEndColor = white_M500,
    progressBarColorStart = white_M500,
    progressBarColorEnd = white_M300,

    inboxNotification = white_M600,
    strikethroughColor = white_M100,
    circleColor = white,
    selectedColor = white_M100,
    unselectedColor = white_M600,
    positiveSelColor = white,
    neutralSelColor = white_M400,
    negativeSelColor = white_M600,
    mapPoiFillColor = white_A75,
    mapRoomBorderColor = white_A58,
    mapRoomFillColor = yellow_M50_A10,
    navigationFooterIconAlt = white_M200,
    splashTextColor = white_M200,
    buttonFilterOnPrimary = white,
)

val Deuteranomaly = Colorblind.copy(
    extrasFamily = Colorblind.extrasFamily.copy(
        title = title_deuteranomaly
    ),

    themeFamily = PET_Base.themeFamily.copy(),
    coreFamily = PET_Base.coreFamily.copy(
        primary =  blue_M175
    ),
    primary = PET_Base.primary.copy(),
    secondary = PET_Base.secondary.copy(),
    tertiary = PET_Base.tertiary.copy(),
    background = PET_Base.background.copy(),
    surface = PET_Base.surface.copy(),
    codexFamily = PET_Base.codexFamily.copy(),
    switchTheme = SwitchColorFamily(
        thumbActive = black,
        trackActive = blue_M175,
        thumbInactive = blue_M175,
        trackInactive = black
    ),
    textFamily = PET_Base.textFamily.copy(
        primary = blue_M175,
        secondary = blue_M200,
        tertiary = blue_M250,
        body = white,
        emphasis = blue_M175,
    ),

    light_active = yellow,
    light_inactive = yellow_A40,
    sanityPieStartColor = white,
    sanityPieEndColor = yellow,
    progressBarColorStart = yellow_M500,
    progressBarColorEnd = yellow_M300,

    inboxNotification = blue_M650,
    strikethroughColor = blue_M175,
    circleColor = yellow,
    selectedColor = yellow,
    unselectedColor = white_M600,
    positiveSelColor = yellow,
    neutralSelColor = white_M400,
    negativeSelColor = blue_M650,
    mapPoiFillColor = blue_M175_A75,
    mapRoomBorderColor = blue_M200_A58,
    mapRoomFillColor = yellow_A10,
    navigationFooterIconAlt = yellow_M100,
    splashTextColor = white_M200,
    buttonFilterOnPrimary = white,
)

val Protanomaly = Colorblind.copy(
    extrasFamily = Colorblind.extrasFamily.copy(
        title = title_protanomaly
    ),

    themeFamily = PET_Base.themeFamily.copy(),
    coreFamily = PET_Base.coreFamily.copy(
        primary = blue_M175
    ),
    primary = PET_Base.primary.copy(),
    secondary = PET_Base.secondary.copy(),
    tertiary = PET_Base.tertiary.copy(),
    background = PET_Base.background.copy(),
    surface = PET_Base.surface.copy(),
    codexFamily = PET_Base.codexFamily.copy(),
    switchTheme = PET_Base.switchTheme.copy(
        trackActive = blue_M175,
        trackInactive = black,
        thumbActive = black,
        thumbInactive = blue_M175,
    ),
    textFamily = PET_Base.textFamily.copy(
        primary = blue_M175,
        secondary = blue_M200,
        tertiary = blue_M250,
        body = white,
        emphasis = blue_M175,
    ),
    
    light_active = yellow,
    light_inactive = yellow_A40,
    sanityPieStartColor = white,
    sanityPieEndColor = yellow,
    progressBarColorStart = yellow_M500,
    progressBarColorEnd = yellow_M300,

    inboxNotification = blue_M650,
    strikethroughColor = blue_M175,
    circleColor = yellow,
    selectedColor = yellow,
    unselectedColor = white_M600,
    positiveSelColor = yellow,
    neutralSelColor = white_M400,
    negativeSelColor = blue_M650,
    mapPoiFillColor = blue_M175_A75,
    mapRoomBorderColor = blue_M200_A58,
    mapRoomFillColor = yellow_A10,
    navigationFooterIconAlt = yellow_M100,
    splashTextColor = yellow_M100,
    buttonFilterOnPrimary = white,
)

val Tritanomaly = Colorblind.copy(
    extrasFamily = Colorblind.extrasFamily.copy(
        title = title_tritanomaly
    ),

    themeFamily = PET_Base.themeFamily.copy(),
    coreFamily = PET_Base.coreFamily.copy(
        primary = red
    ),

    primary = PET_Base.primary.copy(),
    secondary = PET_Base.secondary.copy(),
    tertiary = PET_Base.tertiary.copy(),
    background = PET_Base.background.copy(),
    surface = PET_Base.surface.copy(),
    codexFamily = PET_Base.codexFamily.copy(),
    switchTheme = PET_Base.switchTheme.copy(
        trackInactive = black,
        thumbInactive = red,
        thumbActive = black,
        trackActive = red,
    ),
    textFamily = PET_Base.textFamily.copy(),

    sanityPieStartColor = white,
    sanityPieEndColor = red,
    progressBarColorStart = red_M500,
    progressBarColorEnd = red_M300,

    circleColor = green,
    neutralSelColor = white,
    mapPoiFillColor = red_A75,
    buttonFilterOnPrimary = white,
)

val Whiteboard = Skin.copy(
    extrasFamily = Skin.extrasFamily.copy(
        title = title_whiteboard,
        isLightMode = true
    ),
    themeFamily = Skin.themeFamily.copy(),
    coreFamily = Skin.coreFamily.copy(
        primary = black,
        color_1 = black,
        color_2 = Color(0xFF383839),
        color_3 = white_M500,
        color_4 = Color(0xFFafb7be),
        color_5 = white_M50,
        color_6 = Color(0xFFf55656),
        color_7 = Color(0xFF1cb981),
        color_8 = Color(0xFF394494),
    ),
    primary = Skin.primary.copy(
        color = white_M50,
        onColor = white_M50
    ),
    secondary = Skin.secondary.copy(
        color = white_M50,
        onColor = white_M50
    ),
    tertiary = Skin.tertiary.copy(),

    background = Skin.background.copy(
        color = Color(0xFF383839)
    ),
    surface = Skin.surface.copy(
        color = white_M50,
        onColor = white_M50,
    ),

    codexFamily = Skin.codexFamily.copy(
        codex1 = black_A75,
        codex3 = white_M50,
        codex2 = Color(0xFF383839),
        codex4 = Color(0xFFf55656),
        codex5 = black_A75,
        codex6_gridBackground = white_M50,
    ),
    switchTheme = Skin.switchTheme.copy(
        trackActive = black_M50,
        trackInactive = black_M50,
        thumbActive = Color(0xFFf55656),
        thumbInactive = Color(0xFFafb7be)
    ),
    textFamily = Skin.textFamily.copy(
        primary = Color(0xFF383839),
        secondary = Color(0xFF383839),
        tertiary = Color(0xFF383839),
        body = Color(0xFF383839),
        emphasis = Color(0xFFf55656),
    ),

    //foregroundColor = white_M50,
    //middlegroundColor = white_M50,
    statusBarColor = white_M50,
    navigationBarColor = white_M50,
    windowBackgroundColor = white_M50,
    windowStrokeColor = Color(0xFF383839),

    navigationFooterIcon = white_M500,
    navigationFooterIconAlt = red_M300,

    backgroundColor_mapviewOverlay = Color(0xFFafb7be),

    splashTextColor = Color(0xFF383839),

    light_active = Color(0xFF383839),
    light_inactive = Color(0xFF383839),
    light_off = Color(0xFFafb7be),

    sanityPieStartColor = white_M50,
    sanityPieEndColor = Color(0xFFf55656),

    progressBarColorStart = Color(0xFF383839),
    progressBarColorEnd = Color(0xFF383839),
    sanityHeadBrainColor = black,
    sanityHeadSkullColor = Color(0xFF383839),
    sanityBorderColor = black,


    inboxNotification = Color(0xFFf55656),

    selectedColor = Color(0xFFf55656),
    selectedColor2 = Color(0xFFf55656),
    unselectedColor = Color(0xFFafb7be),

    strikethroughColor = Color(0xFFf55656),
    circleColor = Color(0xFF383839),

    positiveSelColor = Color(0xFFf55656),
    neutralSelColor = Color(0xFF383839),
    negativeSelColor = Color(0xFFafb7be),

    mapPoiFillColor = red_M300_A75,
    mapRoomBorderColor = red_M300_A58,
    mapRoomFillColor = red_A75,

    buttonFilterOnPrimary = white_M50,
    buttonColor = Color(0xFF383839),
    colorOnButton = Color(0xFF383839),

    actionMenuTextColor = Color(0xFF383839),

    /*android:windowLightStatusBar = true),

    android:colorBackground = #383839),
    android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary),
    android:navigationBarColor = M3ColorFamily(
        color = white_M50),
    colorSecondary = M3ColorFamily(
        color = white_M50
    ),
    colorOnSecondary = M3ColorFamily(
        onColor = white_M50
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = white_M50
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = white_M50
    ),

    android:textColor = M3ColorFamily(
        color = primary),
    android:color = M3ColorFamily(
        color = primary),
    android:titleTextColor = M3ColorFamily(
        color = primary),
    android:subtitleTextColor = M3ColorFamily(
        color = body),
    */
)

val Recruit = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_1_recruit,
        badge = badge_1_recruit
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white,
        b = white_M50,
        c = orange_M150,
        d = Color(0xFFB2B1B1),
        e = Color(0xFF818180),
        f = Color(0xFF515151),
        g = Color(0xFF252423),
        h = orange_M950,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF232C2C),
        codex2 = Color(0xFF252423),
        codex3 = orange_M150,
        codex4 = orange_M150,
        codex5 = Color(0xFF252423),
        codex6_gridBackground = Color(0x40232C2C),
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = orange_M150,
        color_1 = orange_M150,
        color_2 = orange_M300,
        color_3 = orange_M900,
        color_4 = orange_M950,
        color_5 = white,
        color_6 = white_M100,
        color_7 = white_M600,
        color_8 = yellow,
    ),

    primary = Prestige.primary.copy(
        color = orange_M950,
        onColor = orange_M950
    ),
    secondary = Prestige.secondary.copy(
        color = orange_M950,
        onColor = orange_M950
    ),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = orange_M950,
        onColor = orange_M900,
    ),

    switchTheme = Prestige.switchTheme.copy(
        trackActive = orange_M500,
        trackInactive = orange_M900,
        thumbActive = orange_M150,
        thumbInactive = white_M100,
    ),

    textFamily = Prestige.textFamily.copy(
        primary = orange_M250,
        secondary = orange_M300,
        tertiary = orange_M400,
        body = white,
        emphasis = orange_M250,
    ),
    
    //foregroundColor = white,
    //middlegroundColor = Color(0xFF818180),
    statusBarColor = orange_M950,
    navigationBarColor = orange_M950,
    windowBackgroundColor = orange_M950,
    windowStrokeColor = white,
    light_active = orange_M150,
    light_inactive = orange_M50_A40,
    light_off = white_M600,

    strikethroughColor = orange_M300,
    circleColor = orange,
    selectedColor = orange_M150,
    selectedColor2 = orange_M150,
    unselectedColor = white_M600,
    positiveSelColor = yellow,
    neutralSelColor = white_M100,
    negativeSelColor = orange_M300,
    mapPoiFillColor = orange_M300_A75,
    mapRoomBorderColor = orange_M300_A58,
    mapRoomFillColor = yellow_A10,

    backgroundColor_mapviewOverlay = Color(0xBF090500),

    progressBarColorStart = orange_M500,
    progressBarColorEnd = orange_M300,
    sanityPieStartColor = white,
    sanityPieEndColor = orange_M150,

    splashTextColor = orange_M150,
    navigationFooterIcon = Color(0xFF818180),
    navigationFooterIconAlt = orange_M150,

    buttonFilterOnPrimary = white_M50,
    inboxNotification = orange_M150

    /*
    colorBackground = M3ColorFamily(
        color = orange_M950
    ),
    statusBarColor = M3ColorFamily(
        color = orange_M950
    ),
    navigationBarColor = M3ColorFamily(
        color = orange_M950
    ),
    colorprimary = Prestige.primary.copy(
        color = orange_M950
    ),
    colorOnprimary = Prestige.primary.copy(
        color = orange_M950
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = orange_M950
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = orange_M950
    ),*/

)

val Investigator = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_2_investigator,
        badge = badge_2_investigator
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white_M10,
        b = white_M50,
        c = white_M300,
        d = purple_M75,
        e = purple_M250,
        f = purple_M525,
        g = purple_M950,
        h = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = purple_M75,
        color_1 = purple_M75,
        color_2 = purple_M75,
        color_3 = purple_M525,
        color_4 = purple_M950,
        color_5 = white,
        color_6 = white_M300,
        color_7 = white_M600,
        color_8 = black,
    ),

    primary = Prestige.primary.copy(
        color = orange_M950,
        onColor = orange_M950
    ),

    secondary = Prestige.secondary.copy(
        color = orange_M950,
        onColor = orange_M950
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = purple_M950,
        onColor = purple_M900,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF390721),
        codex2 = Color(0xFF200413),
        codex3 = white_M300,
        codex4 = purple_M75,
        codex5 = Color(0xFF3C3237),
        codex6_gridBackground = Color(0x40390721),
    ),

    switchTheme = Prestige.switchTheme.copy(
        thumbActive = purple_M75,
        thumbInactive = purple_M525,
        trackActive = purple_M525,
        trackInactive = black,
    ),

    textFamily = Prestige.textFamily.copy(
        primary = purple_M75,
        secondary = purple_M250,
        tertiary = purple_M525,
        body = white_M10,
        emphasis = purple_M250,
    ),

    //foregroundColor = white_M10,
    //middlegroundColor = purple_M250,
    statusBarColor = purple_M950,
    navigationBarColor = purple_M950,
    windowBackgroundColor = purple_M950,
    windowStrokeColor = white_M10,

    light_active = purple_M75,
    light_inactive = Color(0x6689114F),
    light_off = white_M600,

    strikethroughColor = purple_M525,
    circleColor = purple_M75,
    selectedColor = purple_M75,
    selectedColor2 = purple_M75,
    unselectedColor = white_M600,
    positiveSelColor = purple_M75,
    neutralSelColor = white_M50,
    negativeSelColor = purple_M525,
    mapPoiFillColor = Color(0xBFF11284),
    mapRoomBorderColor = Color(0x94F11284),
    mapRoomFillColor = Color(0x1AF11284),

    backgroundColor_mapviewOverlay = Color(0xBF0B000B),

    progressBarColorStart = white_M10,
    progressBarColorEnd = purple_M75,
    sanityPieStartColor = white_M10,
    sanityPieEndColor = purple_M75,

    splashTextColor = purple_M75,

    navigationFooterIcon = Color(0x4DD71472),
    navigationFooterIconAlt = purple_M250,

    buttonFilterOnPrimary = white_M300,
    inboxNotification = purple_M75

    /*
    colorBackground = M3ColorFamily(
        color = orange_M950
    ),
    statusBarColor = M3ColorFamily(
        color = orange_M950
    ),
    navigationBarColor = M3ColorFamily(
        color = orange_M950
    ),

    colorprimary = Prestige.primary.copy(
        color = orange_M950
    ),
    colorOnprimary = Prestige.primary.copy(
        color = orange_M950
    ),
    colorsecondary = Prestige.secondary.copy(
        color = orange_M950
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = orange_M950
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = orange_M950
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = orange_M950
    ),*/

)

val PrivateInvestigator = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_3_pvtinvestigator,
        badge = badge_3_pvtinvestigator
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white,
        b = orange_M85,
        c = orange_M20,
        d = orange_M10,
        e = orange_M510,
        f = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = orange_M10,
        color_1 = orange_M20,
        color_2 = orange_M10,
        color_3 = orange,
        color_4 = orange_M510,
        color_5 = white,
        color_6 = orange_M85,
        color_7 = white_M600,
        color_8 = black,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF150F08),
        codex2 = Color(0xFF332312),
        codex3 = orange_M10,
        codex4 = orange_M10,
        codex5 = Color(0xFF3A3631),
        codex6_gridBackground = Color(0x403E2A17),
    ),

    switchTheme = Prestige.switchTheme.copy(
        thumbActive = orange_M10,
        trackActive = orange_M500,
        thumbInactive = orange_M500,
        trackInactive = black,
    ),

    textFamily = Prestige.textFamily.copy(
        primary = orange_M20,
        secondary = orange_M10,
        tertiary = orange_M510,
        body = white,
        emphasis = orange_M20,
    ),

    primary = Prestige.primary.copy(
        color = black,
        onColor = black
    ),
    secondary = Prestige.secondary.copy(
        color = black,
        onColor = black
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = black,
        onColor = black_M50,
    ),

    //foregroundColor = white,
    //middlegroundColor = orange_M510,

    statusBarColor = black,
    navigationBarColor = black,
    windowBackgroundColor = black,
    windowStrokeColor = white,

    light_active = orange,
    light_inactive = orange_M50_A40,
    light_off = orange_M510,

    strikethroughColor = orange_M510,
    circleColor = orange,
    selectedColor = orange_M10,
    selectedColor2 = orange_M50_A40,
    unselectedColor = white_M600,
    positiveSelColor = orange_M10,
    neutralSelColor = orange_M85,
    negativeSelColor = orange_M510,
    mapPoiFillColor = orange_M300_A75,
    mapRoomBorderColor = orange_M300_A58,
    mapRoomFillColor = yellow_A10,

    backgroundColor_mapviewOverlay = black_A75,

    progressBarColorStart = orange_M85,
    progressBarColorEnd = orange_M10,
    sanityPieStartColor = orange_M85,
    sanityPieEndColor = orange_M10,

    splashTextColor = orange_M10,
    navigationFooterIcon = Color(0x8078522B),
    navigationFooterIconAlt = orange_M10,

    buttonFilterOnPrimary = orange_M20,
    inboxNotification = orange_M10

    /*
    colorBackground = M3ColorFamily(
        color = black
    ),
    statusBarColor = M3ColorFamily(
        color = black
    ),
    navigationBarColor = M3ColorFamily(
        color = black
    ),
    colorprimary = Prestige.primary.copy(
        color = black
    ),
    colorOnprimary = Prestige.primary.copy(
        color = black
    ),
    colorsecondary = Prestige.secondary.copy(
        color = black
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = black
    ),colorPrimaryVariant = M3ColorFamily(
        color = black
    ),colorSecondaryVariant = M3ColorFamily(
        color = black
    ),*/

)

val Detective = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_4_detective,
        badge = badge_4_detective
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white_M20,
        b = white_M750,
        c = white_M850,
        f = green_M25,
        g = green_M525,
        h = green_M825,
        i = green_M875,
        j = green_M950,
        k = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = green_M25,
        color_1 = white_M20,
        color_2 = green_M25,
        color_3 = green_M525,
        color_4 = green_M950,
        color_5 = white,
        color_6 = white_M750,
        color_7 = green_M825,
        color_8 = black,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF1E221F),
        codex2 = green_M825,
        codex3 = Color(0xFFB0E3CE),
        codex4 = green_M25,
        codex5 = white_M850,
        codex6_gridBackground = Color(0x401E221F),
    ),

    textFamily = Prestige.textFamily.copy(
        primary = green_M25,
        secondary = green_M525,
        tertiary = Color(0xFF44BF8C),
        body = white_M20,
        emphasis = green_M525,
    ),

    primary = Prestige.primary.copy(
        color = green_M875,
        onColor = green_M875
    ),
    secondary = Prestige.secondary.copy(
        color = green_M875,
        onColor = green_M875
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = green_M875,
        onColor = green_M825,
    ),
    switchTheme = Prestige.switchTheme.copy(
        thumbActive = green_M825,
        trackActive = green_M25,
        thumbInactive = green_M25,
        trackInactive = green_M825,
    ),

    //foregroundColor = white_M20,
    //middlegroundColor = white_M400,

    statusBarColor = green_M875,
    navigationBarColor = green_M875,
    windowBackgroundColor = green_M875,
    windowStrokeColor = white_M20,

    light_active = green_M25,
    light_inactive = green_M525,
    light_off = Color(0xFF2E332F),

    strikethroughColor = white_M600,
    circleColor = green_M25,
    selectedColor = green_M25,
    selectedColor2 = green_M525,
    unselectedColor = white_M600,
    positiveSelColor = green_M25,
    neutralSelColor = white_M600,
    negativeSelColor = white_M300,
    mapPoiFillColor = Color(0xBF1A9863),
    mapRoomBorderColor = Color(0x941A9863),
    mapRoomFillColor = Color(0x1A1A9863),

    backgroundColor_mapviewOverlay = Color(0xBF0F100F),

    progressBarColorStart = white_M20,
    progressBarColorEnd = green_M25,
    sanityPieStartColor = white_M20,
    sanityPieEndColor = green_M25,

    splashTextColor = green_M25,
    navigationFooterIcon = white_M600,
    navigationFooterIconAlt = green_M25,

    buttonFilterOnPrimary = white_M20,
    inboxNotification = green_M25

    /*
    colorBackground = M3ColorFamily(
        color =
    ),
    statusBarColor = M3ColorFamily(
        color =
    ),
    navigationBarColor = M3ColorFamily(
        color =
    ),

    colorprimary = Prestige.primary.copy(
        color = green_M875
    ),
    colorOnprimary = Prestige.primary.copy(
        color = green_M875
    ),
    colorsecondary = Prestige.secondary.copy(
        color = green_M875
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = green_M875
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = green_M875
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = green_M875
    ),*/

)

val Technician = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_5_technician,
        badge = badge_5_technician
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white,
        b = Color(0xFFE0E0E0),
        c = Color(0xFFABABAB),
        d = red_M375,
        e = Color(0xFF91100C),
        f = Color(0xFF5E0D0B),
        g = Color(0xFF280A09),
        h = Color(0xFF102508),
        i = Color(0xFF081204),
        j = Color(0xFF050C04),
        k = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = red_M375,
        color_1 = red_M375,
        color_2 = Color(0xFF91100C),
        color_3 = Color(0xFF5E0D0B),
        color_4 = Color(0xFF050C04),
        color_5 = white,
        color_6 = Color(0xFFE0E0E0),
        color_7 = Color(0xFFABABAB),
        color_8 = black,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF200605),
        codex2 = Color(0xFF120202),
        codex3 = Color(0xFFE4DEDE),
        codex4 = white,
        codex5 = Color(0xFF575050),
        codex6_gridBackground = Color(0x403E1312),
    ),

    textFamily = Prestige.textFamily.copy(
        primary = red_M375,
        secondary = Color(0xFF91100C),
        tertiary = Color(0xFF8E3431),
        body = white,
        emphasis = Color(0xFFABABAB),
    ),

    primary = Prestige.primary.copy(
        color = Color(0xFF050C04),
        onColor = Color(0xFF050C04)
    ),
    secondary = Prestige.secondary.copy(
        color = Color(0xFF050C04),
        onColor = Color(0xFF050C04)
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = Color(0xFF050C04),
        onColor = Color(0xFF081204),
    ),

    switchTheme = Prestige.switchTheme.copy(
        thumbActive = black,
        trackActive = red_M375,
        thumbInactive = red_M375,
        trackInactive = black,
    ),

    //foregroundColor = white,
    //middlegroundColor = white_M400,
    statusBarColor = Color(0xFF050C04),
    navigationBarColor = Color(0xFF081204),
    windowBackgroundColor = Color(0xFF050C04),
    windowStrokeColor = white,


    light_active = red_M375,
    light_inactive = Color(0xFF5E0D0B),
    light_off = white_M600,

    strikethroughColor = Color(0xFF5E0D0B),
    circleColor = red_M375,
    selectedColor = red_M375,
    selectedColor2 = Color(0xFF5E0D0B),
    unselectedColor = white_M500,
    positiveSelColor = red_M375,
    neutralSelColor = white_M600,
    negativeSelColor = Color(0xFFABABAB),

    mapPoiFillColor = Color(0xBF91100C),
    mapRoomBorderColor = Color(0x8091100C),
    mapRoomFillColor = Color(0x1A91100C),

    backgroundColor_mapviewOverlay = Color(0xBF050C04),

    progressBarColorStart = Color(0xFFE0E0E0),
    progressBarColorEnd = red_M375,
    sanityPieStartColor = Color(0xFFE0E0E0),
    sanityPieEndColor = red_M375,

    splashTextColor = red_M375,
    navigationFooterIcon = white_M600,
    navigationFooterIconAlt = red_M375,

    buttonFilterOnPrimary = Color(0xFFE0E0E0),
    inboxNotification = red_M375

    /*
    colorBackground = M3ColorFamily(
        color = Color(0xFFFF050C04)
    ),
    statusBarColor = M3ColorFamily(
        color = Color(0xFFFf050C04)
    ),
    navigationBarColor = M3ColorFamily(
        color = Color(0xFFFF050C04)
    ),

    colorprimary = Prestige.primary.copy(
        color = Color(0xFF050C04)
    ),
    colorOnprimary = Prestige.primary.copy(
        color = Color(0xFF050C04)
    ),
    colorsecondary = Prestige.secondary.copy(
        color = Color(0xFF050C04)
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = Color(0xFF050C04)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF050C04)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF050C04)
    ),*/

)
val Specialist = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_6_specialist,
        badge = badge_6_specialist
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = Color(0xFFFEFEFE),
        b = white_M25,
        c = Color(0xFFE7E8E7),
        d = Color(0xFFC9CCC8),
        e = Color(0xFF30362B),
        f = Color(0xFF545B4D),
        g = green_M150,
        h = Color(0xFF80994D),
        i = Color(0xFFA0B17B),
        j = green_M850,
        k = Color(0xFF0F1B0A),
        l = Color(0xFF070C05),
        m = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = green_M850,
        color_1 = green_M150,
        color_2 = Color(0xFF80994D),
        color_3 = green_M850,
        color_4 = Color(0xFF070C05),
        color_5 = Color(0xFFFEFEFE),
        color_6 = Color(0xFFE7E8E7),
        color_7 = Color(0xFFC9CCC8),
        color_8 = white_M600,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF070C05),
        codex2 = Color(0xFF24291F),
        codex3 = Color(0xFFD9ECAF),
        codex4 = green_M150,
        codex5 = Color(0xFF545B4D),
        codex6_gridBackground = Color(0x40070C05),
    ),

    textFamily = Prestige.textFamily.copy(
        primary = green_M150,
        secondary = Color(0xFF80994D),
        tertiary = Color(0xFF545B4D),
        body = Color(0xFFFEFEFE),
        emphasis = Color(0xFF80994D),
    ),

    primary = Prestige.primary.copy(
        color = Color(0xFF070C05),
        onColor = Color(0xFF070C05)
    ),
    secondary = Prestige.secondary.copy(
        color = Color(0xFF070C05),
        onColor = Color(0xFF070C05)
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = Color(0xFF070C05),
        onColor = Color(0xFF0F1B0A),
    ),
    switchTheme = Prestige.switchTheme.copy(
        thumbActive = black,
        trackActive = green_M150,
        thumbInactive = green_M150,
        trackInactive = black,
    ),
    
    //foregroundColor = Color(0xFFFEFEFE),
    //middlegroundColor = Color(0xFFC9CCC8),
    statusBarColor = Color(0xFF070C05),
    navigationBarColor = Color(0xFF070C05),
    windowBackgroundColor = Color(0xFF070C05),
    windowStrokeColor = Color(0xFFFEFEFE),

    light_active = green_M150,
    light_inactive = Color(0xFF80994D),
    light_off = white_M600,
    
    strikethroughColor = Color(0xFFA0B17B),
    circleColor = green_M150,
    selectedColor = Color(0xFF80994D),
    selectedColor2 = Color(0xFF80994D),
    unselectedColor = white_M600,
    positiveSelColor = green_M150,
    neutralSelColor = Color(0xFF545B4D),
    negativeSelColor = Color(0xFFA0B17B),
    mapPoiFillColor = green_M150_A75,
    mapRoomBorderColor = green_M150_A58,
    mapRoomFillColor = green_M150_A10,

    backgroundColor_mapviewOverlay = Color(0xBF070C05),

    progressBarColorStart = Color(0xFF80994D),
    progressBarColorEnd = green_M150,
    sanityPieStartColor = Color(0xFFE7E8E7),
    sanityPieEndColor = green_M150,

    splashTextColor = green_M150,
    navigationFooterIcon = Color(0xFF545B4D),
    navigationFooterIconAlt = green_M150,

    buttonFilterOnPrimary = Color(0xBF070C05),
    inboxNotification = green_M150

    /*
    colorBackground = M3ColorFamily(
        color = Color(0xFF070C05)
    ),
    statusBarColor = M3ColorFamily(
        color = Color(0xFF070C05)
    ),
    navigationBarColor = M3ColorFamily(
        color = Color(0xFF070C05)
    ),
    colorprimary = Prestige.primary.copy(
        color = Color(0xFF070C05)
    ),
    colorOnprimary = Prestige.primary.copy(
        color = Color(0xFF070C05)
    ),
    colorsecondary = Prestige.secondary.copy(
        color = Color(0xFF070C05)
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = Color(0xFF070C05)
    ),colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF070C05)
    ),colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF070C05)
    ),*/

)

val Analyst = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_7_analyst,
        badge = badge_7_analyst
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white,
        b = Color(0xFFCD2547),
        c = Color(0xFF7BCD26),
        d = Color(0xFF2667CD),
        e = Color(0xFFA7968C),
        f = Color(0xFF84736A),
        g = orange_M525,
        h = red_M850,
        i = red_M950,
        j = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = Color(0xFF84736A),
        color_1 = Color(0xFFCD2547),
        color_2 = Color(0xFF2667CD),
        color_3 = Color(0xFF7BCD26),
        color_4 = Color(0xFF101E01),
        color_5 = white,
        color_6 = Color(0xFFA7968C),
        color_7 = orange_M525,
        color_8 = red_M850,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF1C1917),
        codex3 = Color(0xFFEDE5E7),
        codex2 = Color(0xFF255BAF),
        codex4 = Color(0xFF7BCD26),
        codex5 = Color(0xFF101E01),
        codex6_gridBackground = Color(0x40201B19),
    ),

    textFamily = Prestige.textFamily.copy(
        primary = Color(0xFFCD2547),
        secondary = Color(0xFF7BCD26),
        tertiary = Color(0xFF2667CD),
        body = white,
        emphasis = Color(0xFFA7968C),
    ),

    primary = Prestige.primary.copy(
        color = red_M850,
        onColor = red_M850
    ),
    secondary = Prestige.secondary.copy(
        color = red_M850,
        onColor = red_M850
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = red_M850,
        onColor = orange_M525,
    ),

    switchTheme = Prestige.switchTheme.copy(
        thumbActive = black,
        trackActive = Color(0xFF2667CD),
        thumbInactive = Color(0xFF2667CD),
        trackInactive = black,
    ),
    
    //foregroundColor = Color(0xFFA7968C),
    //middlegroundColor = Color(0xFF84736A),

    statusBarColor = red_M850,
    navigationBarColor = red_M850,
    windowBackgroundColor = red_M850,
    windowStrokeColor = Color(0xFFA7968C),

    light_active = Color(0xFF2667CD),
    light_inactive = orange_M525,
    light_off = red_M850,

    buttonColor = Color(0xFF7BCD26),
    buttonFilterOnPrimary = Color(0xFFCD2547),
    strikethroughColor = Color(0xFFCD2547),
    circleColor = Color(0xFF7BCD26),
    selectedColor = Color(0xFF2667CD),
    selectedColor2 = Color(0xFF2667CD),
    unselectedColor = orange_M525,
    positiveSelColor = Color(0xFF7BCD26),
    neutralSelColor = Color(0xFFA7968C),
    negativeSelColor = Color(0xFFCD2547),
    mapPoiFillColor = Color(0xBF2667CD),
    mapRoomBorderColor = Color(0x942667CD),
    mapRoomFillColor = Color(0x1A2667CD),

    progressBarColorStart = Color(0xFFA7968C),
    progressBarColorEnd = Color(0xFF2667CD),
    sanityPieStartColor = Color(0xFFA7968C),
    sanityPieEndColor = Color(0xFF2667CD),

    splashTextColor = Color(0xFF2667CD),
    navigationFooterIcon = orange_M525,
    navigationFooterIconAlt = Color(0xFF2667CD),

    backgroundColor_mapviewOverlay = Color(0xBF1B1516),

    inboxNotification = Color(0xFFD3C9C3),

    /*android:colorBackground = M3ColorFamily(
    color = red_M850
    ),
    android:statusBarColor = M3ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = red_M850
    ),
    colorprimary = Prestige.primary.copy(
        color = red_M850
    ),
    colorOnprimary = Prestige.primary.copy(
        color = red_M850
    ),
    colorsecondary = Prestige.secondary.copy(
        color = red_M850
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = red_M850
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = red_M850
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = red_M850
    ),*/
)
val Agent = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_8_agent,
        badge = badge_8_agent
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white,
        b = Color(0xFF858686),
        c = Color(0xFF5E7E7B),
        d = blue_M90,
        e = Color(0xFF117166),
        f = Color(0xFF02A996),
        g = Color(0xFF167467),
        h = Color(0xFF1D4E49),
        i = Color(0xFF202A29),
        j = Color(0xFF101515),
        l = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = blue_M90,
        color_1 = white,
        color_2 = blue_M90,
        color_3 = Color(0xFF167467),
        color_4 = Color(0xFF1D4E49),
        color_5 = Color(0xFF858686),
        color_6 = Color(0xFF5E7E7B),
        color_7 = Color(0xFF202A29),
        color_8 = Color(0xFF101515),
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF101515),
        codex3 = Color(0xFFB7CFCC),
        codex2 = Color(0xFF202A29),
        codex4 = blue_M90,
        codex5 = Color(0xFF5E7E7B),
        codex6_gridBackground = Color(0x40101515),
    ),

    textFamily = Prestige.textFamily.copy(
        primary = blue_M90,
        secondary = Color(0xFF02A996),
        tertiary = Color(0xFF167467),
        body = white,
        emphasis = Color(0xFF167467),
    ),

    switchTheme = Prestige.switchTheme.copy(
        thumbActive = black,
        trackActive = blue_M90,
        thumbInactive = blue_M90,
        trackInactive = black
    ),

    primary = Prestige.primary.copy(
        color = Color(0xFF101515),
        onColor = Color(0xFF101515)
    ),
    secondary = Prestige.secondary.copy(
        color = Color(0xFF101515),
        onColor = Color(0xFF101515)
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = Color(0xFF101515),
        onColor = Color(0xFF131919),
    ),

    //foregroundColor = white,
    //middlegroundColor = Color(0xFF868586),

    statusBarColor = Color(0xFF101515),
    navigationBarColor = Color(0xFF101515),
    windowBackgroundColor = Color(0xFF0E1212),
    windowStrokeColor = white,

    light_active = blue_M90,
    light_inactive = Color(0xFF1D4E49),
    light_off = Color(0xFF202A29),

    buttonFilterOnPrimary = white,
    strikethroughColor = Color(0xFF1D4E49),
    circleColor = blue_M90,
    selectedColor = Color(0xFF167467),
    selectedColor2 = Color(0xFF167467),
    unselectedColor = Color(0xFF858686),
    positiveSelColor = blue_M90,
    neutralSelColor = Color(0xFF5E7E7B),
    negativeSelColor = Color(0xFF1D4E49),
    mapPoiFillColor = Color(0xBF02A996),
    mapRoomBorderColor = Color(0x9402A996),
    mapRoomFillColor = Color(0x1A02A996),

    progressBarColorStart = white,
    progressBarColorEnd = blue_M90,
    sanityPieStartColor = white,
    sanityPieEndColor = blue_M90,

    splashTextColor = blue_M90,
    navigationFooterIcon = Color(0xFF858686),
    navigationFooterIconAlt = blue_M90,

    backgroundColor_mapviewOverlay = Color(0xBF101515),

    inboxNotification = blue_M90,

    /*android:colorBackground = M3ColorFamily(
    color = Color(0xFF101515)
    ),
    android:statusBarColor = M3ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = Color(0xFF101515)
    ),

    colorprimary = Prestige.primary.copy(
        color = Color(0xFF101515)
    ),
    colorOnprimary = Prestige.primary.copy(
        color = Color(0xFF101515)
    ),
    colorsecondary = Prestige.secondary.copy(
        color = Color(0xFF101515)
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = Color(0xFF101515)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF101515)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF101515)
    ),*/

)
val Operator = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_9_operator,
        badge = badge_9_operator
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white,
        b = Color(0xFFFEFEFE),
        c = Color(0xFFF9F8FA),
        d = purple_M475,
        e = blue_M150,
        f = purple_M150,
        g = purple_M450,
        h = purple_M875,
        i = blue_M950,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = purple_M450,
        color_1 = purple_M150,
        color_2 = purple_M450,
        color_3 = blue_M150,
        color_4 = purple_M250,
        color_5 = Color(0xFFF9F8FA),
        color_6 = purple_M475,
        color_7 = purple_M875,
        color_8 = blue_M950,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF130B29),
        codex3 = Color(0xFFC5BCCD),
        codex2 = Color(0xFF370C37),
        codex4 = purple_M150,
        codex5 = Color(0xFF53425E),
        codex6_gridBackground = Color(0x40130B29),
    ),

    textFamily = Prestige.textFamily.copy(
        primary = blue_M150,
        secondary = purple_M300,
        tertiary = purple_M450,
        body = Color(0xFFF9F8FA),
        emphasis = purple_M250,
    ),

    switchTheme = Prestige.switchTheme.copy(
        thumbActive = black,
        thumbInactive = purple_M300,
        trackActive = purple_M300,
        trackInactive = black,
    ),

    primary = Prestige.primary.copy(
        color = blue_M950,
        onColor = blue_M950
    ),
    secondary = Prestige.secondary.copy(
        color = blue_M950,
        onColor = blue_M950
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = blue_M950,
        onColor = blue_M900,
    ),

    //foregroundColor = white,
    //middlegroundColor = blue_M150,

    statusBarColor = blue_M950,
    navigationBarColor = blue_M950,
    windowBackgroundColor = blue_M950,
    windowStrokeColor = white,

    circleColor = purple,
    mapRoomFillColor = purple_A10,
    light_inactive = purple_M50_A40,
    light_active = blue_M150,

    inboxNotification = purple_M150,
    buttonFilterOnPrimary = purple_M475,
    strikethroughColor = purple_M300,
    mapRoomBorderColor = purple_M300_A58,
    mapPoiFillColor = purple_M300_A75,
    selectedColor = purple_M450,
    selectedColor2 = purple_M50_A40,
    unselectedColor = purple_M475,
    negativeSelColor = blue_M150,
    positiveSelColor = purple_M150,
    neutralSelColor = purple_M475,
    light_off = purple_M875,

    progressBarColorStart = purple_M500,
    progressBarColorEnd = blue_M150,
    sanityPieStartColor = Color(0xFFF9F8FA),
    sanityPieEndColor = purple_M150,

    splashTextColor = purple_M150,
    navigationFooterIcon = purple_M475,
    navigationFooterIconAlt = purple_M150,

    backgroundColor_mapviewOverlay = Color(0xBF00000E),

    /*android:colorBackground = M3ColorFamily(
        color = blue_M950
        ),
android:statusBarColor = M3ColorFamily(
color = blue_M950
),
android:navigationBarColor = M3ColorFamily(
color = blue_M950
),colorprimary = Prestige.primary.copy(
        color = blue_M950
    ),
    colorOnprimary = Prestige.primary.copy(
        color = blue_M950
    ),
    colorsecondary = Prestige.secondary.copy(
        color = blue_M950
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = blue_M950
    ),colorPrimaryVariant = M3ColorFamily(
        color = blue_M950
    ),colorSecondaryVariant = M3ColorFamily(
        color = blue_M950
    ),*/

)
val Commissioner = Prestige.copy(
    extrasFamily = Prestige.extrasFamily.copy(
        title = title_10_commissioner,
        badge = badge_10_commissioner
    ),

    themeFamily = Prestige.themeFamily.copy(
        a = white,
        b = Color(0xFFACAAAC),
        c = Color(0xFFF8EFD9),
        d = orange_M05,
        e = Color(0xFFE5A348),
        f = Color(0xFF765932),
        h = purple_M825,
        i = black_M50,
        j = black,
    ),

    coreFamily = Prestige.coreFamily.copy(
        primary = orange_M05,
        color_1 = Color(0xFFF8EFD9),
        color_2 = orange_M05,
        color_3 = Color(0xFFE5A348),
        color_4 = Color(0xFF765932),
        color_5 = white,
        color_6 = Color(0xFFACAAAC),
        color_7 = purple_M825,
        color_8 = black_M50,
    ),

    codexFamily = Prestige.codexFamily.copy(
        codex1 = Color(0xFF232323),
        codex3 = Color(0xFFFFF9F0),
        codex2 = Color(0xFF130B00),
        codex4 = orange_M05,
        codex5 = Color(0xFF3C3B3B),
        codex6_gridBackground = Color(0x40232323),
    ),

    textFamily = Prestige.textFamily.copy(
        primary = orange_M05,
        secondary = Color(0xFFE5A348),
        tertiary = Color(0xFF765932),
        body = Color(0xFFF8EFD9),
        emphasis = Color(0xFFACAAAC),
    ),

    switchTheme = Prestige.switchTheme.copy(
        thumbActive = black,
        trackActive = orange_M10,
        thumbInactive = orange_M10,
        trackInactive = black,
    ),

    primary = Prestige.primary.copy(
        color = black_M50,
        onColor = black_M50
    ),
    secondary = Prestige.secondary.copy(
        color = black_M50,
        onColor = black_M50
    ),
    tertiary = Prestige.tertiary.copy(),
    background = Prestige.background.copy(

    ),
    surface = Prestige.surface.copy(
        color = black_M50,
        onColor = purple_M825,
    ),

    //foregroundColor = white,
    //middlegroundColor = white_M400,

    statusBarColor = black_M50,
    navigationBarColor = black_M50,
    windowBackgroundColor = black_M50,
    windowStrokeColor = white,

    light_active = orange_M05,
    light_inactive = Color(0xFF765932),
    light_off = purple_M825,


    inboxNotification = orange_M05,
    buttonFilterOnPrimary = Color(0xFFFDF9EE),
    strikethroughColor = Color(0xFF765932),
    circleColor = orange_M05,
    selectedColor = Color(0xFFE5A348),
    selectedColor2 = Color(0xFFE5A348),
    unselectedColor = Color(0xFFACAAAC),
    positiveSelColor = orange_M05,
    neutralSelColor = Color(0xFFACAAAC),
    negativeSelColor = Color(0xFF765932),
    mapPoiFillColor = orange_M300_A75,
    mapRoomBorderColor = orange_M300_A58,
    mapRoomFillColor = yellow_A10,

    progressBarColorStart = Color(0xFFF8EFD9),
    progressBarColorEnd = orange_M05,
    sanityPieStartColor = Color(0xFFF8EFD9),
    sanityPieEndColor = orange_M05,

    splashTextColor = orange_M05,
    navigationFooterIcon = Color(0xFFACAAAC),
    navigationFooterIconAlt = Color(0xFFE5A348),

    backgroundColor_mapviewOverlay = Color(0xBF080808)

    /*android:colorBackground = M3ColorFamily(
        color = black_M50
        ),
android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary
),
android:navigationBarColor = M3ColorFamily(
color = black_M50
),
colorprimary = Prestige.primary.copy(
        color = black_M50
    ),
    colorOnprimary = Prestige.primary.copy(
        color = black_M50
    ),
    colorsecondary = Prestige.secondary.copy(
        color = black_M50
    ),
    colorOnsecondary = Prestige.secondary.copy(
        color = black_M50
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = black_M50
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = black_M50
    ),*/
)

val Easter = Event.copy(
    extrasFamily = Event.extrasFamily.copy(
        title = title_easter,
        badge = badge_easter
    ),

    themeFamily = Event.themeFamily.copy(
        a = green_M650,
        b = green_M550,
        c = orange_M05,
        d = black,
        e = Color(0xFF030C0A),
        f = Color(0xFF1C180D),
        g = Color(0xFF21654A),
        i = Color(0xFF7CAB91),
        j = Color(0xFFE1EBE5),
        k = white,
        l = Color(0xFFF8E8BD),
    ),

    coreFamily = Event.coreFamily.copy(
        primary = green_M550,
        color_1 = white,
        color_2 = Color(0xFFF8E8BD),
        color_3 = orange_M75,
        color_4 = white_M600,
        color_5 = Color(0xFFE1EBE5),
        color_6 = green_M550,
        color_7 = Color(0xFF21654A),
        color_8 = Color(0xFF030C0A),
    ),

    codexFamily = Event.codexFamily.copy(
        codex1 = Color(0xFF203E36),
        codex3 = Color(0xFFF8E8BD),
        codex2 = Color(0xFF09231D),
        codex4 = orange_M75,
        codex5 = Color(0xFF2D3332),
        codex6_gridBackground = Color(0x40203E36),
    ),

    textFamily = Event.textFamily.copy(
        primary = green_M550,
        secondary = orange_M75,
        tertiary = Color(0xFF21654A),
        body = Color(0xFFE1EBE5),
        emphasis = Color(0xFFF8E8BD),
    ),

    switchTheme = Event.switchTheme.copy(
        thumbActive = black,
        thumbInactive = orange_M75,
        trackActive = orange_M75,
        trackInactive = black,
    ),

    primary = Event.primary.copy(
        color = Color(0xFF030C0A),
        onColor = Color(0xFF030C0A)
    ),
    secondary = Event.secondary.copy(
        color = Color(0xFF030C0A),
        onColor = Color(0xFF030C0A)
    ),
    tertiary = Event.tertiary.copy(),
    background = Event.background.copy(

    ),
    surface = Event.surface.copy(
        color = Color(0xFF030C0A),
        onColor = Color(0xFF061915),
    ),

    //foregroundColor = Color(0xFFE1EBE5),
    //middlegroundColor = Color(0xFF7CAB91),

    statusBarColor = Color(0xFF030C0A),
    navigationBarColor = Color(0xFF030C0A),
    windowBackgroundColor = Color(0xFF030C0A),
    windowStrokeColor = Color(0xFFE1EBE5),
    light_active = orange_M75,
    light_inactive = Color(0xFFF8E8BD),
    light_off = Color(0xFF1C180D),

    buttonFilterOnPrimary = Color(0xFFE1EBE5),
    inboxNotification = orange_M05,
    strikethroughColor = Color(0xFF21654A),
    circleColor = orange_M75,
    selectedColor = Color(0xFFF8E8BD),
    selectedColor2 = green_M550,
    unselectedColor = white_M600,
    positiveSelColor = orange_M75,
    neutralSelColor = Color(0xFFE1EBE5),
    negativeSelColor = Color(0xFF21654A),
    mapPoiFillColor = Color(0xBF348662),
    mapRoomBorderColor = Color(0x94ECC768),
    mapRoomFillColor = Color(0x1AECC768),

    progressBarColorStart = Color(0xFFE1EBE5),
    progressBarColorEnd = orange_M75,
    sanityPieStartColor = Color(0xFFE1EBE5),
    sanityPieEndColor = orange_M75,

    splashTextColor = green_M550,
    navigationFooterIcon = white_M600,
    navigationFooterIconAlt = orange_M75,

    backgroundColor_mapviewOverlay = Color(0xBF030C0A),

    colorOnButton = Color(0xFF030C0A)

    /*android:colorBackground = M3ColorFamily(
        color = Color(0x030C0A)
        ),
android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary
),
android:navigationBarColor = M3ColorFamily(
color = Color(0x030C0A)
),
colorprimary = Event.primary.copy(
        color = Color(0xFF030C0A)
    ),
    colorOnprimary = Event.primary.copy(
        color = Color(0xFF030C0A)
    ),
    colorsecondary = Event.secondary.copy(
        color = Color(0xFF030C0A)
    ),
    colorOnsecondary = Event.secondary.copy(
        color = Color(0xFF030C0A)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF030C0A)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF030C0A)
    ),*/

)
val Halloween23 = Event.copy(
    extrasFamily = Event.extrasFamily.copy(
        title = title_halloween23,
        badge = badge_halloween23
    ),

    themeFamily = Event.themeFamily.copy(
        a = white,
        b = Color(0xFF373737),
        e = Color(0xFFD7CCC3),
        f = Color(0xFF9F4B0D),
        g = Color(0xFFB3540A),
        h = Color(0xFFC85E0D),
        i = Color(0xFFEC700D),
        j = Color(0xFF3F1602),
        k = Color(0xFF130700),
        l = Color(0xFF0C0400),
        m = black,
    ),

    coreFamily = Event.coreFamily.copy(
        primary = Color(0xFFEC700D),
        color_1 = Color(0xFFEC700D),
        color_2 = Color(0xFFC85E0D),
        color_3 = Color(0xFF9F4B0D),
        color_4 = Color(0xFF3F1602),
        color_5 = Color(0xFFD7CCC3),
        color_6 = Color(0xFF373737),
        color_7 = Color(0xFF130700),
        color_8 = Color(0xFF0C0400),
    ),

    codexFamily = Event.codexFamily.copy(
        codex1 = Color(0xFF1B0A00),
        codex3 = Color(0xFFD7CCC3),
        codex2 = Color(0xFF130700),
        codex4 = Color(0xFFEC700D),
        codex5 = Color(0xFF373737),
        codex6_gridBackground = Color(0x401B0A00),
    ),

    textFamily = Event.textFamily.copy(
        primary = Color(0xFFEC700D),
        secondary = Color(0xFFC85E0D),
        tertiary = Color(0xFF9F4B0D),
        body = Color(0xFFD7CCC3),
        emphasis = Color(0xFFB3540A),
    ),

    switchTheme = Event.switchTheme.copy(
        thumbActive = black,
        trackActive = Color(0xFFEC700D),
        thumbInactive = Color(0xFFEC700D),
        trackInactive = black,
    ),

    primary = Event.primary.copy(
        color = Color(0xFF0C0400),
        onColor = Color(0xFF0C0400)
    ),
    secondary = Event.secondary.copy(
        color = Color(0xFF0C0400),
        onColor = Color(0xFF0C0400)
    ),
    tertiary = Event.tertiary.copy(),
    background = Event.background.copy(

    ),
    surface = Event.surface.copy(
        color = Color(0xFF0C0400),
        onColor = Color(0xFF3F1602),
    ),

    //foregroundColor = white,
    //middlegroundColor = Color(0xFFD7CCC3),

    statusBarColor = Color(0xFF0C0400),
    navigationBarColor = Color(0xFF0C0400),
    windowBackgroundColor = Color(0xFF0C0400),
    windowStrokeColor = white,
    light_active = Color(0xFFEC700D),
    light_inactive = Color(0xFF9F4B0D),
    light_off = Color(0xFF3F1602),

    buttonFilterOnPrimary = Color(0xFFD7CCC3),
    strikethroughColor = Color(0xFFD7CCC3),
    circleColor = Color(0xFFEC700D),
    selectedColor = Color(0xFFC85E0D),
    selectedColor2 = Color(0xFFC85E0D),
    unselectedColor = Color(0xFF373737),
    positiveSelColor = Color(0xFFEC700D),
    neutralSelColor = Color(0xFF373737),
    negativeSelColor = Color(0xFFD7CCC3),
    mapPoiFillColor = Color(0xBFEC700D),
    mapRoomBorderColor = Color(0x94EC700D),
    mapRoomFillColor = Color(0x1AEC700D),

    progressBarColorStart = Color(0xFFD7CCC3),
    progressBarColorEnd = Color(0xFFEC700D),
    sanityPieStartColor = Color(0xFFD7CCC3),
    sanityPieEndColor = Color(0xFFEC700D),

    splashTextColor = Color(0xFFEC700D),
    navigationFooterIcon = Color(0xFF373737),
    navigationFooterIconAlt = Color(0xFFC85E0D),

    backgroundColor_mapviewOverlay = Color(0xBF0C0400),

    inboxNotification = Color(0xFFEC700D),

    /*android:colorBackground = M3ColorFamily(
        color = Color(0x0C0400)
        ),
    android:statusBarColor = M3ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = Color(0x0C0400)
    ),
    colorprimary = Event.primary.copy(
        color = Color(0xFF0C0400)
    ),
    colorOnprimary = Event.primary.copy(
        color = Color(0xFF0C0400)
    ),
    colorsecondary = Event.secondary.copy(
        color = Color(0xFF0C0400)
    ),
    colorOnsecondary = Event.secondary.copy(
        color = Color(0xFF0C0400)
    ),colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF0C0400)
    ),colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF0C0400)
    ),*/

)
val Holiday22 = Event.copy(
    extrasFamily = Event.extrasFamily.copy(
        title = title_holiday22,
        badge = badge_holiday22
    ),

    themeFamily = Event.themeFamily.copy(
        a = Color(0xFFFEFEFE),
        b = Color(0xFFDDDCDB),
        c = orange_M50,
        d = Color(0xFF645956),
        e = Color(0xFFF1F3F7),
        f = blue_M125,
        k = Color(0xFF2E5497),
        g = Color(0xFF2A2E45),
        h = blue_M750,
        i = Color(0xFF0D0E19),
        j = black,
    ),

    coreFamily = Event.coreFamily.copy(
        primary = blue_M125,
        color_1 = Color(0xFFFEFEFE),
        color_2 = Color(0xFFDDDCDB),
        color_3 = Color(0xFFF8BC7F),
        color_4 = orange_M50,
        color_5 = blue_M125,
        color_6 = Color(0xFF2E5497),
        color_7 = Color(0xFF2A2E45),
        color_8 = Color(0xFF0D0E19),
    ),

    codexFamily = Event.codexFamily.copy(
        codex1 = Color(0xFF293750),
        codex3 = Color(0xFFF3E8DE),
        codex2 = blue_M750,
        codex4 = orange_M50,
        codex5 = Color(0xFF2A2E45),
        codex6_gridBackground = Color(0x40293750),
    ),

    textFamily = Event.textFamily.copy(
        primary = orange_M50,
        secondary = blue_M125,
        tertiary = Color(0xFF2E5497),
        body = Color(0xFFFEFEFE),
        emphasis = Color(0xFFF8BC7F),
    ),

    switchTheme = Event.switchTheme.copy(
        thumbActive = black,
        trackActive = blue_M125,
        thumbInactive = blue_M125,
        trackInactive = black,
    ),

    primary = Event.primary.copy(
        color = Color(0xFF0D0E19),
        onColor = Color(0xFF0D0E19)
    ),
    secondary = Event.secondary.copy(
        color = Color(0xFF0D0E19),
        onColor = Color(0xFF0D0E19)
    ),
    tertiary = Event.tertiary.copy(),
    background = Event.background.copy(

    ),
    surface = Event.surface.copy(
        color = Color(0xFF0D0E19),
        onColor = Color(0xFF151529),
    ),

    //foregroundColor = Color(0xFFFEFEFE),
    //middlegroundColor = Color(0xFF645956),

    statusBarColor = Color(0xFF0D0E19),
    navigationBarColor = Color(0xFF0D0E19),
    windowBackgroundColor = Color(0xFF0D0E19),
    windowStrokeColor = Color(0xFFFEFEFE),
    light_active = blue_M125,
    light_inactive = Color(0xFF2A2E45),
    light_off = blue_M750,

    buttonFilterOnPrimary = Color(0xFFF8BC7F),
    strikethroughColor = blue_M125,
    circleColor = orange_M50,
    selectedColor = blue_M125,
    selectedColor2 = blue_M125,
    unselectedColor = Color(0xFF2A2E45),
    positiveSelColor = orange_M50,
    neutralSelColor = Color(0xFFDDDCDB),
    negativeSelColor = blue_M125,
    mapPoiFillColor = Color(0xBFF58514),
    mapRoomBorderColor = Color(0x94447CDD),
    mapRoomFillColor = Color(0x1A447CDD),

    progressBarColorStart = Color(0xFFFEFEFE),
    progressBarColorEnd = orange_M50,
    sanityPieStartColor = Color(0xFFFEFEFE),
    sanityPieEndColor = orange_M50,

    splashTextColor = blue_M125,

    navigationFooterIcon = Color(0xFF2A2E45),
    navigationFooterIconAlt = blue_M125,

    backgroundColor_mapviewOverlay = Color(0xBF0D0E19),

    inboxNotification = Color(0xFF6996E4),

    /*android:colorBackground = M3ColorFamily(
        color = Color(0x0D0E19)
        ),
android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary
),
android:navigationBarColor = M3ColorFamily(
color = Color(0x0D0E19)
),

    colorprimary = Event.primary.copy(
        color = Color(0xFF0D0E19)
    ),
    colorOnprimary = Event.primary.copy(
        color = Color(0xFF0D0E19)
    ),
    colorsecondary = Event.secondary.copy(
        color = Color(0xFF0D0E19)
    ),
    colorOnsecondary = Event.secondary.copy(
        color = Color(0xFF0D0E19)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF0D0E19)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF0D0E19)
    ),*/

)
val Holiday23 = Event.copy(
    extrasFamily = Event.extrasFamily.copy(
        title = title_holiday23,
        badge = badge_holiday23
    ),

    themeFamily = Event.themeFamily.copy(
        a = white,
        b = Color(0xFFE4D0AE),
        c = Color(0xFFE9AD4C),
        d = Color(0xFF447CDE),
        f = Color(0xFF213A67),
        k = Color(0xFF1B1C33),
        j = black,
    ),

    coreFamily = Event.coreFamily.copy(
        primary = Color(0xFF447CDE),
        color_1 = white,
        color_2 = Color(0xFFFEFEFE),
        color_3 = Color(0xFFE4D0AE),
        color_4 = Color(0xFFE9AD4C),
        color_5 = Color(0xFF447CDE),
        color_6 = Color(0xFF2A487E),
        color_7 = Color(0xFF344663),
        color_8 = Color(0xFF1B1C33),
    ),

    codexFamily = Event.codexFamily.copy(
        codex1 = Color(0xFF2A487E),
        codex3 = Color(0xFFECDDC5),
        codex2 = Color(0xFF447CDE),
        codex4 = Color(0xFFE9AD4C),
        codex5 = Color(0xFF344663),
        codex6_gridBackground = Color(0x402A487E),
    ),

    textFamily = Event.textFamily.copy(
        primary = Color(0xFFE9AD4C),
        secondary = Color(0xFF447CDE),
        tertiary = Color(0xFF2E5497),
        body = white,
        emphasis = Color(0xFFE4D0AE),
    ),

    switchTheme = Event.switchTheme.copy(
        thumbActive = black,
        trackActive = Color(0xFF447CDE),
        thumbInactive = Color(0xFF447CDE),
        trackInactive = black,
    ),

    primary = Event.primary.copy(
        color = Color(0xFF1B1C33),
        onColor = Color(0xFF1B1C33)
    ),
    secondary = Event.secondary.copy(
        color = Color(0xFF1B1C33),
        onColor = Color(0xFF1B1C33)
    ),
    tertiary = Event.tertiary.copy(),
    background = Event.background.copy(

    ),
    surface = Event.surface.copy(
        color = Color(0xFF1B1C33),
        onColor = Color(0xFF131423),
    ),

    //foregroundColor = white,
    //middlegroundColor = Color(0xFF645956),

    statusBarColor = Color(0xFF1B1C33),
    navigationBarColor = Color(0xFF1B1C33),
    windowBackgroundColor = Color(0xFF1B1C33),
    windowStrokeColor = white,
    light_active = Color(0xFFE9AD4C),
    light_inactive = Color(0x40E9AD4C),
    light_off = Color(0xFF2A2E45),

    buttonFilterOnPrimary = Color(0xFF447CDE),
    strikethroughColor = Color(0xFF447CDE),
    circleColor = Color(0xFFE9AD4C),
    selectedColor = Color(0xFFE9AD4C),
    selectedColor2 = Color(0xFFE9AD4C),
    unselectedColor = Color(0xFF213A67),
    positiveSelColor = Color(0xFFE9AD4C),
    neutralSelColor = white,
    negativeSelColor = Color(0xFF447CDE),
    mapPoiFillColor = Color(0xBFE9AD4C),
    mapRoomBorderColor = Color(0x94E9AD4C),
    mapRoomFillColor = Color(0x1AE9AD4C),

    progressBarColorStart = Color(0xFFFEFEFE),
    progressBarColorEnd = Color(0xFFE9AD4C),
    sanityPieStartColor = Color(0xFFFEFEFE),
    sanityPieEndColor = Color(0xFFE9AD4C),

    splashTextColor = Color(0xFF447CDE),
    navigationFooterIcon = Color(0xFFB1AEA9),
    navigationFooterIconAlt = Color(0xFFE9AD4C),

    backgroundColor_mapviewOverlay = Color(0xBF1B1C33),

    inboxNotification = Color(0xFFE9AD4C),

    /*
    android:colorBackground = M3ColorFamily(
        color = Color(0x1B1C33)
    ),
    android:statusBarColor = M3ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
        color = Color(0x1B1C33)
    ),
    colorPrimary = M3ColorFamily(
        color = Color(0xFF1B1C33)
    ),
    colorOnPrimary = M3ColorFamily(
        color = Color(0xFF1B1C33)
    ),
    colorSecondary = M3ColorFamily(
        color = Color(0xFF1B1C33)
    ),
    colorOnSecondary = M3ColorFamily(
        color = Color(0xFF1B1C33)
    ),colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF1B1C33)
    ),colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF1B1C33)
    ),*/


)

val Discord = Community.copy(
    extrasFamily = Community.extrasFamily.copy(
        title = title_discord,
        badge = badge_discord
    ),
    
    themeFamily = Community.themeFamily.copy(
        a = blue_M850,
        b = blue_M75,
        c = Color.Unspecified,
        d = black,
        e = Color.Unspecified,
        f = Color.Unspecified,
        g = Color(0xFFC3C4C6),
        h = Color(0xFFE9E9EA),
        i = white,
        j = Color(0xFFF3F3F7),
        k = Color(0xFFAAB3F3),
        l = Color(0xFF717AF2),
        m = Color(0xFF131517),
        n = Color.Unspecified,
        o = Color.Unspecified,
        p = Color.Unspecified,
        q = Color.Unspecified,
    ),

    coreFamily = Community.coreFamily.copy(
        primary = Color(0xFF717AF2),
        color_1 = Color(0xFFAAB3F3),
        color_2 = blue_M75,
        color_3 = Color(0xFF5763EC),
        color_4 = blue_M850,
        color_5 = white,
        color_6 = Color(0xFFF3F3F7),
        color_7 = Color(0xFFC3C4C6),
        color_8 = Color(0xFF131517),
    ),

    codexFamily = Community.codexFamily.copy(
        codex1 = Color(0xFF1D2023),
        codex3 = Color(0xFFAAB3F3),
        codex2 = Color(0xFF131517),
        codex4 = blue_M75,
        codex5 = Color(0xFF2C3032),
        codex6_gridBackground = Color(0x401D2023),
    ),

    textFamily = Community.textFamily.copy(
        primary = blue_M75,
        secondary = Color(0xFF717AF2),
        tertiary = Color(0xFFAAB3F3),
        body = white,
        emphasis = Color(0xFFAAB3F3),
    ),

    switchTheme = Community.switchTheme.copy(
        thumbActive = black,
        trackActive = blue_M75,
        thumbInactive = blue_M75,
        trackInactive = black,
    ),

    primary = Community.primary.copy(
        color = Color(0xFF131517),
        onColor = Color(0xFF131517)
    ),
    secondary = Community.secondary.copy(
        color = Color(0xFF131517),
        onColor = Color(0xFF131517)
    ),
    tertiary = Community.tertiary.copy(),
    background = Community.background.copy(

    ),
    surface = Community.surface.copy(
        color = Color(0xFF131517),
        onColor = blue_M850,
    ),

    //foregroundColor = Color(0xFFF3F3F7),
    //middlegroundColor = Color(0xFFC3C4C6),

    statusBarColor = Color(0xFF131517),
    navigationBarColor = Color(0xFF131517),
    windowBackgroundColor = Color(0xFF131517),
    windowStrokeColor = Color(0xFFF3F3F7),
    light_active = blue_M75,
    light_inactive = Color(0xFFAAB3F3),
    light_off = blue_M850,

    buttonFilterOnPrimary = Color(0xFFAAB3F3),
    strikethroughColor = Color(0xFFC3C4C6),
    circleColor = blue_M75,
    selectedColor = Color(0xFFAAB3F3),
    selectedColor2 = Color(0xFFAAB3F3),
    unselectedColor = blue_M850,
    positiveSelColor = blue_M75,
    neutralSelColor = Color(0xFFF3F3F7),
    negativeSelColor = Color(0xFFC3C4C6),
    mapPoiFillColor = Color(0xBF5763EC),
    mapRoomBorderColor = Color(0x945763EC),
    mapRoomFillColor = Color(0x1A5763EC),

    progressBarColorStart = Color(0xFFF3F3F7),
    progressBarColorEnd = blue_M75,
    sanityPieStartColor = Color(0xFFF3F3F7),
    sanityPieEndColor = blue_M75,

    splashTextColor = blue_M75,

    navigationFooterIcon = blue_M850,
    navigationFooterIconAlt = blue_M75,

    backgroundColor_mapviewOverlay = Color(0xBF131517),

    inboxNotification = Color(0xFFF3F3F7),

    /*
    android:colorBackground = M3ColorFamily(
        color = Color(0x131517)
    ),
    android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
        color = Color(0x131517)
    ),
    colorprimary = Community.primary.copy(
        color = Color(0xFF131517)
    ),
    colorOnprimary = Community.primary.copy(
        color = Color(0xFF131517)
    ),
    colorsecondary = Community.secondary.copy(
        color = Color(0xFF131517)
    ),
    colorOnsecondary = Community.secondary.copy(
        color = Color(0xFF131517)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF131517)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF131517)
    ),*/

)

val ContentCreator = Community.copy(
    extrasFamily = Community.extrasFamily.copy(
        title = title_cc,
        badge = badge_cc
    ),


    themeFamily = Community.themeFamily.copy(
        a = white,
        b = Color(0xFFCACACA),
        c = red_M225,
        d = Color(0xFF9B1B35),
        e = Color(0xFF510E1B),
        f = Color(0xFF1E1013),
        g = Color(0xFF040303),
        h = black_M50,
        i = Color(0xFF010000)
    ),

    coreFamily = Community.coreFamily.copy(
        primary = red_M225,
        color_1 = Color(0xFFDF1740),
        color_2 = red_M225,
        color_3 = Color(0xFF510E1B),
        color_4 = Color(0xFF1E1013),
        color_5 = white,
        color_6 = Color(0xFFCACACA),
        color_7 = Color(0xFF99AEB3),
        color_8 = black_M50,
    ),

    textFamily = Community.textFamily.copy(
        primary = red_M225,
        secondary = Color(0xFF9B1B35),
        tertiary = Color(0xFF510E1B),
        body = white,
        emphasis = Color(0xFF9B1B35)
    ),

    codexFamily = Community.codexFamily.copy(
        codex1 = Color(0xFF510E1B),
        codex3 = Color(0xFF99AEB3),
        codex2 = Color(0xFF3C0A14),
        codex4 = red_M225,
        codex5 = Color(0xFF1E1013),
        codex6_gridBackground = Color(0x40510E1B),
    ),

    switchTheme = Community.switchTheme.copy(
        thumbActive = Color(0xFF1E1013),
        trackActive = red_M225,
        thumbInactive = red_M225,
        trackInactive = Color(0xFF1E1013),
    ),

    primary = Community.primary.copy(
        color = black_M50,
        onColor = black_M50
    ),
    secondary = Community.secondary.copy(
        color = black_M50,
        onColor = black_M50
    ),
    tertiary = Community.tertiary.copy(),
    background = Community.background.copy(

    ),
    surface = Community.surface.copy(
        color = black_M50,
        onColor = Color(0xFF190509),
    ),

    //foregroundColor = white,
    //middlegroundColor = Color(0xFFCACACA),

    statusBarColor = black_M50,
    navigationBarColor = black_M50,
    windowBackgroundColor = black_M50,
    windowStrokeColor = white,

    light_active = red_M225,
    light_inactive = Color(0xFF510E1B),
    light_off = Color(0xFF1E1013),

    buttonFilterOnPrimary = Color(0xFFCACACA),
    strikethroughColor = Color(0xFF780C0C),
    circleColor = Color(0xFFDF1740),
    selectedColor = red_M225,
    selectedColor2 = red_M225,
    unselectedColor = Color(0xFF510E1B),
    positiveSelColor = Color(0xFFDF1740),
    neutralSelColor = Color(0xFFCACACA),
    negativeSelColor = Color(0xFF780C0C),
    mapPoiFillColor = Color(0xBFCD2547),
    mapRoomBorderColor = Color(0x94CD2547),
    mapRoomFillColor = Color(0x1ACD2547),

    progressBarColorStart = Color(0xFFCACACA),
    progressBarColorEnd = red_M225,
    sanityPieStartColor = Color(0xFFCACACA),
    sanityPieEndColor = red_M225,

    splashTextColor = red_M225,

    navigationFooterIcon = Color(0xFF510E1B),
    navigationFooterIconAlt = Color(0xFFDF1740),

    backgroundColor_mapviewOverlay = Color(0xBF080808),

    inboxNotification = red_M225,

    /*
    android:colorBackground = M3ColorFamily(
        color = black_M50
    ),
    android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
        color = black_M50
    )

    colorprimary = Community.primary.copy(
        color = black_M50
    ),
    colorOnprimary = Community.primary.copy(
        color = black_M50
    ),
    colorsecondary = Community.secondary.copy(
        color = black_M50
    ),
    colorOnsecondary = Community.secondary.copy(
        color = black_M50
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = black_M50
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = black_M50
    ),*/

)
val Developer = Community.copy(
    extrasFamily = Community.extrasFamily.copy(
        title = title_dev,
        badge = badge_dev
    ),


    themeFamily = Community.themeFamily.copy(
        a = white,
        b = Color(0xFFE2E2DF),
        c = Color(0xFFAFA98C),
        d = yellow_M25,
        e = Color(0xFF868036),
        f = Color(0xFF4E4507),
        g = Color(0xFF171502),
        h = Color(0xFF020301),
        i = black
    ),
    coreFamily = Community.coreFamily.copy(
        primary = yellow_M25,
        color_1 = yellow_M25,
        color_2 = Color(0xFFCFCCB4),
        color_3 = Color(0xFFD3C667),
        color_4 = Color(0xFF4E4507),
        color_5 = white,
        color_6 = Color(0xFFE2E2DF),
        color_7 = Color(0xFF535353),
        color_8 = black_M50
    ),
    textFamily = Community.textFamily.copy(
        primary = yellow_M25,
        secondary = Color(0xFFCDB70B),
        tertiary = Color(0xFFD3C667),
        body = Color(0xFFE2E2DF),
        emphasis = Color(0xFFCFCCB4)
    ),

    codexFamily = Community.codexFamily.copy(
        codex1 = Color(0xFF535353),
        codex3 = yellow_M25,
        codex2 = Color(0xFF333333),
        codex4 = yellow_M25,
        codex5 = Color(0xFF332E09),
        codex6_gridBackground = Color(0x40535353)
    ),

    switchTheme = Community.switchTheme.copy(
        thumbActive = Color(0xFF4E4507),
        thumbInactive = yellow_M25,
        trackActive = yellow_M25,
        trackInactive = black,
    ),

    primary = Community.primary.copy(
        color = black_M50,
        onColor = black_M50
    ),
    secondary = Community.secondary.copy(
        color = black_M50,
        onColor = black_M50
    ),
    tertiary = Community.tertiary.copy(),
    background = Community.background.copy(

    ),
    surface = Community.surface.copy(
        color = black_M50,
        onColor = Color(0xFF171502),
    ),


    //foregroundColor = Color(0xFFE2E2DF),
    //middlegroundColor = white_M400,
    statusBarColor = black_M50,
    navigationBarColor = black_M50,
    windowBackgroundColor = black_M50,
    windowStrokeColor = Color(0xFFE2E2DF),
    light_active = yellow_M25,
    light_inactive = Color(0x66ECD316),
    light_off = Color(0x1AECD316),

    buttonFilterOnPrimary = Color(0xFFD3C667),
    strikethroughColor = Color(0xFFE2E2DF),
    circleColor = yellow_M25,
    selectedColor = yellow_M25,
    selectedColor2 = yellow_M25,
    unselectedColor = Color(0xFFE2E2DF),
    positiveSelColor = yellow_M25,
    neutralSelColor = Color(0xFF4E4507),
    negativeSelColor = Color(0xFFE2E2DF),
    mapPoiFillColor = Color(0xBFECD316),
    mapRoomBorderColor = Color(0x94ECD316),
    mapRoomFillColor = Color(0x1AECD316),

    progressBarColorStart = Color(0xFFE2E2DF),
    progressBarColorEnd = yellow_M25,
    sanityPieStartColor = Color(0xFFE2E2DF),
    sanityPieEndColor = yellow_M25,

    splashTextColor = yellow_M25,
    navigationFooterIcon = Color(0xFFE2E2DF),
    navigationFooterIconAlt = yellow_M25,

    backgroundColor_mapviewOverlay = Color(0x40535353),

    inboxNotification = Color(0xFFE2E2DF),

    /*
    android:colorBackground = M3ColorFamily(
        color = black_M50
    ),
    android:statusBarColor = M3ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = black_M50
    ),
    colorprimary = Community.primary.copy(
        color = black_M50
    ),
    colorOnprimary = Community.primary.copy(
        color = black_M50
    ),
    colorsecondary = Community.secondary.copy(
        color = black_M50
    ),
    colorOnsecondary = Community.secondary.copy(
        color = black_M50
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = black_M50
    ),colorSecondaryVariant = M3ColorFamily(
        color = black_M50
    ),*/

)
val Translator = Community.copy(
    extrasFamily = Community.extrasFamily.copy(
        title = title_translator,
        badge = badge_translator
    ),


    themeFamily = Community.themeFamily.copy(
        a = white,
        b = Color(0xFFFCFAFB),
        c = Color(0xFFE1C9CC),
        d = Color(0xFFD99CA5),
        e = Color(0xFFDA7F88),
        f = red_M150,
        g = Color(0xFF878887),
        h = Color(0xFF535052),
        i = purple_M850,
        j = black
    ),

    coreFamily = Community.coreFamily.copy(
        primary = red_M150,
        color_1 = Color(0xFFFCFAFB),
        color_2 = Color(0xFFE1C9CC),
        color_3 = Color(0xFFD99CA5),
        color_4 = red_M150,
        color_5 = white_M400,
        color_6 = white_M600,
        color_7 = purple_M850,
        color_8 = black
    ),

    codexFamily = Community.codexFamily.copy(
        codex1 = Color(0xFF33292B),
        codex3 = Color(0xFFE1C9CC),
        codex2 = Color(0xFF492126),
        codex4 = red_M150,
        codex5 = purple_M850,
        codex6_gridBackground = Color(0x4033292B)
    ),

    textFamily = Community.textFamily.copy(
        primary = red_M150,
        secondary = Color(0xFFDA7F88),
        tertiary = Color(0xFFD99CA5),
        body = Color(0xFFFCFAFB),
        emphasis = Color(0xFFE1C9CC)
    ),

    switchTheme = Community.switchTheme.copy(
        thumbActive = red_M150,
        trackActive = Color(0xFFE1C9CC),
        thumbInactive = red_M150,
        trackInactive = black,
    ),

    primary = Community.primary.copy(
        color = black,
        onColor = black
    ),
    secondary = Community.secondary.copy(
        color = black,
        onColor = black
    ),
    tertiary = Community.tertiary.copy(),
    background = Community.background.copy(

    ),
    surface = Community.surface.copy(
        color = black,
        onColor = black_M50,
    ),
    
    //foregroundColor = white,
    //middlegroundColor = white_M400,
    
    statusBarColor = black,
    navigationBarColor = black,
    windowBackgroundColor = black,
    windowStrokeColor = white,

    light_active = red_M150,
    light_inactive = Color(0xFF535052),
    light_off = purple_M850,

    buttonFilterOnPrimary = Color(0xFFFCFAFB),
    strikethroughColor = Color(0xFFD99CA5),
    circleColor = red_M150,
    selectedColor = red_M150,
    selectedColor2 = Color(0xFF535052),
    unselectedColor = white_M600,
    positiveSelColor = red_M150,
    neutralSelColor = Color(0xFFE1C9CC),
    negativeSelColor = purple_M850,
    mapPoiFillColor = Color(0xBFD66D7B),
    mapRoomBorderColor = Color(0x80D66D7B),
    mapRoomFillColor = Color(0x1AD99CA5),

    progressBarColorStart = Color(0xFFFCFAFB),
    progressBarColorEnd = red_M150,
    sanityPieStartColor = Color(0xFFFCFAFB),
    sanityPieEndColor = red_M150,

    splashTextColor = red_M150,

    navigationFooterIcon = white_M600,
    navigationFooterIconAlt = red_M150,

    backgroundColor_mapviewOverlay = black_A75,

    inboxNotification = Color(0xFFDA7F88),

    /*
    android:colorBackground = M3ColorFamily(
    color = black
    ),
    android:statusBarColor = M3ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = black

    colorPrimary = M3ColorFamily(
        color = black
    ),
    colorOnPrimary = M3ColorFamily(
        color = black
    ),
    colorSecondary = M3ColorFamily(
        color = black
    ),
    colorOnSecondary = M3ColorFamily(
        color = black
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = black
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = black
    ),*/

)

val Winner = Community.copy(
    extrasFamily = Community.extrasFamily.copy(
        title = title_winner,
        badge = badge_winner
    ),

    themeFamily = Community.themeFamily.copy(
        a = white,
        b = Color(0xFF878D8F),
        c = Color(0xFF3F4346),
        d = Color(0xFFECDFCD),
        e = Color(0xFFDABA80),
        f = orange_M250,
        g = Color(0xFF936305),
        h = blue_M850,
        i = Color(0xFF090A0B),
        j = black
    ),
    coreFamily = Community.coreFamily.copy(
        primary = orange_M250,
        color_1 = Color(0xFFECDFCD),
        color_2 = Color(0xFFDABA80),
        color_3 = orange_M250,
        color_4 = Color(0xFF936305),
        color_5 = white,
        color_6 = white_M500,
        color_7 = blue_M850,
        color_8 = Color(0xFF060C10)
    ),
    primary = Community.primary.copy(
        color = Color(0xFF060C10),
        onColor = Color(0xFF060C10)
    ),
    secondary = Community.secondary.copy(
        color = Color(0xFF060C10),
        onColor = Color(0xFF060C10)
    ),
    tertiary = Community.tertiary.copy(),
    background = Community.background.copy(

    ),
    surface = Community.surface.copy(
        color = Color(0xFF060C10),
        onColor = Color(0xFF091319)
    ),
    
    codexFamily = Community.codexFamily.copy(
        codex1 = blue_M850,
        codex3 = Color(0xFFBFB5A6),
        codex2 = Color(0xFF0A151C),
        codex4 = orange_M250,
        codex5 = Color(0xFF090A0B),
        codex6_gridBackground = Color(0x40161C1C)
    ),
    
    switchTheme = Community.switchTheme.copy(
        thumbActive = Color(0xFFECDFCD),
        trackActive = orange_M250,
        thumbInactive = orange_M250,
        trackInactive = Color(0xFF060C10),
    ),
    textFamily = Community.textFamily.copy(
        primary = orange_M250,
        secondary = Color(0xFF936305),
        tertiary = orange_M300,
        body = white,
        emphasis = Color(0xFFDABA80)
    ),
    
    //foregroundColor = white,
    //middlegroundColor = white_M400,

    statusBarColor = Color(0xFF060C10),
    navigationBarColor = Color(0xFF060C10),
    windowBackgroundColor = Color(0xFF060C10),
    windowStrokeColor = white,

    light_active = Color(0xFFDABA80),
    light_inactive = blue_M850,
    light_off = white_M600,

    buttonFilterOnPrimary = white,
    strikethroughColor = Color(0xFF936305),
    circleColor = orange_M250,
    selectedColor = Color(0xFFDABA80),
    selectedColor2 = blue_M850,
    unselectedColor = white_M600,

    positiveSelColor = orange_M250,
    neutralSelColor = white_M500,
    negativeSelColor = Color(0xFFECDFCD),

    mapPoiFillColor = Color(0xBF936305),
    mapRoomBorderColor = Color(0x80936305),
    mapRoomFillColor = yellow_A10,

    progressBarColorStart = Color(0xFFECDFCD),
    progressBarColorEnd = orange_M250,
    sanityPieStartColor = Color(0xFFECDFCD),
    sanityPieEndColor = orange_M250,

    splashTextColor = orange_M250,
    navigationFooterIcon = white_M600,
    navigationFooterIconAlt = orange_M250,

    backgroundColor_mapviewOverlay = Color(0xBF060C10),

    inboxNotification = Color(0xFFDABA80),

    /*android:colorBackground = M3ColorFamily(
    color = Color(0x060C10)
    ),
    android:statusBarColor = M3ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = Color(0x060C10)
    ),
    colorPrimary = M3ColorFamily(
        color = Color(0xFF060C10)
    ),
    colorOnPrimary = M3ColorFamily(
        color = Color(0xFF060C10)
    ),
    colorSecondary = M3ColorFamily(
        color = Color(0xFF060C10)
    ),
    colorOnSecondary = M3ColorFamily(
        color = Color(0xFF060C10)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF060C10)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF060C10)
    ),*/
)
val Artist = Community.copy(
    extrasFamily = Community.extrasFamily.copy(
        title = title_artist,
        badge = badge_artist
    ),

    themeFamily = Community.themeFamily.copy(
        a = white,
        b = Color(0xFFE3D6F7),
        c = Color(0xFFAA7FFD),
        d = purple_M550,
        e = Color(0xFF432878),
        f = Color(0xFFB0ACBA),
        g = Color(0xFF7E7C7E),
        h = blue_M850,
        i = Color(0xFF0A0C0C),
        j = black
    ),
    coreFamily = Community.coreFamily.copy(
        primary = Color(0xFFAA7FFD),
        color_1 = Color(0xFFE3D6F7),
        color_2 = Color(0xFFAA7FFD),
        color_3 = purple_M550,
        color_4 = Color(0xFF432878),
        color_5 = white,
        color_6 = Color(0xFFB0ACBA),
        color_7 = blue_M850,
        color_8 = black
    ),
    primary = Community.primary.copy(
        color = Color(0xFF0A0C0C),
        onColor = Color(0xFF0A0C0C)
    ),
    secondary = Community.secondary.copy(
        color = Color(0xFF0A0C0C),
        onColor = Color(0xFF0A0C0C)
    ),
    tertiary = Community.tertiary.copy(),
    background = Community.background.copy(

    ),
    surface = Community.surface.copy(
        color = Color(0xFF0A0C0C),
        onColor = Color(0xFF131717)
    ),
    
    codexFamily = Community.codexFamily.copy(
        codex1 = Color(0xFF0A0C0C),
        codex3 = Color(0xFFE3D6F7),
        codex2 = Color(0xFF1E1235),
        codex4 = Color(0xFFAA7FFD),
        codex5 = blue_M850,
        codex6_gridBackground = Color(0x400A0C0C)
        
    ),
    
    switchTheme = Community.switchTheme.copy(
        thumbActive = Color(0xFFE3D6F7),
        trackActive = Color(0xFFAA7FFD),
        thumbInactive = Color(0xFFAA7FFD),
        trackInactive = Color(0xFF0A0C0C)
    ),
    textFamily = Community.textFamily.copy(
        primary = purple_M550,
        secondary = Color(0xFF5B399E),
        tertiary = Color(0xFFAA7FFD),
        body = white,
        emphasis = Color(0xFFE3D6F7),
    ),

    //foregroundColor = white,
    //middlegroundColor = white_M400,
    
    statusBarColor = Color(0xFF0A0C0C),
    navigationBarColor = Color(0xFF0A0C0C),
    windowBackgroundColor = Color(0xFF0A0C0C),
    windowStrokeColor = white,

    light_active = purple_M550,
    light_inactive = Color(0xFF5B399E),
    light_off = white_M600,

    buttonFilterOnPrimary = Color(0xFFE3D6F7),
    strikethroughColor = Color(0xFF5B399E),
    circleColor = Color(0xFFE3D6F7),
    selectedColor = purple_M550,
    selectedColor2 = Color(0xFF5B399E),
    unselectedColor = white_M600,
    positiveSelColor = Color(0xFFE3D6F7),
    neutralSelColor = Color(0xFF7E7C7E),
    negativeSelColor = purple_M550,
    mapPoiFillColor = Color(0xBF5B399E),
    mapRoomBorderColor = Color(0x805B399E),
    mapRoomFillColor = yellow_A10,

    progressBarColorStart = Color(0xFFE3D6F7),
    progressBarColorEnd = Color(0xFFAA7FFD),
    sanityPieStartColor = Color(0xFFE3D6F7),
    sanityPieEndColor = purple_M550,

    splashTextColor = Color(0xFFAA7FFD),
    navigationFooterIcon = white_M600,
    navigationFooterIconAlt = purple_M550,

    backgroundColor_mapviewOverlay = Color(0xBF0A0C0C),

    inboxNotification = Color(0xFFE3D6F7),

    /*
    theme_colorPrimary = M3ColorFamily(
        color = Color(0xFFAA7FFD)
    ),
    color_1 = M3ColorFamily(
        color = Color(0xFFE3D6F7)
    ),
    color_2 = M3ColorFamily(
        color = Color(0xFFAA7FFD)
    ),
    color_3 = M3ColorFamily(
        color = purple_M550
    ),
    color_4 = M3ColorFamily(
        color = Color(0xFF432878)
    ),
    color_5 = M3ColorFamily(
        color = white
    ),
    color_6 = M3ColorFamily(
        color = Color(0xFFB0ACBA)
    ),
    color_7 = M3ColorFamily(
        color = blue_M850
    ),
    color_8 = M3ColorFamily(
        color = black
    ),
    android:colorBackground = M3ColorFamily(
        color = Color(0x0A0C0C)
    ),
    android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = Color(0x0A0C0C)),

    colorPrimary = M3ColorFamily(
        color = Color(0xFF0A0C0C)
    ),
    colorOnPrimary = M3ColorFamily(
        color = Color(0xFF0A0C0C)
    ),
    colorSecondary = M3ColorFamily(
        color = Color(0xFF0A0C0C)
    ),
    colorOnSecondary = M3ColorFamily(
        color = Color(0xFF0A0C0C)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF0A0C0C)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF0A0C0C)
    ),*/

)

val StrategemHero = Special.copy(
    extrasFamily = Special.extrasFamily.copy(
        title = title_strategemhero,
        badge = badge_strategemhero
    ),

    themeFamily = Special.themeFamily.copy(
        a = white,
        b = Color(0xFFA3A3A3),
        c = Color(0xFFFFD608),
        d = Color(0xFFBA9F36),
        e = Color(0xFFA04907),
        f = Color(0xFFDE2C38),
        g = Color(0xFF700000),
        h = Color(0xFF000B17),
        i = Color(0xFF06223B),
        j = Color(0xFF0D3A63),
        k = Color(0xFF2D70A1),
        l = Color(0xFFB7CBDC),
        m = Color(0xFFE2EDFB),
        n = Color(0xFF16564A),
        o = Color(0xFF95D2C8),
        p = Color(0xFFBBBFB3),
        q = black
    ),

    coreFamily = Special.coreFamily.copy(
        primary = white,
        color_1 = Color(0xFFA3A3A3),
        color_2 = Color(0xFFFFD608),
        color_3 = Color(0xFFA04907),
        color_4 = Color(0xFFDE2C38),
        color_5 = Color(0xFFB7CBDC),
        color_6 = Color(0xFF95D2C8),
        color_7 = Color(0xFF0D3A63),
        color_8 = Color(0xFF000B17)
    ),

    codexFamily = Special.codexFamily.copy(
        codex1 = Color(0xFF001021),
        codex3 = Color(0xFFB7CBDC),
        codex2 = Color(0xFF001021),
        codex4 = Color(0xFFFFD608),
        codex5 = Color(0xFFA3A3A3),
        codex6_gridBackground = Color(0x40001021)
    ),

    textFamily = Special.textFamily.copy(
        primary = Color(0xFFFFD608),
        secondary = white,
        tertiary = Color(0xFF2E5497),
        body = Color(0xFFA3A3A3),
        emphasis = white
    ),

    switchTheme = Special.switchTheme.copy(
        thumbActive = Color(0xFF0D3A63),
        trackActive = Color(0xFFB7CBDC),
        thumbInactive = Color(0xFFB7CBDC),
        trackInactive = Color(0xFF000B17)
    ),

    primary = Special.primary.copy(
        color = Color(0xFF000B17),
        onColor = Color(0xFF000B17)
    ),
    secondary = Special.secondary.copy(
        color = Color(0xFF000B17),
        onColor = Color(0xFF000B17)
    ),
    tertiary = Special.tertiary.copy(),
    background = Special.background.copy(

    ),
    surface = Special.surface.copy(
        color = Color(0xFF000B17),
        onColor = Color(0xFF000E1E),
    ),

    //foregroundColor = white,
    //middlegroundColor = Color(0xFF645956),

    statusBarColor = Color(0xFF000B17),
    navigationBarColor = Color(0xFF000B17),
    windowBackgroundColor = Color(0xFF000B17),
    windowStrokeColor = white,

    light_active = Color(0xFFFFD608),
    light_inactive = Color(0x40FFD608),
    light_off = Color(0xFFB7CBDC),

    strikethroughColor = Color(0xFFB7CBDC),
    circleColor = Color(0xFFFFD608),
    selectedColor = Color(0xFFFFD608),
    selectedColor2 = Color(0xFFFFD608),
    unselectedColor = Color(0xFFA3A3A3),
    positiveSelColor = Color(0xFFFFD608),
    neutralSelColor = Color(0xFFA3A3A3),
    negativeSelColor = Color(0xFFB7CBDC),

    mapPoiFillColor = Color(0xBFE9AD4C),
    mapRoomBorderColor = Color(0x94E9AD4C),
    mapRoomFillColor = Color(0x1AE9AD4C),

    progressBarColorStart = Color(0xFFB7CBDC),
    progressBarColorEnd = Color(0xFFDE2C38),
    sanityPieStartColor = Color(0xFFB7CBDC),
    sanityPieEndColor = Color(0xFFDE2C38),

    splashTextColor = Color(0xFF447CDE),

    navigationFooterIcon = white,
    navigationFooterIconAlt = Color(0xFFFFD608),

    backgroundColor_mapviewOverlay = Color(0xBF001021),

    inboxNotification = Color(0xFF95D2C8),

    buttonFilterOnPrimary = Color(0xFFB7CBDC),
    buttonBorderOnPrimary = white,
    buttonBackgroundOnPrimary = Color(0xFFB7CBDC),

    colorActionPrimary = Color(0xFFFFD608)

    /*
    theme_colorPrimary = M3ColorFamily(
        color = white
    ),
    color_1 = M3ColorFamily(
        color = Color(0xFFA3A3A3)
    ),
    color_2 = M3ColorFamily(
        color = Color(0xFFFFD608)
    ),
    color_3 = M3ColorFamily(
        color = Color(0xFFA04907)
    ),
    color_4 = M3ColorFamily(
        color = Color(0xFFDE2C38)
    ),
    color_5 = M3ColorFamily(
        color = Color(0xFFB7CBDC)
    ),
    color_6 = M3ColorFamily(
        color = Color(0xFF95D2C8)
    ),
    color_7 = M3ColorFamily(
        color = Color(0xFF0D3A63)
    ),
    color_8 = M3ColorFamily(
        color = Color(0xFF000B17)
    ),

    android:colorBackground = M3ColorFamily(
        color = Color(0x001021)
    ),
    android:statusBarColor = M3ColorFamily(
        color = colorOnSecondary
    ),
    android:navigationBarColor = M3ColorFamily(
    color = Color(0x000B17)
    ),

    colorPrimary = M3ColorFamily(
        color = Color(0xFF000B17)
    ),
    colorOnPrimary = M3ColorFamily(
        color = Color(0xFF000B17)
    ),
    colorSecondary = M3ColorFamily(
        color = Color(0xFF000B17)
    ),
    colorOnSecondary = M3ColorFamily(
        color = Color(0xFF000B17)
    ),
    colorPrimaryVariant = M3ColorFamily(
        color = Color(0xFF000B17)
    ),
    colorSecondaryVariant = M3ColorFamily(
        color = Color(0xFF000B17)
    ),*/

)

//@Immutable
data class ExtendedPalette(

    val extrasFamily: ExtrasFamily = ExtrasFamily(),

    val themeFamily: ThemeColorFamily = ThemeColorFamily(),
    val coreFamily: CoreColorFamily = CoreColorFamily(),

    val primary: M3ColorFamily = M3ColorFamily(),
    val secondary: M3ColorFamily = M3ColorFamily(),
    val tertiary: M3ColorFamily = M3ColorFamily(),
    val background: M3ColorFamily = M3ColorFamily(),
    val surface: M3ColorFamily = M3ColorFamily(),

    val codexFamily: CodexColorFamily = CodexColorFamily(),
    val switchTheme: SwitchColorFamily = SwitchColorFamily(),
    val textFamily: TextColorFamily = TextColorFamily(),

    val inverse: M3ColorFamily = M3ColorFamily(),
    val error: M3ColorFamily = M3ColorFamily(),

    //val background: M3ColorFamily = M3ColorFamily(),
    val outline: Color = Color.Unspecified,

    val surfaceTint: Color = Color.Unspecified,

    val splashTextColor: Color = Color.Unspecified, // splashTextColor

    /*val //foregroundColor: Color = Color.Unspecified, // //foregroundColor
    val //middlegroundColor: Color = Color.Unspecified, // //middlegroundColor*/
    val backgroundColor_mapviewOverlay: Color = Color.Unspecified, // backgroundColor_mapviewOverlay

    val navigationBarColor: Color = surface.color, // navigationBarColor
    val statusBarColor: Color = surface.color, // statusBarColor

    val colorActionPrimary: Color = textFamily.body, // colorActionPrimary
    val colorActionSecondary: Color = Color.Unspecified, // colorActionSecondary
    val colorActionTertiary: Color = Color.Unspecified, // colorActionTertiary

    val windowStrokeColor: Color = Color.Unspecified, // windowStrokeColor
    val windowBackgroundColor: Color = Color.Unspecified, // windowBackgroundColor
    val fragmentBackgroundColor: Color = Color.Unspecified, // fragmentBackgroundColor

    val light_active: Color = Color.Unspecified, // light_active
    val light_inactive: Color = Color.Unspecified, // light_inactive
    val light_off: Color = Color.Unspecified, // light_off

    val inboxNotification: Color = Color.Unspecified, // inboxNotification

    val buttonFilterOnPrimary: Color = Color.Unspecified, // buttonFilterOnPrimary
    val buttonBorderOnPrimary: Color = Color.Unspecified, // buttonBorderOnPrimary
    val buttonBackgroundOnPrimary: Color = Color.Unspecified, // buttonBackgroundOnPrimary

    val progressBarColorStart: Color = Color.Unspecified, // progressBarColorStart
    val progressBarColorEnd: Color = Color.Unspecified, // progressBarColorEnd
    val progressBarColorThumbGradientStart: Color =
        Color.Unspecified, // progressBarColorThumbGradientStart
    val progressBarColorThumbGradientEnd: Color =
        Color.Unspecified, // progressBarColorThumbGradientEnd
    val progressBarColorThumbOutline: Color = Color.Unspecified, // progressBarColorThumbOutline

    val selectedColor: Color = Color.Unspecified, // selectedColor
    val selectedColor2: Color = Color.Unspecified, // selectedColor2
    val unselectedColor: Color = Color.Unspecified, // unselectedColor

    val navigationFooterIcon: Color = unselectedColor, // navigationFooterIcon
    val navigationFooterIconAlt: Color = Color.Unspecified, // navigationFooterIconAlt

    val positiveSelColor: Color = Color.Unspecified, // positiveSelColor
    val negativeSelColor: Color = Color.Unspecified, // negativeSelColor
    val neutralSelColor: Color = Color.Unspecified, // neutralSelColor

    val strikethroughColor: Color = Color.Unspecified, // strikethroughColor
    val circleColor: Color = Color.Unspecified, // circleColor

    val mapPoiFillColor: Color = Color.Unspecified, // mapPoiFillColor
    val mapRoomFillColor: Color = Color.Unspecified, // mapRoomFillColor
    val mapRoomBorderColor: Color = Color.Unspecified, // mapRoomBorderColor

    val sanityPieStartColor: Color = Color.Unspecified, // sanityPieStartColor
    val sanityPieEndColor: Color = Color.Unspecified, // sanityPieEndColor
    val sanityHeadBrainColor: Color = primary.color, // sanityHeadBrainColor
    val sanityHeadSkullColor: Color = Color.Unspecified, // sanityHeadSkullColor
    val sanityBorderColor: Color = Color.Unspecified, // sanityBorderColor

    val buttonColor: Color = light_inactive, // buttonColor
    val colorOnButton: Color = textFamily.body, // colorOnButton

    val actionMenuTextColor: Color = Color.Unspecified

    /*val colorPrimary: M3ColorFamily = M3ColorFamily(), // colorPrimary
    val colorOnPrimary: M3ColorFamily = M3ColorFamily(), // colorOnPrimary
    val colorSecondary: M3ColorFamily = M3ColorFamily(), // colorSecondary
    val colorOnSecondary: M3ColorFamily = M3ColorFamily(), // colorOnSecondary

    val textColorPrimary: M3ColorFamily = M3ColorFamily(), // textColorPrimary
    val textColorSecondary: M3ColorFamily = M3ColorFamily(), // textColorSecondary
    val textColorTertiary: M3ColorFamily = M3ColorFamily(), // textColorTertiary
    val textColorBody: M3ColorFamily = M3ColorFamily(), // textColorBody
    val textColorBodyEmphasis: M3ColorFamily = M3ColorFamily(), // textColorBodyEmphasis

    val theme_colorPrimary: M3ColorFamily = M3ColorFamily(), // theme_colorPrimary


    val color_1: M3ColorFamily = M3ColorFamily(), //  color_1
    val color_2: M3ColorFamily = M3ColorFamily(), //  color_2
    val color_3: M3ColorFamily = M3ColorFamily(), //  color_3
    val color_4: M3ColorFamily = M3ColorFamily(), //  color_4
    val color_5: M3ColorFamily = M3ColorFamily(), //  color_5
    val color_6: M3ColorFamily = M3ColorFamily(), //  color_6
    val color_7: M3ColorFamily = M3ColorFamily(), //  color_7
    val color_8: M3ColorFamily = M3ColorFamily(), //  color_8


    val codex1: M3ColorFamily = M3ColorFamily(), // codex1
    val codex2: M3ColorFamily = M3ColorFamily(), // codex2
    val codex3: M3ColorFamily = M3ColorFamily(), // codex3
    val codex4: M3ColorFamily = M3ColorFamily(), // codex4
    val codex5: M3ColorFamily = M3ColorFamily(), // codex5

    val codex2_headerBackground: M3ColorFamily = M3ColorFamily(), // codex2_headerBackground

    val codex1_itemBackground: M3ColorFamily = codex1, // codex1_itemBackground
    val codex2_itemBorder: M3ColorFamily = codex2, // codex2_itemBorder
    val codex3_itemBorder: M3ColorFamily = codex3, // codex3_itemBorder
    val codex2_tierNormal: M3ColorFamily = codex2, // codex2_tierNormal
    val codex4_tierAlt: M3ColorFamily = codex4, // codex4_tierAlt
    val codex3_tierBackground: M3ColorFamily = codex3, // codex3_tierBackground

    val codex3_groupHeaderText: M3ColorFamily = codex3, // codex3_groupHeaderText

    val codex3_throbber: M3ColorFamily = codex3, // codex3_throbber

    val codex1_gh0stBackground: M3ColorFamily = codex1, // codex1_gh0stBackground
    val codex3_gh0stTextNormal: M3ColorFamily = codex3, // codex3_gh0stTextNormal
    val codex4_gh0stTextAlt: M3ColorFamily = codex4, // codex4_gh0stTextAlt
    val codex1_adViewBackground: M3ColorFamily = codex1, // codex1_adViewBackground

    val codex1_cardArrow: M3ColorFamily = codex1, // codex1_cardArrow
    val codex3_cardBorder: M3ColorFamily = codex3, // codex3_cardBorder

    val codex3_navHeaderBackground: M3ColorFamily = codex3, // codex3_navHeaderBackground
    val codex5_navHeaderText: M3ColorFamily = codex5, // codex5_navHeaderText
    val codex2_navBackIcon: M3ColorFamily = codex2, // codex2_navBackIcon

    val codex3_popupHeaderText: M3ColorFamily = codex3, // codex3_popupHeaderText
    val codex3_popupCloseBackground: M3ColorFamily = codex3, // codex3_popupCloseBackground
    val codex2_popupCloseIcon: M3ColorFamily = codex2, // codex2_popupCloseIcon
    val codex3_popupAttrIcons: M3ColorFamily = codex3, // codex3_popupAttrIcons
    val codex3_popupAttrText: M3ColorFamily = codex3, // codex3_popupAttrText

    val codex3_other: M3ColorFamily = codex3, // codex3_other

    val codex5_sel: M3ColorFamily = codex5, // codex5_sel
    val codex4_unsel: M3ColorFamily = codex4, // codex4_unsel

    val codex4_border: M3ColorFamily = codex4, // codex4_border
    val codex4_background: M3ColorFamily = codex4, // codex4_background

    val codex6_gridBackground: M3ColorFamily = M3ColorFamily(), // codex6_gridBackground
    val codex7_gridStroke: M3ColorFamily = M3ColorFamily(), // codex7_gridStroke

    val codex3_buttonBackground: M3ColorFamily = codex3, // codex3_buttonBackground
    val codex5_header: M3ColorFamily = codex5, // codex5_header


    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint
    */
): ExtendedTheme()

@Immutable
data class ExtrasFamily(
    @StringRes val title: Int = title_default,
    @DrawableRes val badge: Int = badge_default,
    val isLightMode: Boolean = false
)

@Immutable
data class TextColorFamily(
    val primary: Color = Color.Unspecified, // textColorPrimary
    val secondary: Color = primary, // textColorSecondary
    val tertiary: Color = secondary, // textColorTertiary
    val body: Color = tertiary, // textColorBody
    val emphasis: Color = body, // textColorBodyEmphasis
)

@Immutable
data class ThemeColorFamily(
    val a: Color = Color.Unspecified, // a
    val b: Color = a, // b
    val c: Color = b, // c
    val d: Color = c, // d
    val e: Color = d, // e
    val f: Color = e, // f
    val g: Color = f, // g
    val h: Color = g, // h
    val i: Color = h, // i
    val j: Color = i, // j
    val k: Color = j, // k
    val l: Color = k, // l
    val m: Color = l, // m
    val n: Color = m, // n
    val o: Color = n, // o
    val p: Color = o, // p
    val q: Color = p, // q
)

@Immutable
data class CoreColorFamily(

    val primary: Color = Color.Unspecified, // theme_colorPrimary

    val color_1: Color = Color.Unspecified, //  color_1
    val color_2: Color = color_1, //  color_2
    val color_3: Color = color_2, //  color_3
    val color_4: Color = color_3, //  color_4
    val color_5: Color = color_4, //  color_5
    val color_6: Color = color_5, //  color_6
    val color_7: Color = color_6, //  color_7
    val color_8: Color = color_7, //  color_8

)

@Immutable
data class CodexColorFamily(
    val codex1: Color = Color.Unspecified, // codex1
    val codex2: Color = codex1, // codex2
    val codex3: Color = codex2, // codex3
    val codex4: Color = codex3, // codex4
    val codex5: Color = codex4, // codex5

    val codex2_headerBackground: Color = Color.Unspecified, // codex2_headerBackground

    val codex1_itemBackground: Color = codex1, // codex1_itemBackground
    val codex2_itemBorder: Color = codex2, // codex2_itemBorder
    val codex3_itemBorder: Color = codex3, // codex3_itemBorder
    val codex2_tierNormal: Color = codex2, // codex2_tierNormal
    val codex4_tierAlt: Color = codex4, // codex4_tierAlt
    val codex3_tierBackground: Color = codex3, // codex3_tierBackground

    val codex3_groupHeaderText: Color = codex3, // codex3_groupHeaderText

    val codex3_throbber: Color = codex3, // codex3_throbber

    val codex1_gh0stBackground: Color = codex1, // codex1_gh0stBackground
    val codex3_gh0stTextNormal: Color = codex3, // codex3_gh0stTextNormal
    val codex4_gh0stTextAlt: Color = codex4, // codex4_gh0stTextAlt
    val codex1_adViewBackground: Color = codex1, // codex1_adViewBackground

    val codex1_cardArrow: Color = codex1, // codex1_cardArrow
    val codex3_cardBorder: Color = codex3, // codex3_cardBorder

    val codex3_navHeaderBackground: Color = codex3, // codex3_navHeaderBackground
    val codex5_navHeaderText: Color = codex5, // codex5_navHeaderText
    val codex2_navBackIcon: Color = codex2, // codex2_navBackIcon

    val codex3_popupHeaderText: Color = codex3, // codex3_popupHeaderText
    val codex3_popupCloseBackground: Color = codex3, // codex3_popupCloseBackground
    val codex2_popupCloseIcon: Color = codex2, // codex2_popupCloseIcon
    val codex3_popupAttrIcons: Color = codex3, // codex3_popupAttrIcons
    val codex3_popupAttrText: Color = codex3, // codex3_popupAttrText

    val codex3_other: Color = codex3, // codex3_other

    val codex5_sel: Color = codex5, // codex5_sel
    val codex4_unsel: Color = codex4, // codex4_unsel

    val codex4_border: Color = codex4, // codex4_border
    val codex4_background: Color = codex4, // codex4_background

    val codex6_gridBackground: Color = Color.Unspecified, // codex6_gridBackground
    val codex7_gridStroke: Color = Color.Unspecified, // codex7_gridStroke

    val codex3_buttonBackground: Color = codex3, // codex3_buttonBackground
    val codex5_header: Color = codex5, // codex5_header
)

@Immutable
data class M3ColorFamily(
    val color: Color = Color.Unspecified,
    val onColor: Color = Color.Unspecified,
    val colorContainer: Color = Color.Unspecified,
    val onColorContainer: Color = Color.Unspecified,

    val variant: Color = Color.Unspecified,
    val onColorVariant: Color = Color.Unspecified,
    val containerVariant: Color = Color.Unspecified,
    val onContainerVariant: Color = Color.Unspecified
)

@Immutable
data class SwitchColorFamily(
    val trackInactive: Color = Color.Unspecified,
    val trackActive: Color = trackInactive,
    val thumbInactive: Color = Color.Unspecified, //switchColorOff
    val thumbActive: Color = thumbInactive, //switchColorOn
)

val LocalPalette = staticCompositionLocalOf { ExtendedPalette() }

val PaletteFromStyle = mapOf(
    Pair(R.style.NonColorblind_Base, ClassicPalette),
    Pair(R.style.Monochromacy, Monochromacy),
    Pair(R.style.Deuteranomaly, Deuteranomaly),
    Pair(R.style.Protanomaly, Protanomaly),
    Pair(R.style.Tritanomaly, Tritanomaly),
    Pair(R.style.Recruit, Recruit),
    Pair(R.style.Investigator, Investigator),
    Pair(R.style.PrivateInvestigator, PrivateInvestigator),
    Pair(R.style.Detective, Detective),
    Pair(R.style.Technician, Technician),
    Pair(R.style.Specialist, Specialist),
    Pair(R.style.Analyst, Analyst),
    Pair(R.style.Agent, Agent),
    Pair(R.style.Operator, Operator),
    Pair(R.style.Commissioner, Commissioner),
    Pair(R.style.Easter, Easter),
    Pair(R.style.Halloween23, Halloween23),
    Pair(R.style.Holiday22, Holiday22),
    Pair(R.style.Holiday23, Holiday23),
    Pair(R.style.Discord, Discord),
    Pair(R.style.ContentCreator, ContentCreator),
    Pair(R.style.Developer, Developer),
    Pair(R.style.Translator, Translator),
    Pair(R.style.Winner, Winner),
    Pair(R.style.Artist, Artist),
    Pair(R.style.Whiteboard, Whiteboard),
    Pair(R.style.StrategemHero, StrategemHero),
)

val LocalPalettesMap = mapOf(
    Pair("CzjtxSbXRwIpX8SYR0ttngAND", ClassicPalette),
    Pair("ey6VbQN5tx0MgR8tw4iJq3J8L", Monochromacy),
    Pair("9Ec5cQIB0Jb73gbQ3a9R5kScX", Deuteranomaly),
    Pair("44uBEX9ID9131TMG3yH7T2P1Q", Protanomaly),
    Pair("28yi3LJWYMbA7v87RMAEznISy", Tritanomaly),
    Pair("9QWud4Mj5C4jYp1hOA91b6G0L", Recruit),
    Pair("1UdIWr97JGtOoRszQHpBYfK5U", Investigator),
    Pair("1oF0ZF78LqA64zRyn8ozgMqLu", PrivateInvestigator),
    Pair("5T2mB6W1370DEc3cnYN2bI0gT", Detective),
    Pair("9t44qIYH7UU3EPwvgXEO6309E", Technician),
    Pair("9Uo9I6AmpW2XbhHDIxCP4ppds", Specialist),
    Pair("E2m4fr5Vp6dv07JnWE5Q3T08D", Analyst),
    Pair("6BL0zB7jGGye94j45hq3wtwVq", Agent),
    Pair("8No78zJ6oUSBLS818jH10Seni", Operator),
    Pair("4p77qstew9oX20l4X5O0J010k", Commissioner),
    Pair("B6xTXIXttQUqW02Skd094BY15", Easter),
    Pair("EOkBD2aJZhOh9V821o20GFLXK", Halloween23),
    Pair("7RF705OjW3uo39zl0aTktINHd", Holiday22),
    Pair("0XrEhnlpA9mD9dNxrzCbNfkRe", Holiday23),
    Pair("Ex00XEBLXbh65Nk9SdYMrMfsP", Discord),
    Pair("2pe8HDVVWRfxhPUw4Xf0rhgwb", ContentCreator),
    Pair("56iV5cnR3lRJ59uZF1mTtEz9X", Developer),
    Pair("e6nDE683SE7Wg9dRPJeN61CD6", Translator),
    Pair("8xkSw89x50Zwgr4tGd6z5X0a7", Winner),
    Pair("88kEPt48wGs0ZxR4n7gtM3OTe", Artist),
    Pair("tempWhiteboard", Whiteboard),
    Pair("tempStratHero", StrategemHero),
)

private val LocalPalettesList = LocalPalettesMap.toList()

val LocalDefaultPalette = SimpleUniquePalette(
    LocalPalettesList[0].first,
    LocalPalettesList[0].second
)


data class SimpleUniquePalette(val uuid: String, val palette: ExtendedPalette)