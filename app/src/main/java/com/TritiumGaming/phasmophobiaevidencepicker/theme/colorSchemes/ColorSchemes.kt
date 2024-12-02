package com.TritiumGaming.phasmophobiaevidencepicker.theme.colorSchemes

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.TritiumGaming.phasmophobiaevidencepicker.theme.black
import com.TritiumGaming.phasmophobiaevidencepicker.theme.black_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.black_M50
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M125
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M150
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M175
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M175_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M200
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M200_A58
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M250
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M650
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M750
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M850
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M90
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M900
import com.TritiumGaming.phasmophobiaevidencepicker.theme.blue_M950
import com.TritiumGaming.phasmophobiaevidencepicker.theme.create
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M150
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M150_A10
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M150_A58
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M150_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M25
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M525
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M550
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M650
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M825
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M850
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M875
import com.TritiumGaming.phasmophobiaevidencepicker.theme.green_M950
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_background
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_error
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_errorContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_inverseOnSurface
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_inversePrimary
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_inverseSurface
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onBackground
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onError
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onErrorContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onPrimary
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onPrimaryContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onSecondary
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onSecondaryContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onSurface
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onSurfaceVariant
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onTertiary
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_onTertiaryContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_outline
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_primary
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_primaryContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_secondary
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_secondaryContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_surface
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_surfaceTint
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_surfaceVariant
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_tertiary
import com.TritiumGaming.phasmophobiaevidencepicker.theme.md_theme_dark_tertiaryContainer
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M05
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M10
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M150
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M20
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M250
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M300
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M300_A58
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M300_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M400
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M50
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M500
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M50_A40
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M510
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M525
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M85
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M900
import com.TritiumGaming.phasmophobiaevidencepicker.theme.orange_M950
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_A10
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M150
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M250
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M300
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M300_A58
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M300_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M450
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M475
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M500
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M50_A40
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M525
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M550
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M825
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M850
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M875
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M900
import com.TritiumGaming.phasmophobiaevidencepicker.theme.purple_M950
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_A40
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M150
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M225
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M250
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M300
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M300_A58
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M300_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M350
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M375
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M500
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M850
import com.TritiumGaming.phasmophobiaevidencepicker.theme.red_M950
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_A58
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_A75
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M10
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M100
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M20
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M200
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M25
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M300
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M400
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M50
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M500
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M600
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M750
import com.TritiumGaming.phasmophobiaevidencepicker.theme.white_M850
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow_A10
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow_A40
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow_M100
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow_M25
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow_M300
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow_M500
import com.TritiumGaming.phasmophobiaevidencepicker.theme.yellow_M50_A10

val DefaultLightColors = lightColorScheme(/*
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
    surfaceTint = md_theme_light_surfaceTint*/
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

private val ExtendedDefaultLight = ExtendedColorScheme()
// private val ExtendedDefaultDark = ExtendedColorScheme()

private val PET_Base = ExtendedDefaultLight.copy(
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor = ColorFamily(
        color = black
    ),
    backgroundColorOnBackground = ColorFamily(
        color = create(0xFF191616)
    ),
    windowBackgroundColor = ColorFamily(
        color = black
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    navigationFooterIconAlt = ColorFamily(
        color = white_M200
    ),
    theme_colorPrimary = ColorFamily(
        color = red_M350
    ),
    theme_coreColor_1 = ColorFamily(
        color = white
    ),
    theme_coreColor_2 = ColorFamily(
        color = white
    ),
    theme_coreColor_3 = ColorFamily(
        color = white
    ),
    theme_coreColor_4 = ColorFamily(
        color = white
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = white
    ),
    theme_coreColor_7 = ColorFamily(
        color = white
    ),
    theme_coreColor_8 = ColorFamily(
        color = white
    ),
    backgroundColor_mapviewOverlay = ColorFamily(
        color = black_A75
    ),
    splashTextColor = ColorFamily(
        color = red_M350
    ),
    textColorPrimary = ColorFamily(
        color = red_M250
    ),
    textColorSecondary = ColorFamily(
        color = red_M300
    ),
    textColorTertiary = ColorFamily(
        color = red_M350
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = red_M250
    ),
    light_active = ColorFamily(
        color = red
    ),
    light_inactive = ColorFamily(
        color = red_A40
    ),
    light_off = ColorFamily(
        color = white_M600
    ),
    sanityPieStartColor = ColorFamily(
        color = white
    ),
    sanityPieEndColor = ColorFamily(
        color = red
    ),
    sanityHeadSkullColor = ColorFamily(
        color = black
    ),
    sanityBorderColor = ColorFamily(
        color = white
    ),
    progressBarColorStart = ColorFamily(
        color = red_M350
    ),
    progressBarColorEnd = ColorFamily(
        color = red_M350
    ),
    progressBarColorThumbGradientStart = ColorFamily(
        color = white
    ),
    progressBarColorThumbGradientEnd = ColorFamily(
        color = black
    ),
    progressBarColorThumbOutline = ColorFamily(
        color = create(0xFFCCCCCC)
    ),
    switchColorOn = ColorFamily(
        color = red_M300
    ),
    switchColorOff = ColorFamily(
        color = white_M200
    ),
    inboxNotification = ColorFamily(
        color = red_M300
    ),
    selectedColor = ColorFamily(
        color = red
    ),
    selectedColor2 = ColorFamily(
        color = red_A40
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    strikethroughColor = ColorFamily(
        color = red_M300
    ),
    circleColor = ColorFamily(
        color = green
    ),
    positiveSelColor = ColorFamily(
        color = green
    ),
    neutralSelColor = ColorFamily(
        color = white_M100
    ),
    negativeSelColor = ColorFamily(
        color = red_M350
    ),
    mapPoiFillColor = ColorFamily(
        color = red_M300_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = red_M300_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = white
    ),
    codex1 = ColorFamily(
        color = create(0xFF161C1C)
    ),
    codex2 = ColorFamily(
        color = create(0xFF151B1B)
    ),
    codex3 = ColorFamily(
        color = create(0xFF99AEB3)
    ),
    codex4 = ColorFamily(
        color = create(0xFFFFB43D)
    ),
    codex5 = ColorFamily(
        color = create(0xFF2D3635)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40161C1C)
    ),
    codex7_gridStroke = ColorFamily(
        color = create(0x40FFFFFF)
    ),
)

private val Colorblind = PET_Base.copy()

val Non_Colorblind = Colorblind.copy()

val Monochromacy = Colorblind.copy(
    textColorPrimary = ColorFamily(
        color = white
    ),
    textColorSecondary = ColorFamily(
        color = white
    ),
    textColorTertiary = ColorFamily(
        color = white
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = white
    ),
    light_active = ColorFamily(
        color = white
    ),
    light_inactive = ColorFamily(
        color = white_M400
    ),
    sanityPieStartColor = ColorFamily(
        color = white
    ),
    sanityPieEndColor = ColorFamily(
        color = white_M500
    ),
    progressBarColorStart = ColorFamily(
        color = white_M500
    ),
    progressBarColorEnd = ColorFamily(
        color = white_M300
    ),
    switchColorOn = ColorFamily(
        color = white_M100
    ),
    inboxNotification = ColorFamily(
        color = white_M600
    ),
    strikethroughColor = ColorFamily(
        color = white_M100
    ),
    circleColor = ColorFamily(
        color = white
    ),
    selectedColor = ColorFamily(
        color = white_M100
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = white
    ),
    neutralSelColor = ColorFamily(
        color = white_M400
    ),
    negativeSelColor = ColorFamily(
        color = white_M600
    ),
    mapPoiFillColor = ColorFamily(
        color = white_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = white_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_M50_A10
    ),
    navigationFooterIconAlt = ColorFamily(
        color = white_M200
    ),
    splashTextColor = ColorFamily(
        color = white_M200
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = white
    ),
    theme_colorPrimary = ColorFamily(
        white_M100
    )
)

val Deuteranomaly = Colorblind.copy(
    textColorPrimary = ColorFamily(
        color = blue_M175
    ),
    textColorSecondary = ColorFamily(
        color = blue_M200
    ),
    textColorTertiary = ColorFamily(
        color = blue_M250
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = blue_M175
    ),
    light_active = ColorFamily(
        color = yellow
    ),
    light_inactive = ColorFamily(
        color = yellow_A40
    ),
    sanityPieStartColor = ColorFamily(
        color = white
    ),
    sanityPieEndColor = ColorFamily(
        color = yellow
    ),
    progressBarColorStart = ColorFamily(
        color = yellow_M500
    ),
    progressBarColorEnd = ColorFamily(
        color = yellow_M300
    ),
    switchColorOn = ColorFamily(
        color = yellow_M100
    ),
    inboxNotification = ColorFamily(
        color = blue_M650
    ),
    strikethroughColor = ColorFamily(
        color = blue_M175
    ),
    circleColor = ColorFamily(
        color = yellow
    ),
    selectedColor = ColorFamily(
        color = yellow
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = yellow
    ),
    neutralSelColor = ColorFamily(
        color = white_M400
    ),
    negativeSelColor = ColorFamily(
        color = blue_M650
    ),
    mapPoiFillColor = ColorFamily(
        color = blue_M175_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = blue_M200_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),
    navigationFooterIconAlt = ColorFamily(
        color = yellow_M100
    ),
    splashTextColor = ColorFamily(
        color = white_M200
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = white
    ),
    theme_colorPrimary = ColorFamily(
        blue_M175
    )
)

val Protanomaly = Colorblind.copy(
    textColorPrimary = ColorFamily(
        color = blue_M175
    ),
    textColorSecondary = ColorFamily(
        color = blue_M200
    ),
    textColorTertiary = ColorFamily(
        color = blue_M250
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = blue_M175
    ),
    light_active = ColorFamily(
        color = yellow
    ),
    light_inactive = ColorFamily(
        color = yellow_A40
    ),
    sanityPieStartColor = ColorFamily(
        color = white
    ),
    sanityPieEndColor = ColorFamily(
        color = yellow
    ),
    progressBarColorStart = ColorFamily(
        color = yellow_M500
    ),
    progressBarColorEnd = ColorFamily(
        color = yellow_M300
    ),
    switchColorOn = ColorFamily(
        color = yellow_M100
    ),
    inboxNotification = ColorFamily(
        color = blue_M650
    ),
    strikethroughColor = ColorFamily(
        color = blue_M175
    ),
    circleColor = ColorFamily(
        color = yellow
    ),
    selectedColor = ColorFamily(
        color = yellow
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = yellow
    ),
    neutralSelColor = ColorFamily(
        color = white_M400
    ),
    negativeSelColor = ColorFamily(
        color = blue_M650
    ),
    mapPoiFillColor = ColorFamily(
        color = blue_M175_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = blue_M200_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),
    navigationFooterIconAlt = ColorFamily(
        color = yellow_M100
    ),
    splashTextColor = ColorFamily(
        color = yellow_M100
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = white
    ),
    theme_colorPrimary = ColorFamily(
        blue_M175
    )
)

val Tritanomaly = Colorblind.copy(
    sanityPieStartColor = ColorFamily(
        color = white
    ),
    sanityPieEndColor = ColorFamily(
        color = red
    ),
    progressBarColorStart = ColorFamily(
        color = red_M500
    ),
    progressBarColorEnd = ColorFamily(
        color = red_M300
    ),
    switchColorOn = ColorFamily(
        color = red
    ),
    circleColor = ColorFamily(
        color = green
    ),
    neutralSelColor = ColorFamily(
        color = white
    ),
    mapPoiFillColor = ColorFamily(
        color = red_A75
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = white
    ),
    theme_colorPrimary = ColorFamily(
        red
    )
)

private val Skin = Non_Colorblind.copy()

val Whiteboard = Skin.copy(
    /*android:windowLightStatusBar = true),

    android:colorBackground = #383839),
    android:statusBarColor = ColorFamily(
        color = colorOnSecondary),
    android:navigationBarColor = ColorFamily(
        color = white_M50),*/
    foregroundColor = ColorFamily(
        color = white_M50
    ),
    middlegroundColor = ColorFamily(
        color = white_M50
    ),
    backgroundColor = ColorFamily(
        color = white_M50
    ),
    backgroundColorOnBackground = ColorFamily(
        color = white_M50
    ),
    statusBarColor = ColorFamily(
        color = white_M50
    ),
    navigationBarColor = ColorFamily(
        color = white_M50
    ),
    windowBackgroundColor = ColorFamily(
        color = white_M50
    ),
    windowStrokeColor = ColorFamily(
        color = create(0x383839)
    ),
    colorPrimary = ColorFamily(
        color = white_M50
    ),
    colorPrimaryVariant = ColorFamily(
        color = white_M50
    ),
    colorOnPrimary = ColorFamily(
        color = white_M50
    ),
    colorSecondary = ColorFamily(
        color = white_M50
    ),
    colorSecondaryVariant = ColorFamily(
        color = white_M50
    ),
    colorOnSecondary = ColorFamily(
        color = white_M50
    ),

    navigationFooterIcon = ColorFamily(
        color = white_M500
    ),
    navigationFooterIconAlt = ColorFamily(
        color = red_M300
    ),

    theme_colorPrimary = ColorFamily(
        color = black
    ),
    theme_coreColor_1 = ColorFamily(
        color = black
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0x383839)
    ),
    theme_coreColor_3 = ColorFamily(
        color = white_M500
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0xafb7be)
    ),
    theme_coreColor_5 = ColorFamily(
        color = white_M50
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xf55656)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x1cb981)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x394494)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xafb7be)
    ),

    splashTextColor = ColorFamily(
        color = create(0x383839)
    ),

    textColorPrimary = ColorFamily(
        color = create(0x383839)
    ),
    textColorSecondary = ColorFamily(
        color = create(0x383839)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x383839)
    ),

    textColorBody = ColorFamily(
        color = create(0x383839)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xf55656)
    ),

    light_active = ColorFamily(
        color = create(0x383839)
    ),
    light_inactive = ColorFamily(
        color = create(0x383839)
    ),
    light_off = ColorFamily(
        color = create(0xafb7be)
    ),

    sanityPieStartColor = ColorFamily(
        color = white_M50
    ),
    sanityPieEndColor = ColorFamily(
        color = create(0xf55656)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0x383839)
    ),
    progressBarColorEnd = ColorFamily(
        color = create(0x383839)
    ),
    sanityHeadBrainColor = ColorFamily(
        color = black
    ),
    sanityHeadSkullColor = ColorFamily(
        color = create(0x383839)
    ),
    sanityBorderColor = ColorFamily(
        color = black),

    switchColorOn = ColorFamily(
        color = create(0xf55656)
    ),
    switchColorOff = ColorFamily(
        color = create(0xafb7be)
    ),

    inboxNotification = ColorFamily(
        color = create(0xf55656)
    ),

    selectedColor = ColorFamily(
        color = create(0xf55656)
    ),
    selectedColor2 = ColorFamily(
        color = create(0xf55656)
    ),
    unselectedColor = ColorFamily(
        color = create(0xafb7be)
    ),

    strikethroughColor = ColorFamily(
        color = create(0xf55656)
    ),
    circleColor = ColorFamily(
        color = create(0x383839)
    ),

    positiveSelColor = ColorFamily(
        color = create(0xf55656)
    ),
    neutralSelColor = ColorFamily(
        color = create(0x383839)
    ),
    negativeSelColor = ColorFamily(
        color = create(0xafb7be)
    ),

    mapPoiFillColor = ColorFamily(
        color = red_M300_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = red_M300_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = red_A75
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = white_M50
    ),

    codex1 = ColorFamily(
        color = black_A75
    ),
    codex3 = ColorFamily(
        color = white_M50
    ),
    codex2 = ColorFamily(
        color = create(0x383839)
    ),
    codex4 = ColorFamily(
        color = create(0xf55656)
    ),
    codex5 = ColorFamily(
        color = black_A75
    ),
    codex6_gridBackground = ColorFamily(
        color = white_M50
    ),

    buttonColor = ColorFamily(
        color = create(0x383839)
    ),
    colorOnButton = ColorFamily(
        color = create(0x383839)
    ),

    actionMenuTextColor = ColorFamily(
        color = create(0x383839)
    ),
    /*
    android:textColor = ColorFamily(
        color = textColorPrimary),
    android:color = ColorFamily(
        color = textColorPrimary),
    android:titleTextColor = ColorFamily(
        color = textColorPrimary),
    android:subtitleTextColor = ColorFamily(
        color = textColorBody),
    */
)

private val Prestige = Skin.copy()
private val Event = Skin.copy()
private val Community = Skin.copy()
private val Special = Skin.copy()

val Recruit = Prestige.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = white_M50
    ),
    c = ColorFamily(
        color = orange_M150
    ),
    d = ColorFamily(
        color = create(0xB2B1B1)
    ),
    e = ColorFamily(
        color = create(0x818180)
    ),
    f = ColorFamily(
        color = create(0x515151)
    ),
    g = ColorFamily(
        color = create(0x252423)
    ),
    h = ColorFamily(
        color = orange_M950
    ),

    theme_colorPrimary = ColorFamily(
        color = orange_M150
    ),
    theme_coreColor_1 = ColorFamily(
        color = orange_M150
    ),
    theme_coreColor_2 = ColorFamily(
        color = orange_M300
    ),
    theme_coreColor_3 = ColorFamily(
        color = orange_M900
    ),
    theme_coreColor_4 = ColorFamily(
        color = orange_M950
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = white_M100
    ),
    theme_coreColor_7 = ColorFamily(
        color = white_M600
    ),
    theme_coreColor_8 = ColorFamily(
        color = yellow
    ),

    /*
    colorBackground = ColorFamily(
        color = orange_M950
    ),
    statusBarColor = ColorFamily(
        color = orange_M950
    ),
    navigationBarColor = ColorFamily(
        color = orange_M950
    ),
    */

    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = create(0x818180)
    ),
    backgroundColor = ColorFamily(
        color = orange_M950
    ),
    backgroundColorOnBackground = ColorFamily(
        color = orange_M900
    ),
    statusBarColor = ColorFamily(
        color = orange_M950
    ),
    navigationBarColor = ColorFamily(
        color = orange_M950
    ),
    windowBackgroundColor = ColorFamily(
        color = orange_M950
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = orange_M950
    ),
    colorPrimaryVariant = ColorFamily(
        color = orange_M950
    ),
    colorOnPrimary = ColorFamily(
        color = orange_M950
    ),
    colorSecondary = ColorFamily(
        color = orange_M950
    ),
    colorSecondaryVariant = ColorFamily(
        color = orange_M950
    ),
    colorOnSecondary = ColorFamily(
        color = orange_M950
    ),

    textColorPrimary = ColorFamily(
        color = orange_M250
    ),
    textColorSecondary = ColorFamily(
        color = orange_M300
    ),
    textColorTertiary = ColorFamily(
        color = orange_M400
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = orange_M250
    ),
    light_active = ColorFamily(
        color = orange_M150
    ),
    light_inactive = ColorFamily(
        color = orange_M50_A40
    ),
    light_off = ColorFamily(
        color = white_M600
    ),
    switchColorOn = ColorFamily(
        color = orange_M300
    ),
    strikethroughColor = ColorFamily(
        color = orange_M300
    ),
    circleColor = ColorFamily(
        color = orange
    ),
    selectedColor = ColorFamily(
        color = orange_M150
    ),
    selectedColor2 = ColorFamily(
        color = orange_M150
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = yellow
    ),
    neutralSelColor = ColorFamily(
        color = white_M100
    ),
    negativeSelColor = ColorFamily(
        color = orange_M300
    ),
    mapPoiFillColor = ColorFamily(
        color = orange_M300_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = orange_M300_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF090500)
    ),

    progressBarColorStart = ColorFamily(
        color = orange_M500
    ),
    progressBarColorEnd = ColorFamily(
        color = orange_M300
    ),
    sanityPieStartColor = ColorFamily(
        color = white
    ),
    sanityPieEndColor = ColorFamily(
        color = orange_M150
    ),

    splashTextColor = ColorFamily(
        color = orange_M150
    ),

    codex1 = ColorFamily(
        color = create(0x232C2C)
    ),
    codex3 = ColorFamily(
        color = orange_M150
    ),
    codex2 = ColorFamily(
        color = create(0x252423)
    ),
    codex4 = ColorFamily(
        color = orange_M150
    ),
    codex5 = ColorFamily(
        color = create(0x252423)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40232C2C)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x818180)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = orange_M150
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = white_M50
    ),
    inboxNotification = ColorFamily(
        color = orange_M150
    )
)

val Investigator = Prestige.copy(
    a = ColorFamily(
        color = white_M10
    ),
    b = ColorFamily(
        color = white_M50
    ),
    c = ColorFamily(
        color = white_M300
    ),
    d = ColorFamily(
        color = purple_M75
    ),
    e = ColorFamily(
        color = purple_M250
    ),
    f = ColorFamily(
        color = purple_M525
    ),
    g = ColorFamily(
        color = purple_M950
    ),
    h = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = purple_M75
    ),
    theme_coreColor_1 = ColorFamily(
        color = purple_M75
    ),
    theme_coreColor_2 = ColorFamily(
        color = purple_M75
    ),
    theme_coreColor_3 = ColorFamily(
        color = purple_M525
    ),
    theme_coreColor_4 = ColorFamily(
        color = purple_M950
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = white_M300
    ),
    theme_coreColor_7 = ColorFamily(
        color = white_M600
    ),
    theme_coreColor_8 = ColorFamily(
        color = black
    ),

    /*
    colorBackground = ColorFamily(
        color = orange_M950
    ),
    statusBarColor = ColorFamily(
        color = orange_M950
    ),
    navigationBarColor = ColorFamily(
        color = orange_M950
    ),
    */

    foregroundColor = ColorFamily(
        color = white_M10
    ),
    middlegroundColor = ColorFamily(
        color = purple_M250
    ),
    backgroundColor = ColorFamily(
        color = purple_M950
    ),
    backgroundColorOnBackground = ColorFamily(
        color = purple_M900
    ),
    statusBarColor = ColorFamily(
        color = purple_M950
    ),
    navigationBarColor = ColorFamily(
        color = purple_M950
    ),
    windowBackgroundColor = ColorFamily(
        color = purple_M950
    ),
    windowStrokeColor = ColorFamily(
        color = white_M10
    ),

    colorPrimary = ColorFamily(
        color = orange_M950
    ),
    colorPrimaryVariant = ColorFamily(
        color = orange_M950
    ),
    colorOnPrimary = ColorFamily(
        color = orange_M950
    ),
    colorSecondary = ColorFamily(
        color = orange_M950
    ),
    colorSecondaryVariant = ColorFamily(
        color = orange_M950
    ),
    colorOnSecondary = ColorFamily(
        color = orange_M950
    ),

    textColorPrimary = ColorFamily(
        color = purple_M75
    ),
    textColorSecondary = ColorFamily(
        color = purple_M250
    ),
    textColorTertiary = ColorFamily(
        color = purple_M525
    ),
    textColorBody = ColorFamily(
        color = white_M10
    ),
    textColorBodyEmphasis = ColorFamily(
        color = purple_M250
    ),
    light_active = ColorFamily(
        color = purple_M75
    ),
    light_inactive = ColorFamily(
        color = create(0x6689114F)
    ),
    light_off = ColorFamily(
        color = white_M600
    ),
    switchColorOn = ColorFamily(
        color = purple_M75
    ),
    strikethroughColor = ColorFamily(
        color = purple_M525
    ),
    circleColor = ColorFamily(
        color = purple_M75
    ),
    selectedColor = ColorFamily(
        color = purple_M75
    ),
    selectedColor2 = ColorFamily(
        color = purple_M75
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = purple_M75
    ),
    neutralSelColor = ColorFamily(
        color = white_M50
    ),
    negativeSelColor = ColorFamily(
        color = purple_M525
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBFF11284)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94F11284)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1AF11284)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF0B000B)
    ),

    progressBarColorStart = ColorFamily(
        color = white_M10
    ),
    progressBarColorEnd = ColorFamily(
        color = purple_M75
    ),
    sanityPieStartColor = ColorFamily(
        color = white_M10
    ),
    sanityPieEndColor = ColorFamily(
        color = purple_M75
    ),

    splashTextColor = ColorFamily(
        color = purple_M75
    ),

    codex1 = ColorFamily(
        color = create(0x390721)
    ),
    codex2 = ColorFamily(
        color = create(0x200413)
    ),
    codex3 = ColorFamily(
        color = white_M300
    ),
    codex4 = ColorFamily(
        color = purple_M75
    ),
    codex5 = ColorFamily(
        color = create(0x3C3237)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40390721)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x4DD71472)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = purple_M250
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = white_M300
    ),
    inboxNotification = ColorFamily(
        color = purple_M75
    )
)

val PrivateInvestigator = Prestige.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = orange_M85
    ),
    c = ColorFamily(
        color = orange_M20
    ),
    d = ColorFamily(
        color = orange_M10
    ),
    e = ColorFamily(
        color = orange_M510
    ),
    f = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = orange_M10
    ),
    theme_coreColor_1 = ColorFamily(
        color = orange_M20
    ),
    theme_coreColor_2 = ColorFamily(
        color = orange_M10
    ),
    theme_coreColor_3 = ColorFamily(
        color = orange
    ),
    theme_coreColor_4 = ColorFamily(
        color = orange_M510
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = orange_M85
    ),
    theme_coreColor_7 = ColorFamily(
        color = white_M600
    ),
    theme_coreColor_8 = ColorFamily(
        color = black
    ),

    /*
    colorBackground = ColorFamily(
        color = black
    ),
    statusBarColor = ColorFamily(
        color = black
    ),
    navigationBarColor = ColorFamily(
        color = black
    ),
    */

    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = orange_M510
    ),
    backgroundColor = ColorFamily(
        color = black
    ),
    backgroundColorOnBackground = ColorFamily(
        color = black_M50
    ),
    statusBarColor = ColorFamily(
        color = black
    ),
    navigationBarColor = ColorFamily(
        color = black
    ),
    windowBackgroundColor = ColorFamily(
        color = black
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),

    colorPrimary = ColorFamily(
        color = black
    ),
    colorPrimaryVariant = ColorFamily(
        color = black
    ),
    colorOnPrimary = ColorFamily(
        color = black
    ),
    colorSecondary = ColorFamily(
        color = black
    ),
    colorSecondaryVariant = ColorFamily(
        color = black
    ),
    colorOnSecondary = ColorFamily(
        color = black
    ),

    textColorPrimary = ColorFamily(
        color = orange_M20
    ),
    textColorSecondary = ColorFamily(
        color = orange_M10
    ),
    textColorTertiary = ColorFamily(
        color = orange_M510
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = orange_M20
    ),
    light_active = ColorFamily(
        color = orange
    ),
    light_inactive = ColorFamily(
        color = orange_M50_A40
    ),
    light_off = ColorFamily(
        color = orange_M510
    ),
    switchColorOn = ColorFamily(
        color = orange_M10
    ),
    strikethroughColor = ColorFamily(
        color = orange_M510
    ),
    circleColor = ColorFamily(
        color = orange
    ),
    selectedColor = ColorFamily(
        color = orange_M10
    ),
    selectedColor2 = ColorFamily(
        color = orange_M50_A40
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = orange_M10
    ),
    neutralSelColor = ColorFamily(
        color = orange_M85
    ),
    negativeSelColor = ColorFamily(
        color = orange_M510
    ),
    mapPoiFillColor = ColorFamily(
        color = orange_M300_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = orange_M300_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = black_A75
    ),

    progressBarColorStart = ColorFamily(
        color = orange_M85
    ),
    progressBarColorEnd = ColorFamily(
        color = orange_M10
    ),
    sanityPieStartColor = ColorFamily(
        color = orange_M85
    ),
    sanityPieEndColor = ColorFamily(
        color = orange_M10
    ),

    splashTextColor = ColorFamily(
        color = orange_M10
    ),

    codex1 = ColorFamily(
        color = create(0xFF150F08)
    ),
    codex2 = ColorFamily(
        color = create(0xFF332312)
    ),
    codex3 = ColorFamily(
        color = orange_M10
    ),
    codex4 = ColorFamily(
        color = orange_M10
    ),
    codex5 = ColorFamily(
        color = create(0xFF3A3631)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x403E2A17)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x8078522B)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = orange_M10
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = orange_M20
    ),
    inboxNotification = ColorFamily(
        color = orange_M10
    )
)

val Detective = Prestige.copy(
    a = ColorFamily(
        color = white_M20
    ),
    b = ColorFamily(
        color = white_M750
    ),
    c = ColorFamily(
        color = white_M850
    ),
    f = ColorFamily(
        color = green_M25
    ),
    g = ColorFamily(
        color = green_M525
    ),
    h = ColorFamily(
        color = green_M825
    ),
    i = ColorFamily(
        color = green_M875
    ),
    j = ColorFamily(
        color = green_M950
    ),
    k = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = green_M25
    ),
    theme_coreColor_1 = ColorFamily(
        color = white_M20
    ),
    theme_coreColor_2 = ColorFamily(
        color = green_M25
    ),
    theme_coreColor_3 = ColorFamily(
        color = green_M525
    ),
    theme_coreColor_4 = ColorFamily(
        color = green_M950
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = white_M750
    ),
    theme_coreColor_7 = ColorFamily(
        color = green_M825
    ),
    theme_coreColor_8 = ColorFamily(
        color = black
    ),

    /*
    colorBackground = ColorFamily(
        color = 
    ),
    statusBarColor = ColorFamily(
        color = 
    ),
    navigationBarColor = ColorFamily(
        color = 
    ),
    */

    foregroundColor = ColorFamily(
        color = white_M20
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor = ColorFamily(
        color = green_M875
    ),
    backgroundColorOnBackground = ColorFamily(
        color = green_M825
    ),
    statusBarColor = ColorFamily(
        color = green_M875
    ),
    navigationBarColor = ColorFamily(
        color = green_M875
    ),
    windowBackgroundColor = ColorFamily(
        color = green_M875
    ),
    windowStrokeColor = ColorFamily(
        color = white_M20
    ),

    colorPrimary = ColorFamily(
        color = green_M875
    ),
    colorPrimaryVariant = ColorFamily(
        color = green_M875
    ),
    colorOnPrimary = ColorFamily(
        color = green_M875
    ),
    colorSecondary = ColorFamily(
        color = green_M875
    ),
    colorSecondaryVariant = ColorFamily(
        color = green_M875
    ),
    colorOnSecondary = ColorFamily(
        color = green_M875
    ),

    textColorPrimary = ColorFamily(
        color = green_M25
    ),
    textColorSecondary = ColorFamily(
        color = green_M525
    ),
    textColorTertiary = ColorFamily(
        color = create(0xFF44BF8C)
    ),
    textColorBody = ColorFamily(
        color = white_M20
    ),
    textColorBodyEmphasis = ColorFamily(
        color = green_M525
    ),
    light_active = ColorFamily(
        color = green_M25
    ),
    light_inactive = ColorFamily(
        color = green_M525
    ),
    light_off = ColorFamily(
        color = create(0xFF2E332F)
    ),
    switchColorOn = ColorFamily(
        color = green_M25
    ),
    strikethroughColor = ColorFamily(
        color = white_M600
    ),
    circleColor = ColorFamily(
        color = green_M25
    ),
    selectedColor = ColorFamily(
        color = green_M25
    ),
    selectedColor2 = ColorFamily(
        color = green_M525
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = green_M25
    ),
    neutralSelColor = ColorFamily(
        color = white_M600
    ),
    negativeSelColor = ColorFamily(
        color = white_M300
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBF1A9863)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x941A9863)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1A1A9863)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF0F100F)
    ),

    progressBarColorStart = ColorFamily(
        color = white_M20
    ),
    progressBarColorEnd = ColorFamily(
        color = green_M25
    ),
    sanityPieStartColor = ColorFamily(
        color = white_M20
    ),
    sanityPieEndColor = ColorFamily(
        color = green_M25
    ),

    splashTextColor = ColorFamily(
        color = green_M25
    ),

    codex1 = ColorFamily(
        color = create(0x1E221F)
    ),
    codex2 = ColorFamily(
        color = green_M825
    ),
    codex3 = ColorFamily(
        color = create(0xFFB0E3CE)
    ),
    codex4 = ColorFamily(
        color = green_M25
    ),
    codex5 = ColorFamily(
        color = white_M850
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x401E221F)
    ),

    navigationFooterIcon = ColorFamily(
        color = white_M600
    ),
    navigationFooterIconAlt = ColorFamily(
        color = green_M25
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = white_M20
    ),
    inboxNotification = ColorFamily(
        color = green_M25
    )
)

val Technician = Prestige.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xFFE0E0E0)
    ),
    c = ColorFamily(
        color = create(0xFFABABAB)
    ),
    d = ColorFamily(
        color = red_M375
    ),
    e = ColorFamily(
        color = create(0xFF91100C)
    ),
    f = ColorFamily(
        color = create(0xFF5E0D0B)
    ),
    g = ColorFamily(
        color = create(0xFF280A09)
    ),
    h = ColorFamily(
        color = create(0xFF102508)
    ),
    i = ColorFamily(
        color = create(0xFF081204)
    ),
    j = ColorFamily(
        color = create(0xFF050C04)
    ),
    k = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = red_M375
    ),
    theme_coreColor_1 = ColorFamily(
        color = red_M375
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0x91100C)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0x5E0D0B)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x050C04)
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xE0E0E0)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0xABABAB)
    ),
    theme_coreColor_8 = ColorFamily(
        color = black
    ),

    /*
    colorBackground = ColorFamily(
        color = create(0xFF050C04)
    ),
    statusBarColor = ColorFamily(
        color = create(0xFf050C04)
    ),
    navigationBarColor = ColorFamily(
        color = create(0xFF050C04)
    ),
    */

    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor = ColorFamily(
        color = create(0x050C04)
    ),
    backgroundColorOnBackground = ColorFamily(
        color = create(0x081204)
    ),
    statusBarColor = ColorFamily(
        color = create(0x050C04)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x081204)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x050C04)
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),

    colorPrimary = ColorFamily(
        color = create(0x050C04)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x050C04)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x050C04)
    ),
    colorSecondary = ColorFamily(
        color = create(0x050C04)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x050C04)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x050C04)
    ),

    textColorPrimary = ColorFamily(
        color = red_M375
    ),
    textColorSecondary = ColorFamily(
        color = create(0x91100C)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x8E3431)
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xABABAB)
    ),
    light_active = ColorFamily(
        color = red_M375
    ),
    light_inactive = ColorFamily(
        color = create(0x5E0D0B)
    ),
    light_off = ColorFamily(
        color = white_M600
    ),
    switchColorOn = ColorFamily(
        color = red_M375
    ),
    strikethroughColor = ColorFamily(
        color = create(0x5E0D0B)
    ),
    circleColor = ColorFamily(
        color = red_M375
    ),
    selectedColor = ColorFamily(
        color = red_M375
    ),
    selectedColor2 = ColorFamily(
        color = create(0x5E0D0B)
    ),
    unselectedColor = ColorFamily(
        color = white_M500
    ),
    positiveSelColor = ColorFamily(
        color = red_M375
    ),
    neutralSelColor = ColorFamily(
        color = white_M600
    ),
    negativeSelColor = ColorFamily(
        color = create(0xABABAB)
    ),

    mapPoiFillColor = ColorFamily(
        color = create(0xBF91100C)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x8091100C)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1A91100C)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF050C04)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xE0E0E0)
    ),
    progressBarColorEnd = ColorFamily(
        color = red_M375
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xE0E0E0)
    ),
    sanityPieEndColor = ColorFamily(
        color = red_M375
    ),

    splashTextColor = ColorFamily(
        color = red_M375
    ),

    codex1 = ColorFamily(
        color = create(0x200605)
    ),
    codex2 = ColorFamily(
        color = create(0x120202)
    ),
    codex3 = ColorFamily(
        color = create(0xE4DEDE)
    ),
    codex4 = ColorFamily(
        color = white
    ),
    codex5 = ColorFamily(
        color = create(0x575050)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x403E1312)
    ),

    navigationFooterIcon = ColorFamily(
        color = white_M600
    ),
    navigationFooterIconAlt = ColorFamily(
        color = red_M375
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = create(0xE0E0E0)
    ),
    inboxNotification = ColorFamily(
        color = red_M375
    )
)
val Specialist = Prestige.copy(
    a = ColorFamily(
        color = create(0xFEFEFE)
    ),
    b = ColorFamily(
        color = white_M25
    ),
    c = ColorFamily(
        color = create(0xE7E8E7)
    ),
    d = ColorFamily(
        color = create(0xC9CCC8)
    ),
    e = ColorFamily(
        color = create(0x30362B)
    ),
    f = ColorFamily(
        color = create(0x545B4D)
    ),
    g = ColorFamily(
        color = green_M150
    ),
    h = ColorFamily(
        color = create(0x80994D)
    ),
    i = ColorFamily(
        color = create(0xA0B17B)
    ),
    j = ColorFamily(
        color = green_M850
    ),
    k = ColorFamily(
        color = create(0x0F1B0A)
    ),
    l = ColorFamily(
        color = create(0x070C05)
    ),
    m = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = green_M850
    ),
    theme_coreColor_1 = ColorFamily(
        color = green_M150
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0x80994D)
    ),
    theme_coreColor_3 = ColorFamily(
        color = green_M850
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x070C05)
    ),
    theme_coreColor_5 = ColorFamily(
        color = create(0xFEFEFE)
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xE7E8E7)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0xC9CCC8)
    ),
    theme_coreColor_8 = ColorFamily(
        color = white_M600
    ),

    /*
    colorBackground = ColorFamily(
        color = create(0x070C05)
    ),
    statusBarColor = ColorFamily(
        color = create(0x070C05)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x070C05)
    ),
    */

    foregroundColor = ColorFamily(
        color = create(0xFEFEFE)
    ),
    middlegroundColor = ColorFamily(
        color = create(0xC9CCC8)
    ),
    backgroundColor = ColorFamily(
        color = create(0x070C05)
    ),
    backgroundColorOnBackground = ColorFamily(
        color = create(0x0F1B0A)
    ),
    statusBarColor = ColorFamily(
        color = create(0x070C05)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x070C05)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x070C05)
    ),
    windowStrokeColor = ColorFamily(
        color = create(0xFEFEFE)
    ),

    colorPrimary = ColorFamily(
        color = create(0x070C05)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x070C05)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x070C05)
    ),
    colorSecondary = ColorFamily(
        color = create(0x070C05)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x070C05)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x070C05)
    ),

    textColorPrimary = ColorFamily(
        color = green_M150
    ),
    textColorSecondary = ColorFamily(
        color = create(0x80994D)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x545B4D)
    ),
    textColorBody = ColorFamily(
        color = create(0xFEFEFE)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0x80994D)
    ),
    light_active = ColorFamily(
        color = green_M150
    ),
    light_inactive = ColorFamily(
        color = create(0x80994D)
    ),
    light_off = ColorFamily(
        color = white_M600
    ),
    switchColorOn = ColorFamily(
        color = green_M150
    ),
    strikethroughColor = ColorFamily(
        color = create(0xA0B17B)
    ),
    circleColor = ColorFamily(
        color = green_M150
    ),
    selectedColor = ColorFamily(
        color = create(0x80994D)
    ),
    selectedColor2 = ColorFamily(
        color = create(0x80994D)
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = green_M150
    ),
    neutralSelColor = ColorFamily(
        color = create(0x545B4D)
    ),
    negativeSelColor = ColorFamily(
        color = create(0xA0B17B)
    ),
    mapPoiFillColor = ColorFamily(
        color = green_M150_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = green_M150_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = green_M150_A10
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF070C05)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0x80994D)
    ),
    progressBarColorEnd = ColorFamily(
        color = green_M150
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xE7E8E7)
    ),
    sanityPieEndColor = ColorFamily(
        color = green_M150
    ),

    splashTextColor = ColorFamily(
        color = green_M150
    ),

    codex1 = ColorFamily(
        color = create(0x070C05)
    ),
    codex2 = ColorFamily(
        color = create(0x24291F)
    ),
    codex3 = ColorFamily(
        color = create(0xD9ECAF)
    ),
    codex4 = ColorFamily(
        color = green_M150
    ),
    codex5 = ColorFamily(
        color = create(0x545B4D)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40070C05)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x545B4D)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = green_M150
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = create(0xBF070C05)
    ),
    inboxNotification = ColorFamily(
        color = green_M150
    )
)

val Analyst = Prestige.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xCD2547)
    ),
    c = ColorFamily(
        color = create(0x7BCD26)
    ),
    d = ColorFamily(
        color = create(0x2667CD)
    ),
    e = ColorFamily(
        color = create(0xA7968C)
    ),
    f = ColorFamily(
        color = create(0x84736A)
    ),
    g = ColorFamily(
        color = orange_M525
    ),
    h = ColorFamily(
        color = red_M850
    ),
    i = ColorFamily(
        color = red_M950
    ),
    j = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = create(0x84736A)
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xCD2547)
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0x2667CD)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0x7BCD26)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x101E01)
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xA7968C)
    ),
    theme_coreColor_7 = ColorFamily(
        color = orange_M525
    ),
    theme_coreColor_8 = ColorFamily(
        color = red_M850
    ),

    /*android:colorBackground = ColorFamily(
    color = red_M850
    ),
    android:statusBarColor = ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = ColorFamily(
    color = red_M850
    ),*/
    foregroundColor = ColorFamily(
        color = create(0xA7968C)
    ),
    middlegroundColor = ColorFamily(
        color = create(0x84736A)
    ),
    backgroundColor = ColorFamily(
        color = red_M850
    ),
    backgroundColorOnBackground = ColorFamily(
        color = orange_M525
    ),
    statusBarColor = ColorFamily(
        color = red_M850
    ),
    navigationBarColor = ColorFamily(
        color = red_M850
    ),
    windowBackgroundColor = ColorFamily(
        color = red_M850
    ),
    windowStrokeColor = ColorFamily(
        color = create(0xA7968C)
    ),
    colorPrimary = ColorFamily(
        color = red_M850
    ),
    colorPrimaryVariant = ColorFamily(
        color = red_M850
    ),
    colorOnPrimary = ColorFamily(
        color = red_M850
    ),
    colorSecondary = ColorFamily(
        color = red_M850
    ),
    colorSecondaryVariant = ColorFamily(
        color = red_M850
    ),
    colorOnSecondary = ColorFamily(
        color = red_M850
    ),

    textColorPrimary = ColorFamily(
        color = create(0xCD2547)
    ),
    textColorSecondary = ColorFamily(
        color = create(0x7BCD26)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x2667CD)
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xA7968C)
    ),
    light_active = ColorFamily(
        color = create(0x2667CD)
    ),
    light_inactive = ColorFamily(
        color = orange_M525
    ),
    light_off = ColorFamily(
        color = red_M850
    ),
    switchColorOn = ColorFamily(
        color = create(0x2667CD)
    ),
    buttonColor = ColorFamily(
        color = create(0x7BCD26)
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xCD2547)
    ),
    strikethroughColor = ColorFamily(
        color = create(0xCD2547)
    ),
    circleColor = ColorFamily(
        color = create(0x7BCD26)
    ),
    selectedColor = ColorFamily(
        color = create(0x2667CD)
    ),
    selectedColor2 = ColorFamily(
        color = create(0x2667CD)
    ),
    unselectedColor = ColorFamily(
        color = orange_M525
    ),
    positiveSelColor = ColorFamily(
        color = create(0x7BCD26)
    ),
    neutralSelColor = ColorFamily(
        color = create(0xA7968C)
    ),
    negativeSelColor = ColorFamily(
        color = create(0xCD2547)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBF2667CD)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x942667CD)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1A2667CD)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xA7968C)
    ),
    progressBarColorEnd = ColorFamily(
        color = create(0x2667CD)
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xA7968C)
    ),
    sanityPieEndColor = ColorFamily(
        color = create(0x2667CD)
    ),

    splashTextColor = ColorFamily(
        color = create(0x2667CD)
    ),

    codex1 = ColorFamily(
        color = create(0x1C1917)
    ),
    codex3 = ColorFamily(
        color = create(0xEDE5E7)
    ),
    codex2 = ColorFamily(
        color = create(0x255BAF)
    ),
    codex4 = ColorFamily(
        color = create(0x7BCD26)
    ),
    codex5 = ColorFamily(
        color = create(0x101E01)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40201B19)
    ),

    navigationFooterIcon = ColorFamily(
        color = orange_M525
    ),
    navigationFooterIconAlt = ColorFamily(
        color = create(0x2667CD)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF1B1516)
    ),

    inboxNotification = ColorFamily(
        color = create(0xD3C9C3)
    ),

    )
val Agent = Prestige.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0x858686)
    ),
    c = ColorFamily(
        color = create(0x5E7E7B)
    ),
    d = ColorFamily(
        color = blue_M90
    ),
    e = ColorFamily(
        color = create(0x117166)
    ),
    f = ColorFamily(
        color = create(0x02A996)
    ),
    g = ColorFamily(
        color = create(0x167467)
    ),
    h = ColorFamily(
        color = create(0x1D4E49)
    ),
    i = ColorFamily(
        color = create(0x202A29)
    ),
    j = ColorFamily(
        color = create(0x101515)
    ),
    l = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = blue_M90
    ),
    theme_coreColor_1 = ColorFamily(
        color = white
    ),
    theme_coreColor_2 = ColorFamily(
        color = blue_M90
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0x167467)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x1D4E49)
    ),
    theme_coreColor_5 = ColorFamily(
        color = create(0x858686)
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0x5E7E7B)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x202A29)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x101515)
    ),

    /*android:colorBackground = ColorFamily(
    color = create(0x101515)
    ),
    android:statusBarColor = ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = ColorFamily(
    color = create(0x101515)
    ),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = create(0x868586)
    ),
    backgroundColor  = ColorFamily(
        color = create(0x101515)
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x131919)
    ),
    statusBarColor = ColorFamily(
        color = create(0x101515)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x101515)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x0E1212)
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = create(0x101515)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x101515)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x101515)
    ),
    colorSecondary = ColorFamily(
        color = create(0x101515)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x101515)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x101515)
    ),

    textColorPrimary = ColorFamily(
        color = blue_M90
    ),
    textColorSecondary = ColorFamily(
        color = create(0x02A996)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x167467)
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0x167467)
    ),
    light_active = ColorFamily(
        color = blue_M90
    ),
    light_inactive = ColorFamily(
        color = create(0x1D4E49)
    ),
    light_off = ColorFamily(
        color = create(0x202A29)
    ),
    switchColorOn = ColorFamily(
        color = blue_M90
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = white
    ),
    strikethroughColor = ColorFamily(
        color = create(0x1D4E49)
    ),
    circleColor = ColorFamily(
        color = blue_M90
    ),
    selectedColor = ColorFamily(
        color = create(0x167467)
    ),
    selectedColor2 = ColorFamily(
        color = create(0x167467)
    ),
    unselectedColor = ColorFamily(
        color = create(0x858686)
    ),
    positiveSelColor = ColorFamily(
        color = blue_M90
    ),
    neutralSelColor = ColorFamily(
        color = create(0x5E7E7B)
    ),
    negativeSelColor = ColorFamily(
        color = create(0x1D4E49)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBF02A996)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x9402A996)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1A02A996)
    ),

    progressBarColorStart = ColorFamily(
        color = white
    ),
    progressBarColorEnd = ColorFamily(
        color = blue_M90
    ),
    sanityPieStartColor = ColorFamily(
        color = white
    ),
    sanityPieEndColor = ColorFamily(
        color = blue_M90
    ),

    splashTextColor = ColorFamily(
        color = blue_M90
    ),

    codex1 = ColorFamily(
        color = create(0x101515)
    ),
    codex3 = ColorFamily(
        color = create(0xB7CFCC)
    ),
    codex2 = ColorFamily(
        color = create(0x202A29)
    ),
    codex4 = ColorFamily(
        color = blue_M90
    ),
    codex5 = ColorFamily(
        color = create(0x5E7E7B)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40101515)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x858686)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = blue_M90
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF101515)
    ),

    inboxNotification = ColorFamily(
        color = blue_M90
    ),
)
val Operator = Prestige.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xFEFEFE)
    ),
    c = ColorFamily(
        color = create(0xF9F8FA)
    ),
    d = ColorFamily(
        color = purple_M475
    ),
    e = ColorFamily(
        color = blue_M150
    ),
    f = ColorFamily(
        color = purple_M150
    ),
    g = ColorFamily(
        color = purple_M450
    ),
    h = ColorFamily(
        color = purple_M875
    ),
    i = ColorFamily(
        color = blue_M950
    ),

    theme_colorPrimary = ColorFamily(
        color = purple_M450
    ),
    theme_coreColor_1 = ColorFamily(
        color = purple_M150
    ),
    theme_coreColor_2 = ColorFamily(
        color = purple_M450
    ),
    theme_coreColor_3 = ColorFamily(
        color = blue_M150
    ),
    theme_coreColor_4 = ColorFamily(
        color = purple_M250
    ),
    theme_coreColor_5 = ColorFamily(
        color = create(0xF9F8FA)
    ),
    theme_coreColor_6 = ColorFamily(
        color = purple_M475
    ),
    theme_coreColor_7 = ColorFamily(
        color = purple_M875
    ),
    theme_coreColor_8 = ColorFamily(
        color = blue_M950
    ),

    /*android:colorBackground = ColorFamily(
        color = blue_M950
        ),
android:statusBarColor = ColorFamily(
color = blue_M950
),
android:navigationBarColor = ColorFamily(
color = blue_M950
),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = blue_M150
    ),
    backgroundColor = ColorFamily(
        color = blue_M950
    ),
    backgroundColorOnBackground = ColorFamily(
        color = blue_M900
    ),
    statusBarColor = ColorFamily(
        color = blue_M950
    ),
    navigationBarColor = ColorFamily(
        color = blue_M950
    ),
    windowBackgroundColor = ColorFamily(
        color = blue_M950
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = blue_M950
    ),
    colorPrimaryVariant = ColorFamily(
        color = blue_M950
    ),
    colorOnPrimary = ColorFamily(
        color = blue_M950
    ),
    colorSecondary = ColorFamily(
        color = blue_M950
    ),
    colorSecondaryVariant = ColorFamily(
        color = blue_M950
    ),
    colorOnSecondary = ColorFamily(
        color = blue_M950
    ),

    textColorBody = ColorFamily(
        color = create(0xF9F8FA)
    ),
    circleColor = ColorFamily(
        color = purple
    ),
    mapRoomFillColor = ColorFamily(
        color = purple_A10
    ),
    light_inactive = ColorFamily(
        color = purple_M50_A40
    ),
    textColorPrimary = ColorFamily(
        color = blue_M150
    ),
    light_active = ColorFamily(
        color = blue_M150
    ),
    textColorBodyEmphasis = ColorFamily(
        color = purple_M250
    ),
    switchColorOn = ColorFamily(
        color = purple_M300
    ),
    inboxNotification = ColorFamily(
        color = purple_M150
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = purple_M475
    ),
    strikethroughColor = ColorFamily(
        color = purple_M300
    ),
    textColorSecondary = ColorFamily(
        color = purple_M300
    ),
    mapRoomBorderColor = ColorFamily(
        color = purple_M300_A58
    ),
    mapPoiFillColor = ColorFamily(
        color = purple_M300_A75
    ),
    selectedColor = ColorFamily(
        color = purple_M450
    ),
    selectedColor2 = ColorFamily(
        color = purple_M50_A40
    ),
    textColorTertiary = ColorFamily(
        color = purple_M450
    ),
    unselectedColor = ColorFamily(
        color = purple_M475
    ),
    negativeSelColor = ColorFamily(
        color = blue_M150
    ),
    positiveSelColor = ColorFamily(
        color = purple_M150
    ),
    neutralSelColor = ColorFamily(
        color = purple_M475
    ),
    light_off = ColorFamily(
        color = purple_M875
    ),

    progressBarColorStart = ColorFamily(
        color = purple_M500
    ),
    progressBarColorEnd = ColorFamily(
        color = blue_M150
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xF9F8FA)
    ),
    sanityPieEndColor = ColorFamily(
        color = purple_M150
    ),

    splashTextColor = ColorFamily(
        color = purple_M150
    ),

    codex1 = ColorFamily(
        color = create(0x130B29)
    ),
    codex3 = ColorFamily(
        color = create(0xC5BCCD)
    ),
    codex2 = ColorFamily(
        color = create(0x370C37)
    ),
    codex4 = ColorFamily(
        color = purple_M150
    ),
    codex5 = ColorFamily(
        color = create(0x53425E)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40130B29)
    ),

    navigationFooterIcon = ColorFamily(
        color = purple_M475
    ),
    navigationFooterIconAlt = ColorFamily(
        color = purple_M150
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF00000E)
    ),
)
val Commissioner = Prestige.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xACAAAC)
    ),
    c = ColorFamily(
        color = create(0xF8EFD9)
    ),
    d = ColorFamily(
        color = orange_M05
    ),
    e = ColorFamily(
        color = create(0xE5A348)
    ),
    f = ColorFamily(
        color = create(0x765932)
    ),
    h = ColorFamily(
        color = purple_M825
    ),
    i = ColorFamily(
        color = black_M50
    ),
    j = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = orange_M05
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xF8EFD9)
    ),
    theme_coreColor_2 = ColorFamily(
        color = orange_M05
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0xE5A348)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x765932)
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xACAAAC)
    ),
    theme_coreColor_7 = ColorFamily(
        color = purple_M825
    ),
    theme_coreColor_8 = ColorFamily(
        color = black_M50
    ),

    /*android:colorBackground = ColorFamily(
        color = black_M50
        ),
android:statusBarColor = ColorFamily(
        color = colorOnSecondary
),
android:navigationBarColor = ColorFamily(
color = black_M50
),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor = ColorFamily(
        color = black_M50
    ),
    backgroundColorOnBackground = ColorFamily(
        color = purple_M825
    ),
    statusBarColor = ColorFamily(
        color = black_M50
    ),
    navigationBarColor = ColorFamily(
        color = black_M50
    ),
    windowBackgroundColor = ColorFamily(
        color = black_M50
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = black_M50
    ),
    colorPrimaryVariant = ColorFamily(
        color = black_M50
    ),
    colorOnPrimary = ColorFamily(
        color = black_M50
    ),
    colorSecondary = ColorFamily(
        color = black_M50
    ),
    colorSecondaryVariant = ColorFamily(
        color = black_M50
    ),
    colorOnSecondary = ColorFamily(
        color = black_M50
    ),

    textColorPrimary = ColorFamily(
        color = orange_M05
    ),
    textColorSecondary = ColorFamily(
        color = create(0xE5A348)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x765932)
    ),
    textColorBody = ColorFamily(
        color = create(0xF8EFD9)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xACAAAC)
    ),
    light_active = ColorFamily(
        color = orange_M05
    ),
    light_inactive = ColorFamily(
        color = create(0x765932)
    ),
    light_off = ColorFamily(
        color = purple_M825
    ),
    switchColorOn = ColorFamily(
        color = orange_M10
    ),
    inboxNotification = ColorFamily(
        color = orange_M05
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xFDF9EE)
    ),
    strikethroughColor = ColorFamily(
        color = create(0x765932)
    ),
    circleColor = ColorFamily(
        color = orange_M05
    ),
    selectedColor = ColorFamily(
        color = create(0xE5A348)
    ),
    selectedColor2 = ColorFamily(
        color = create(0xE5A348)
    ),
    unselectedColor = ColorFamily(
        color = create(0xACAAAC)
    ),
    positiveSelColor = ColorFamily(
        color = orange_M05
    ),
    neutralSelColor = ColorFamily(
        color = create(0xACAAAC)
    ),
    negativeSelColor = ColorFamily(
        color = create(0x765932)
    ),
    mapPoiFillColor = ColorFamily(
        color = orange_M300_A75
    ),
    mapRoomBorderColor = ColorFamily(
        color = orange_M300_A58
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xF8EFD9)
    ),
    progressBarColorEnd = ColorFamily(
        color = orange_M05
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xF8EFD9)
    ),
    sanityPieEndColor = ColorFamily(
        color = orange_M05
    ),

    splashTextColor = ColorFamily(
        color = orange_M05
    ),

    codex1 = ColorFamily(
        color = create(0x232323)
    ),
    codex3 = ColorFamily(
        color = create(0xFFF9F0)
    ),
    codex2 = ColorFamily(
        color = create(0x130B00)
    ),
    codex4 = ColorFamily(
        color = orange_M05
    ),
    codex5 = ColorFamily(
        color = create(0x3C3B3B)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40232323)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0xACAAAC)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = create(0xE5A348)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF080808)
    ),

    )

val Easter = Event.copy(
    k = ColorFamily(
        color = white
    ),
    l = ColorFamily(
        color = create(0xF8E8BD)
    ),
    c = ColorFamily(
        color = orange_M05
    ),
    j = ColorFamily(
        color = create(0xE1EBE5)
    ),
    i = ColorFamily(
        color = create(0x7CAB91)
    ),
    b = ColorFamily(
        color = green_M550
    ),
    g = ColorFamily(
        color = create(0x21654A)
    ),
    a = ColorFamily(
        color = green_M650
    ),
    e = ColorFamily(
        color = create(0x030C0A)
    ),
    f = ColorFamily(
        color = create(0x1C180D)
    ),
    d = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = green_M550
    ),
    theme_coreColor_1 = ColorFamily(
        color = white
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xF8E8BD)
    ),
    theme_coreColor_3 = ColorFamily(
        color = orange_M75
    ),
    theme_coreColor_4 = ColorFamily(
        color = white_M600
    ),
    theme_coreColor_5 = ColorFamily(
        color = create(0xE1EBE5)
    ),
    theme_coreColor_6 = ColorFamily(
        color = green_M550
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x21654A)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x030C0A)
    ),

    /*android:colorBackground = ColorFamily(
        color = create(0x030C0A)
        ),
android:statusBarColor = ColorFamily(
        color = colorOnSecondary
),
android:navigationBarColor = ColorFamily(
color = create(0x030C0A)
),*/
    foregroundColor = ColorFamily(
        color = create(0xE1EBE5)
    ),
    middlegroundColor = ColorFamily(
        color = create(0x7CAB91)
    ),
    backgroundColor  = ColorFamily(
        color = create(0x030C0A)
    ),
    backgroundColorOnBackground = ColorFamily(
        color = create(0x061915)
    ),
    statusBarColor = ColorFamily(
        color = create(0x030C0A)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x030C0A)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x030C0A)
    ),
    windowStrokeColor = ColorFamily(
        color = create(0xE1EBE5)
    ),
    colorPrimary = ColorFamily(
        color = create(0x030C0A)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x030C0A)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x030C0A)
    ),
    colorSecondary = ColorFamily(
        color = create(0x030C0A)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x030C0A)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x030C0A)
    ),

    textColorPrimary = ColorFamily(
        color = green_M550
    ),
    textColorSecondary = ColorFamily(
        color = orange_M75
    ),
    textColorTertiary = ColorFamily(
        color = create(0x21654A)
    ),
    textColorBody = ColorFamily(
        color = create(0xE1EBE5)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xF8E8BD)
    ),
    light_active = ColorFamily(
        color = orange_M75
    ),
    light_inactive = ColorFamily(
        color = create(0xF8E8BD)
    ),
    light_off = ColorFamily(
        color = create(0x1C180D)
    ),
    switchColorOn = ColorFamily(
        color = orange_M75
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xE1EBE5)
    ),
    inboxNotification = ColorFamily(
        color = orange_M05
    ),
    strikethroughColor = ColorFamily(
        color = create(0x21654A)
    ),
    circleColor = ColorFamily(
        color = orange_M75
    ),
    selectedColor = ColorFamily(
        color = create(0xF8E8BD)
    ),
    selectedColor2 = ColorFamily(
        color = green_M550
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = orange_M75
    ),
    neutralSelColor = ColorFamily(
        color = create(0xE1EBE5)
    ),
    negativeSelColor = ColorFamily(
        color = create(0x21654A)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBF348662)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94ECC768)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1AECC768)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xE1EBE5)
    ),
    progressBarColorEnd = ColorFamily(
        color = orange_M75
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xE1EBE5)
    ),
    sanityPieEndColor = ColorFamily(
        color = orange_M75
    ),

    splashTextColor = ColorFamily(
        color = green_M550
    ),

    codex1 = ColorFamily(
        color = create(0x203E36)
    ),
    codex3 = ColorFamily(
        color = create(0xF8E8BD)
    ),
    codex2 = ColorFamily(
        color = create(0x09231D)
    ),
    codex4 = ColorFamily(
        color = orange_M75
    ),
    codex5 = ColorFamily(
        color = create(0x2D3332)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40203E36)
    ),

    navigationFooterIcon = ColorFamily(
        color = white_M600
    ),
    navigationFooterIconAlt = ColorFamily(
        color = orange_M75
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF030C0A)
    ),

    colorOnButton = ColorFamily(
        color = create(0x030C0A)
    ),
)
val Halloween23 = Event.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0x373737)
    ),
    e = ColorFamily(
        color = create(0xD7CCC3)
    ),
    f = ColorFamily(
        color = create(0x9F4B0D)
    ),
    g = ColorFamily(
        color = create(0xB3540A)
    ),
    h = ColorFamily(
        color = create(0xC85E0D)
    ),
    i = ColorFamily(
        color = create(0xEC700D)
    ),
    j = ColorFamily(
        color = create(0x3F1602)
    ),
    k = ColorFamily(
        color = create(0x130700)
    ),
    l = ColorFamily(
        color = create(0x0C0400)
    ),
    m = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = create(0xEC700D)
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xEC700D)
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xC85E0D)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0x9F4B0D)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x3F1602)
    ),
    theme_coreColor_5 = ColorFamily(
        color = create(0xD7CCC3)
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0x373737)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x130700)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x0C0400)
    ),

    /*android:colorBackground = ColorFamily(
        color = create(0x0C0400)
        ),
android:statusBarColor = ColorFamily(
        color = colorOnSecondary
),
android:navigationBarColor = ColorFamily(
color = create(0x0C0400)
),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = create(0xD7CCC3)
    ),
    backgroundColor  = ColorFamily(
        color = create(0x0C0400)
    ),
    backgroundColorOnBackground = ColorFamily(
        color = create(0x3F1602)
    ),
    statusBarColor = ColorFamily(
        color = create(0x0C0400)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x0C0400)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x0C0400)
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = create(0x0C0400)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x0C0400)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x0C0400)
    ),
    colorSecondary = ColorFamily(
        color = create(0x0C0400)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x0C0400)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x0C0400)
    ),

    textColorPrimary = ColorFamily(
        color = create(0xEC700D)
    ),
    textColorSecondary = ColorFamily(
        color = create(0xC85E0D)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x9F4B0D)
    ),
    textColorBody = ColorFamily(
        color = create(0xD7CCC3)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xB3540A)
    ),
    light_active = ColorFamily(
        color = create(0xEC700D)
    ),
    light_inactive = ColorFamily(
        color = create(0x9F4B0D)
    ),
    light_off = ColorFamily(
        color = create(0x3F1602)
    ),
    switchColorOn = ColorFamily(
        color = create(0xEC700D)
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xD7CCC3)
    ),
    strikethroughColor = ColorFamily(
        color = create(0xD7CCC3)
    ),
    circleColor = ColorFamily(
        color = create(0xEC700D)
    ),
    selectedColor = ColorFamily(
        color = create(0xC85E0D)
    ),
    selectedColor2 = ColorFamily(
        color = create(0xC85E0D)
    ),
    unselectedColor = ColorFamily(
        color = create(0x373737)
    ),
    positiveSelColor = ColorFamily(
        color = create(0xEC700D)
    ),
    neutralSelColor = ColorFamily(
        color = create(0x373737)
    ),
    negativeSelColor = ColorFamily(
        color = create(0xD7CCC3)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBFEC700D)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94EC700D)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1AEC700D)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xD7CCC3)
    ),
    progressBarColorEnd = ColorFamily(
        color = create(0xEC700D)
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xD7CCC3)
    ),
    sanityPieEndColor = ColorFamily(
        color = create(0xEC700D)
    ),

    splashTextColor = ColorFamily(
        color = create(0xEC700D)
    ),

    codex1 = ColorFamily(
        color = create(0x1B0A00)
    ),
    codex3 = ColorFamily(
        color = create(0xD7CCC3)
    ),
    codex2 = ColorFamily(
        color = create(0x130700)
    ),
    codex4 = ColorFamily(
        color = create(0xEC700D)
    ),
    codex5 = ColorFamily(
        color = create(0x373737)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x401B0A00)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x373737)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = create(0xC85E0D)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF0C0400)
    ),

    inboxNotification = ColorFamily(
        color = create(0xEC700D)
    ),
)
val Holiday22 = Event.copy(
    a = ColorFamily(
        color = create(0xFEFEFE)
    ),
    b = ColorFamily(
        color = create(0xDDDCDB)
    ),
    c = ColorFamily(
        color = orange_M50
    ),
    d = ColorFamily(
        color = create(0x645956)
    ),
    e = ColorFamily(
        color = create(0xF1F3F7)
    ),
    f = ColorFamily(
        color = blue_M125
    ),
    k = ColorFamily(
        color = create(0x2E5497)
    ),
    g = ColorFamily(
        color = create(0x2A2E45)
    ),
    h = ColorFamily(
        color = blue_M750
    ),
    i = ColorFamily(
        color = create(0x0D0E19)
    ),
    j = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = blue_M125
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xFEFEFE)
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xDDDCDB)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0xF8BC7F)
    ),
    theme_coreColor_4 = ColorFamily(
        color = orange_M50
    ),
    theme_coreColor_5 = ColorFamily(
        color = blue_M125
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0x2E5497)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x2A2E45)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x0D0E19)
    ),

    /*android:colorBackground = ColorFamily(
        color = create(0x0D0E19)
        ),
android:statusBarColor = ColorFamily(
        color = colorOnSecondary
),
android:navigationBarColor = ColorFamily(
color = create(0x0D0E19)
),*/
    foregroundColor = ColorFamily(
        color = create(0xFEFEFE)
    ),
    middlegroundColor = ColorFamily(
        color = create(0x645956)
    ),
    backgroundColor  = ColorFamily(
        color = create(0x0D0E19)
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x151529)
    ),
    statusBarColor = ColorFamily(
        color = create(0x0D0E19)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x0D0E19)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x0D0E19)
    ),
    windowStrokeColor = ColorFamily(
        color = create(0xFEFEFE)
    ),
    colorPrimary = ColorFamily(
        color = create(0x0D0E19)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x0D0E19)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x0D0E19)
    ),
    colorSecondary = ColorFamily(
        color = create(0x0D0E19)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x0D0E19)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x0D0E19)
    ),

    textColorPrimary = ColorFamily(
        color = orange_M50
    ),
    textColorSecondary = ColorFamily(
        color = blue_M125
    ),
    textColorTertiary = ColorFamily(
        color = create(0x2E5497)
    ),
    textColorBody = ColorFamily(
        color = create(0xFEFEFE)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xF8BC7F)
    ),
    light_active = ColorFamily(
        color = blue_M125
    ),
    light_inactive = ColorFamily(
        color = create(0x2A2E45)
    ),
    light_off = ColorFamily(
        color = blue_M750
    ),
    switchColorOn = ColorFamily(
        color = blue_M125
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xF8BC7F)
    ),
    strikethroughColor = ColorFamily(
        color = blue_M125
    ),
    circleColor = ColorFamily(
        color = orange_M50
    ),
    selectedColor = ColorFamily(
        color = blue_M125
    ),
    selectedColor2 = ColorFamily(
        color = blue_M125
    ),
    unselectedColor = ColorFamily(
        color = create(0x2A2E45)
    ),
    positiveSelColor = ColorFamily(
        color = orange_M50
    ),
    neutralSelColor = ColorFamily(
        color = create(0xDDDCDB)
    ),
    negativeSelColor = ColorFamily(
        color = blue_M125
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBFF58514)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94447CDD)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1A447CDD)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xFEFEFE)
    ),
    progressBarColorEnd = ColorFamily(
        color = orange_M50
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xFEFEFE)
    ),
    sanityPieEndColor = ColorFamily(
        color = orange_M50
    ),

    splashTextColor = ColorFamily(
        color = blue_M125
    ),

    codex1 = ColorFamily(
        color = create(0x293750)
    ),
    codex3 = ColorFamily(
        color = create(0xF3E8DE)
    ),
    codex2 = ColorFamily(
        color = blue_M750
    ),
    codex4 = ColorFamily(
        color = orange_M50
    ),
    codex5 = ColorFamily(
        color = create(0x2A2E45)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40293750)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x2A2E45)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = blue_M125
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF0D0E19)
    ),

    inboxNotification = ColorFamily(
        color = create(0x6996E4)
    ),

    )
val Holiday23 = Event.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xE4D0AE)
    ),
    c = ColorFamily(
        color = create(0xE9AD4C)
    ),
    d = ColorFamily(
        color = create(0x447CDE)
    ),
    f = ColorFamily(
        color = create(0x213A67)
    ),
    k = ColorFamily(
        color = create(0x1B1C33)
    ),
    j = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = create(0x447CDE)
    ),
    theme_coreColor_1 = ColorFamily(
        color = white
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xFEFEFE)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0xE4D0AE)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0xE9AD4C)
    ),
    theme_coreColor_5 = ColorFamily(
        color = create(0x447CDE)
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0x2A487E)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x344663)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x1B1C33)
    ),
    /*
                android:colorBackground = ColorFamily(
                    color = create(0x1B1C33)
                    ),
            android:statusBarColor = ColorFamily(
                    color = colorOnSecondary
            ),
            android:navigationBarColor = ColorFamily(
            color = create(0x1B1C33)
            ),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = create(0x645956)
    ),
    backgroundColor = ColorFamily(
        color = create(0x1B1C33)
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x131423)
    ),
    statusBarColor = ColorFamily(
        color = create(0x1B1C33)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x1B1C33)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x1B1C33)
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = create(0x1B1C33)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x1B1C33)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x1B1C33)
    ),
    colorSecondary = ColorFamily(
        color = create(0x1B1C33)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x1B1C33)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x1B1C33)
    ),

    textColorPrimary = ColorFamily(
        color = create(0xE9AD4C)
    ),
    textColorSecondary = ColorFamily(
        color = create(0x447CDE)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x2E5497)
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xE4D0AE)
    ),
    light_active = ColorFamily(
        color = create(0xE9AD4C)
    ),
    light_inactive = ColorFamily(
        color = create(0x40E9AD4C)
    ),
    light_off = ColorFamily(
        color = create(0x2A2E45)
    ),
    switchColorOn = ColorFamily(
        color = create(0x447CDE)
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0x447CDE)
    ),
    strikethroughColor = ColorFamily(
        color = create(0x447CDE)
    ),
    circleColor = ColorFamily(
        color = create(0xE9AD4C)
    ),
    selectedColor = ColorFamily(
        color = create(0xE9AD4C)
    ),
    selectedColor2 = ColorFamily(
        color = create(0xE9AD4C)
    ),
    unselectedColor = ColorFamily(
        color = create(0x213A67)
    ),
    positiveSelColor = ColorFamily(
        color = create(0xE9AD4C)
    ),
    neutralSelColor = ColorFamily(
        color = white
    ),
    negativeSelColor = ColorFamily(
        color = create(0x447CDE)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBFE9AD4C)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94E9AD4C)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1AE9AD4C)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xFEFEFE)
    ),
    progressBarColorEnd = ColorFamily(
        color = create(0xE9AD4C)
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xFEFEFE)
    ),
    sanityPieEndColor = ColorFamily(
        color = create(0xE9AD4C)
    ),

    splashTextColor = ColorFamily(
        color = create(0x447CDE)
    ),

    codex1 = ColorFamily(
        color = create(0x2A487E)
    ),
    codex3 = ColorFamily(
        color = create(0xECDDC5)
    ),
    codex2 = ColorFamily(
        color = create(0x447CDE)
    ),
    codex4 = ColorFamily(
        color = create(0xE9AD4C)
    ),
    codex5 = ColorFamily(
        color = create(0x344663)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x402A487E)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0xB1AEA9)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = create(0xE9AD4C)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF1B1C33)
    ),

    inboxNotification = ColorFamily(
        color = create(0xE9AD4C)
    ),

    )

val Discord = Community.copy(
    i = ColorFamily(
        color = white
    ),
    j = ColorFamily(
        color = create(0xF3F3F7)
    ),
    h = ColorFamily(
        color = create(0xE9E9EA)
    ),
    g = ColorFamily(
        color = create(0xC3C4C6)
    ),
    k = ColorFamily(
        color = create(0xAAB3F3)
    ),
    l = ColorFamily(
        color = create(0x717AF2)
    ),
    b = ColorFamily(
        color = blue_M75
    ),
    a = ColorFamily(
        color = blue_M850
    ),
    m = ColorFamily(
        color = create(0x131517)
    ),
    d = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = create(0x717AF2)
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xAAB3F3)
    ),
    theme_coreColor_2 = ColorFamily(
        color = blue_M75
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0x5763EC)
    ),
    theme_coreColor_4 = ColorFamily(
        color = blue_M850
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xF3F3F7)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0xC3C4C6)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x131517)
    ),
    /*
                android:colorBackground = ColorFamily(
                    color = create(0x131517)
                    ),
            android:statusBarColor = ColorFamily(
                    color = colorOnSecondary
            ),
            android:navigationBarColor = ColorFamily(
            color = create(0x131517)
            ),*/
    foregroundColor = ColorFamily(
        color = create(0xF3F3F7)
    ),
    middlegroundColor = ColorFamily(
        color = create(0xC3C4C6)
    ),
    backgroundColor  = ColorFamily(
        color = create(0x131517)
    ),
    backgroundColorOnBackground = ColorFamily(
        color = blue_M850
    ),
    statusBarColor = ColorFamily(
        color = create(0x131517)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x131517)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x131517)
    ),
    windowStrokeColor = ColorFamily(
        color = create(0xF3F3F7)
    ),
    colorPrimary = ColorFamily(
        color = create(0x131517)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x131517)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x131517)
    ),
    colorSecondary = ColorFamily(
        color = create(0x131517)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x131517)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x131517)
    ),

    textColorPrimary = ColorFamily(
        color = blue_M75
    ),
    textColorSecondary = ColorFamily(
        color = create(0x717AF2)
    ),
    textColorTertiary = ColorFamily(
        color = create(0xAAB3F3)
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xAAB3F3)
    ),
    light_active = ColorFamily(
        color = blue_M75
    ),
    light_inactive = ColorFamily(
        color = create(0xAAB3F3)
    ),
    light_off = ColorFamily(
        color = blue_M850
    ),
    switchColorOn = ColorFamily(
        color = blue_M75
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xAAB3F3)
    ),
    strikethroughColor = ColorFamily(
        color = create(0xC3C4C6)
    ),
    circleColor = ColorFamily(
        color = blue_M75
    ),
    selectedColor = ColorFamily(
        color = create(0xAAB3F3)
    ),
    selectedColor2 = ColorFamily(
        color = create(0xAAB3F3)
    ),
    unselectedColor = ColorFamily(
        color = blue_M850
    ),
    positiveSelColor = ColorFamily(
        color = blue_M75
    ),
    neutralSelColor = ColorFamily(
        color = create(0xF3F3F7)
    ),
    negativeSelColor = ColorFamily(
        color = create(0xC3C4C6)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBF5763EC)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x945763EC)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1A5763EC)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xF3F3F7)
    ),
    progressBarColorEnd = ColorFamily(
        color = blue_M75
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xF3F3F7)
    ),
    sanityPieEndColor = ColorFamily(
        color = blue_M75
    ),

    splashTextColor = ColorFamily(
        color = blue_M75
    ),

    codex1 = ColorFamily(
        color = create(0x1D2023)
    ),
    codex3 = ColorFamily(
        color = create(0xAAB3F3)
    ),
    codex2 = ColorFamily(
        color = create(0x131517)
    ),
    codex4 = ColorFamily(
        color = blue_M75
    ),
    codex5 = ColorFamily(
        color = create(0x2C3032)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x401D2023)
    ),

    navigationFooterIcon = ColorFamily(
        color = blue_M850
    ),
    navigationFooterIconAlt = ColorFamily(
        color = blue_M75
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF131517)
    ),

    inboxNotification = ColorFamily(
        color = create(0xF3F3F7)
    ),
)

val ContentCreator = Community.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xCACACA)
    ),
    c = ColorFamily(
        color = red_M225
    ),
    d = ColorFamily(
        color = create(0x9B1B35)
    ),
    e = ColorFamily(
        color = create(0x510E1B)
    ),
    f = ColorFamily(
        color = create(0x1E1013)
    ),
    g = ColorFamily(
        color = create(0x040303)
    ),
    h = ColorFamily(
        color = black_M50
    ),
    i = ColorFamily(
        color = create(0x010000)
    ),

    theme_colorPrimary = ColorFamily(
        color = red_M225
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xDF1740)
    ),
    theme_coreColor_2 = ColorFamily(
        color = red_M225
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0x510E1B)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x1E1013)
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xCACACA)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x99AEB3)
    ),
    theme_coreColor_8 = ColorFamily(
        color = black_M50
    ),
    /*
                android:colorBackground = ColorFamily(
                    color = black_M50
                    ),
            android:statusBarColor = ColorFamily(
                    color = colorOnSecondary
            ),
            android:navigationBarColor = ColorFamily(
            color = black_M50
            ),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = create(0xCACACA)
    ),
    backgroundColor = ColorFamily(
        color = black_M50
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x190509)
    ),
    statusBarColor = ColorFamily(
        color = black_M50
    ),
    navigationBarColor = ColorFamily(
        color = black_M50
    ),
    windowBackgroundColor = ColorFamily(
        color = black_M50
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = black_M50
    ),
    colorPrimaryVariant = ColorFamily(
        color = black_M50
    ),
    colorOnPrimary = ColorFamily(
        color = black_M50
    ),
    colorSecondary = ColorFamily(
        color = black_M50
    ),
    colorSecondaryVariant = ColorFamily(
        color = black_M50
    ),
    colorOnSecondary = ColorFamily(
        color = black_M50
    ),

    textColorPrimary = ColorFamily(
        color = red_M225
    ),
    textColorSecondary = ColorFamily(
        color = create(0x9B1B35)
    ),
    textColorTertiary = ColorFamily(
        color = create(0x510E1B)
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0x9B1B35)
    ),
    light_active = ColorFamily(
        color = red_M225
    ),
    light_inactive = ColorFamily(
        color = create(0x510E1B)
    ),
    light_off = ColorFamily(
        color = create(0x1E1013)
    ),
    switchColorOn = ColorFamily(
        color = red_M225
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xCACACA)
    ),
    strikethroughColor = ColorFamily(
        color = create(0x780C0C)
    ),
    circleColor = ColorFamily(
        color = create(0xDF1740)
    ),
    selectedColor = ColorFamily(
        color = red_M225
    ),
    selectedColor2 = ColorFamily(
        color = red_M225
    ),
    unselectedColor = ColorFamily(
        color = create(0x510E1B)
    ),
    positiveSelColor = ColorFamily(
        color = create(0xDF1740)
    ),
    neutralSelColor = ColorFamily(
        color = create(0xCACACA)
    ),
    negativeSelColor = ColorFamily(
        color = create(0x780C0C)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBFCD2547)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94CD2547)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1ACD2547)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xCACACA)
    ),
    progressBarColorEnd = ColorFamily(
        color = red_M225
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xCACACA)
    ),
    sanityPieEndColor = ColorFamily(
        color = red_M225
    ),

    splashTextColor = ColorFamily(
        color = red_M225
    ),

    codex1 = ColorFamily(
        color = create(0x510E1B)
    ),
    codex3 = ColorFamily(
        color = create(0x99AEB3)
    ),
    codex2 = ColorFamily(
        color = create(0x3C0A14)
    ),
    codex4 = ColorFamily(
        color = red_M225
    ),
    codex5 = ColorFamily(
        color = create(0x1E1013)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40510E1B)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0x510E1B)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = create(0xDF1740)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF080808)
    ),

    inboxNotification = ColorFamily(
        color = red_M225
    ),
)
val Developer = Community.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xE2E2DF)
    ),
    c = ColorFamily(
        color = create(0xAFA98C)
    ),
    d = ColorFamily(
        color = yellow_M25
    ),
    e = ColorFamily(
        color = create(0x868036)
    ),
    f = ColorFamily(
        color = create(0x4E4507)
    ),
    g = ColorFamily(
        color = create(0x171502)
    ),
    h = ColorFamily(
        color = create(0x020301)
    ),
    i = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = yellow_M25
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xCFCCB4)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0xD3C667)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x4E4507)
    ),
    theme_coreColor_1 = ColorFamily(
        color = yellow_M25
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xE2E2DF)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x535353)
    ),
    theme_coreColor_8 = ColorFamily(
        color = black_M50
    ),
    /*
                android:colorBackground = ColorFamily(
                    color = black_M50
                    ),
            android:statusBarColor = ColorFamily(
                    color = colorOnSecondary
            ),
            android:navigationBarColor = ColorFamily(
            color = black_M50
            ),*/
    foregroundColor = ColorFamily(
        color = create(0xE2E2DF)
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor = ColorFamily(
        color = black_M50
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x171502)
    ),
    statusBarColor = ColorFamily(
        color = black_M50
    ),
    navigationBarColor = ColorFamily(
        color = black_M50
    ),
    windowBackgroundColor = ColorFamily(
        color = black_M50
    ),
    windowStrokeColor = ColorFamily(
        color = create(0xE2E2DF)
    ),
    colorPrimary = ColorFamily(
        color = black_M50
    ),
    colorPrimaryVariant = ColorFamily(
        color = black_M50
    ),
    colorOnPrimary = ColorFamily(
        color = black_M50
    ),
    colorSecondary = ColorFamily(
        color = black_M50
    ),
    colorSecondaryVariant = ColorFamily(
        color = black_M50
    ),
    colorOnSecondary = ColorFamily(
        color = black_M50
    ),

    textColorPrimary = ColorFamily(
        color = yellow_M25
    ),
    textColorSecondary = ColorFamily(
        color = create(0xCDB70B)
    ),
    textColorTertiary = ColorFamily(
        color = create(0xD3C667)
    ),
    textColorBody = ColorFamily(
        color = create(0xE2E2DF)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xCFCCB4)
    ),
    light_active = ColorFamily(
        color = yellow_M25
    ),
    light_inactive = ColorFamily(
        color = create(0x66ECD316)
    ),
    light_off = ColorFamily(
        color = create(0x1AECD316)
    ),
    switchColorOn = ColorFamily(
        color = yellow_M25
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xD3C667)
    ),
    strikethroughColor = ColorFamily(
        color = create(0xE2E2DF)
    ),
    circleColor = ColorFamily(
        color = yellow_M25
    ),
    selectedColor = ColorFamily(
        color = yellow_M25
    ),
    selectedColor2 = ColorFamily(
        color = yellow_M25
    ),
    unselectedColor = ColorFamily(
        color = create(0xE2E2DF)
    ),
    positiveSelColor = ColorFamily(
        color = yellow_M25
    ),
    neutralSelColor = ColorFamily(
        color = create(0x4E4507)
    ),
    negativeSelColor = ColorFamily(
        color = create(0xE2E2DF)
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBFECD316)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94ECD316)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1AECD316)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xE2E2DF)
    ),
    progressBarColorEnd = ColorFamily(
        color = yellow_M25
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xE2E2DF)
    ),
    sanityPieEndColor = ColorFamily(
        color = yellow_M25
    ),

    splashTextColor = ColorFamily(
        color = yellow_M25
    ),

    codex1 = ColorFamily(
        color = create(0x535353)
    ),
    codex3 = ColorFamily(
        color = yellow_M25
    ),
    codex2 = ColorFamily(
        color = create(0x333333)
    ),
    codex4 = ColorFamily(
        color = yellow_M25
    ),
    codex5 = ColorFamily(
        color = create(0x332E09)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40535353)
    ),

    navigationFooterIcon = ColorFamily(
        color = create(0xE2E2DF)
    ),
    navigationFooterIconAlt = ColorFamily(
        color = yellow_M25
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0x40535353)
    ),

    inboxNotification = ColorFamily(
        color = create(0xE2E2DF)
    ),
)
val Translator = Community.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xFCFAFB)
    ),
    c = ColorFamily(
        color = create(0xE1C9CC)
    ),
    d = ColorFamily(
        color = create(0xD99CA5)
    ),
    e = ColorFamily(
        color = create(0xDA7F88)
    ),
    f = ColorFamily(
        color = red_M150
    ),
    g = ColorFamily(
        color = create(0x878887)
    ),
    h = ColorFamily(
        color = create(0x535052)
    ),
    i = ColorFamily(
        color = purple_M850
    ),
    j = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = red_M150
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xFCFAFB)
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xE1C9CC)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0xD99CA5)
    ),
    theme_coreColor_4 = ColorFamily(
        color = red_M150
    ),
    theme_coreColor_5 = ColorFamily(
        color = white_M400
    ),
    theme_coreColor_6 = ColorFamily(
        color = white_M600
    ),
    theme_coreColor_7 = ColorFamily(
        color = purple_M850
    ),
    theme_coreColor_8 = ColorFamily(
        color = black
    ),
    /*
            android:colorBackground = ColorFamily(
            color = black
            ),
            android:statusBarColor = ColorFamily(
                    color = colorOnSecondary
            ),
            android:navigationBarColor = ColorFamily(
            color = black
            ),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor = ColorFamily(
        color = black
    ),
    backgroundColorOnBackground = ColorFamily(
        color = black_M50
    ),
    statusBarColor = ColorFamily(
        color = black
    ),
    navigationBarColor = ColorFamily(
        color = black
    ),
    windowBackgroundColor = ColorFamily(
        color = black
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = black
    ),
    colorPrimaryVariant = ColorFamily(
        color = black
    ),
    colorOnPrimary = ColorFamily(
        color = black
    ),
    colorSecondary = ColorFamily(
        color = black
    ),
    colorSecondaryVariant = ColorFamily(
        color = black
    ),
    colorOnSecondary = ColorFamily(
        color = black
    ),

    textColorPrimary = ColorFamily(
        color = red_M150
    ),
    textColorSecondary = ColorFamily(
        color = create(0xDA7F88)
    ),
    textColorTertiary = ColorFamily(
        color = create(0xD99CA5)
    ),
    textColorBody = ColorFamily(
        color = create(0xFCFAFB)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xE1C9CC)
    ),
    light_active = ColorFamily(
        color = red_M150
    ),
    light_inactive = ColorFamily(
        color = create(0x535052)
    ),
    light_off = ColorFamily(
        color = purple_M850
    ),
    switchColorOn = ColorFamily(
        color = red_M150
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xFCFAFB)
    ),
    strikethroughColor = ColorFamily(
        color = create(0xD99CA5)
    ),
    circleColor = ColorFamily(
        color = red_M150
    ),
    selectedColor = ColorFamily(
        color = red_M150
    ),
    selectedColor2 = ColorFamily(
        color = create(0x535052)
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = red_M150
    ),
    neutralSelColor = ColorFamily(
        color = create(0xE1C9CC)
    ),
    negativeSelColor = ColorFamily(
        color = purple_M850
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBFD66D7B)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x80D66D7B)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1AD99CA5)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xFCFAFB)
    ),
    progressBarColorEnd = ColorFamily(
        color = red_M150
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xFCFAFB)
    ),
    sanityPieEndColor = ColorFamily(
        color = red_M150
    ),

    splashTextColor = ColorFamily(
        color = red_M150
    ),

    codex1 = ColorFamily(
        color = create(0x33292B)
    ),
    codex3 = ColorFamily(
        color = create(0xE1C9CC)
    ),
    codex2 = ColorFamily(
        color = create(0x492126)
    ),
    codex4 = ColorFamily(
        color = red_M150
    ),
    codex5 = ColorFamily(
        color = purple_M850
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x4033292B)
    ),

    navigationFooterIcon = ColorFamily(
        color = white_M600
    ),
    navigationFooterIconAlt = ColorFamily(
        color = red_M150
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = black_A75
    ),

    inboxNotification = ColorFamily(
        color = create(0xDA7F88)
    ),

    )
val Winner = Community.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0x878D8F)
    ),
    c = ColorFamily(
        color = create(0x3F4346)
    ),
    d = ColorFamily(
        color = create(0xECDFCD)
    ),
    e = ColorFamily(
        color = create(0xDABA80)
    ),
    f = ColorFamily(
        color = orange_M250
    ),
    g = ColorFamily(
        color = create(0x936305)
    ),
    h = ColorFamily(
        color = blue_M850
    ),
    i = ColorFamily(
        color = create(0x090A0B)
    ),
    j = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = orange_M250
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xECDFCD)
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xDABA80)
    ),
    theme_coreColor_3 = ColorFamily(
        color = orange_M250
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x936305)
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = white_M500
    ),
    theme_coreColor_7 = ColorFamily(
        color = blue_M850
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x060C10)
    ),

    /*android:colorBackground = ColorFamily(
    color = create(0x060C10)
    ),
    android:statusBarColor = ColorFamily(
            color = colorOnSecondary
    ),
    android:navigationBarColor = ColorFamily(
    color = create(0x060C10)
    ),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor = ColorFamily(
        color = create(0x060C10)
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x091319)
    ),
    statusBarColor = ColorFamily(
        color = create(0x060C10)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x060C10)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x060C10)
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = create(0x060C10)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x060C10)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x060C10)
    ),
    colorSecondary = ColorFamily(
        color = create(0x060C10)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x060C10)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x060C10)
    ),

    textColorPrimary = ColorFamily(
        color = orange_M250
    ),
    textColorSecondary = ColorFamily(
        color = create(0x936305)
    ),
    textColorTertiary = ColorFamily(
        color = orange_M300
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xDABA80)
    ),
    light_active = ColorFamily(
        color = create(0xDABA80)
    ),
    light_inactive = ColorFamily(
        color = blue_M850
    ),
    light_off = ColorFamily(
        color = white_M600
    ),
    switchColorOn = ColorFamily(
        color = create(0x936305)
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = white
    ),
    strikethroughColor = ColorFamily(
        color = create(0x936305)
    ),
    circleColor = ColorFamily(
        color = orange_M250
    ),
    selectedColor = ColorFamily(
        color = create(0xDABA80)
    ),
    selectedColor2 = ColorFamily(
        color = blue_M850
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),

    positiveSelColor = ColorFamily(
        color = orange_M250
    ),
    neutralSelColor = ColorFamily(
        color = white_M500
    ),
    negativeSelColor = ColorFamily(
        color = create(0xECDFCD)
    ),

    mapPoiFillColor = ColorFamily(
        color = create(0xBF936305)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x80936305)
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xECDFCD)
    ),
    progressBarColorEnd = ColorFamily(
        color = orange_M250
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xECDFCD)
    ),
    sanityPieEndColor = ColorFamily(
        color = orange_M250
    ),

    splashTextColor = ColorFamily(
        color = orange_M250
    ),

    codex1 = ColorFamily(
        color = blue_M850
    ),
    codex3 = ColorFamily(
        color = create(0xBFB5A6)
    ),
    codex2 = ColorFamily(
        color = create(0x0A151C)
    ),
    codex4 = ColorFamily(
        color = orange_M250
    ),
    codex5 = ColorFamily(
        color = create(0x090A0B)
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x40161C1C)
    ),

    navigationFooterIcon = ColorFamily(
        color = white_M600
    ),
    navigationFooterIconAlt = ColorFamily(
        color = orange_M250
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF060C10)
    ),

    inboxNotification = ColorFamily(
        color = create(0xDABA80)
    ),
)
val Artist = Community.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xE3D6F7)
    ),
    c = ColorFamily(
        color = create(0xAA7FFD)
    ),
    d = ColorFamily(
        color = purple_M550
    ),
    e = ColorFamily(
        color = create(0x432878)
    ),
    f = ColorFamily(
        color = create(0xB0ACBA)
    ),
    g = ColorFamily(
        color = create(0x7E7C7E)
    ),
    h = ColorFamily(
        color = blue_M850
    ),
    i = ColorFamily(
        color = create(0x0A0C0C)
    ),
    j = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = create(0xAA7FFD)
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xE3D6F7)
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xAA7FFD)
    ),
    theme_coreColor_3 = ColorFamily(
        color = purple_M550
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0x432878)
    ),
    theme_coreColor_5 = ColorFamily(
        color = white
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0xB0ACBA)
    ),
    theme_coreColor_7 = ColorFamily(
        color = blue_M850
    ),
    theme_coreColor_8 = ColorFamily(
        color = black
    ),
    /*
                android:colorBackground = ColorFamily(
                    color = create(0x0A0C0C)
                    ),
            android:statusBarColor = ColorFamily(
                    color = colorOnSecondary
            ),
            android:navigationBarColor = ColorFamily(
            color = create(0x0A0C0C)
            ),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = white_M400
    ),
    backgroundColor  = ColorFamily(
        color = create(0x0A0C0C)
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x131717)
    ),
    statusBarColor  = ColorFamily(
        color = create(0x0A0C0C)
    ),
    navigationBarColor  = ColorFamily(
        color = create(0x0A0C0C)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x0A0C0C)
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = create(0x0A0C0C)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x0A0C0C)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x0A0C0C)
    ),
    colorSecondary = ColorFamily(
        color = create(0x0A0C0C)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x0A0C0C)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x0A0C0C)
    ),

    textColorPrimary = ColorFamily(
        color = purple_M550
    ),
    textColorSecondary = ColorFamily(
        color = create(0x5B399E)
    ),
    textColorTertiary = ColorFamily(
        color = create(0xAA7FFD)
    ),
    textColorBody = ColorFamily(
        color = white
    ),
    textColorBodyEmphasis = ColorFamily(
        color = create(0xE3D6F7)
    ),
    light_active = ColorFamily(
        color = purple_M550
    ),
    light_inactive = ColorFamily(
        color = create(0x5B399E)
    ),
    light_off = ColorFamily(
        color = white_M600
    ),
    switchColorOn = ColorFamily(
        color = purple_M550
    ),
    buttonFilterOnPrimary = ColorFamily(
        color = create(0xE3D6F7)
    ),
    strikethroughColor = ColorFamily(
        color = create(0x5B399E)
    ),
    circleColor = ColorFamily(
        color = create(0xE3D6F7)
    ),
    selectedColor = ColorFamily(
        color = purple_M550
    ),
    selectedColor2 = ColorFamily(
        color = create(0x5B399E)
    ),
    unselectedColor = ColorFamily(
        color = white_M600
    ),
    positiveSelColor = ColorFamily(
        color = create(0xE3D6F7)
    ),
    neutralSelColor = ColorFamily(
        color = create(0x7E7C7E)
    ),
    negativeSelColor = ColorFamily(
        color = purple_M550
    ),
    mapPoiFillColor = ColorFamily(
        color = create(0xBF5B399E)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x805B399E)
    ),
    mapRoomFillColor = ColorFamily(
        color = yellow_A10
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xE3D6F7)
    ),
    progressBarColorEnd = ColorFamily(
        color = create(0xAA7FFD)
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xE3D6F7)
    ),
    sanityPieEndColor = ColorFamily(
        color = purple_M550
    ),

    splashTextColor = ColorFamily(
        color = create(0xAA7FFD)
    ),

    codex1 = ColorFamily(
        color = create(0x0A0C0C)
    ),
    codex3 = ColorFamily(
        color = create(0xE3D6F7)
    ),
    codex2 = ColorFamily(
        color = create(0x1E1235)
    ),
    codex4 = ColorFamily(
        color = create(0xAA7FFD)
    ),
    codex5 = ColorFamily(
        color = blue_M850
    ),
    codex6_gridBackground = ColorFamily(
        color = create(0x400A0C0C)
    ),

    navigationFooterIcon = ColorFamily(
        color = white_M600
    ),
    navigationFooterIconAlt = ColorFamily(
        color = purple_M550
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF0A0C0C)
    ),

    inboxNotification = ColorFamily(
        color = create(0xE3D6F7)
    ),
)

val StrategemHero = Special.copy(
    a = ColorFamily(
        color = white
    ),
    b = ColorFamily(
        color = create(0xA3A3A3)
    ),
    c = ColorFamily(
        color = create(0xFFD608)
    ),
    d = ColorFamily(
        color = create(0xBA9F36)
    ),
    e = ColorFamily(
        color = create(0xA04907)
    ),
    f = ColorFamily(
        color = create(0xDE2C38)
    ),
    g = ColorFamily(
        color = create(0x700000)
    ),
    h = ColorFamily(
        color = create(0x000B17)
    ),
    i = ColorFamily(
        color = create(0x06223B)
    ),
    j = ColorFamily(
        color = create(0x0D3A63)
    ),
    k = ColorFamily(
        color = create(0x2D70A1)
    ),
    l = ColorFamily(
        color = create(0xB7CBDC)
    ),
    m = ColorFamily(
        color = create(0xE2EDFB)
    ),
    n = ColorFamily(
        color = create(0x16564A)
    ),
    o = ColorFamily(
        color = create(0x95D2C8)
    ),
    p = ColorFamily(
        color = create(0xBBBFB3)
    ),
    q = ColorFamily(
        color = black
    ),

    theme_colorPrimary = ColorFamily(
        color = white
    ),
    theme_coreColor_1 = ColorFamily(
        color = create(0xA3A3A3)
    ),
    theme_coreColor_2 = ColorFamily(
        color = create(0xFFD608)
    ),
    theme_coreColor_3 = ColorFamily(
        color = create(0xA04907)
    ),
    theme_coreColor_4 = ColorFamily(
        color = create(0xDE2C38)
    ),
    theme_coreColor_5 = ColorFamily(
        color = create(0xB7CBDC)
    ),
    theme_coreColor_6 = ColorFamily(
        color = create(0x95D2C8)
    ),
    theme_coreColor_7 = ColorFamily(
        color = create(0x0D3A63)
    ),
    theme_coreColor_8 = ColorFamily(
        color = create(0x000B17)
    ),
    /*
                android:colorBackground = ColorFamily(
                    color = create(0x001021)
                ),
                android:statusBarColor = ColorFamily(
                    color = colorOnSecondary
                ),
                android:navigationBarColor = ColorFamily(
                color = create(0x000B17)
                ),*/
    foregroundColor = ColorFamily(
        color = white
    ),
    middlegroundColor = ColorFamily(
        color = create(0x645956)
    ),
    backgroundColor = ColorFamily(
        color = create(0x000B17)
    ),
    backgroundColorOnBackground  = ColorFamily(
        color = create(0x000E1E)
    ),
    statusBarColor = ColorFamily(
        color = create(0x000B17)
    ),
    navigationBarColor = ColorFamily(
        color = create(0x000B17)
    ),
    windowBackgroundColor = ColorFamily(
        color = create(0x000B17)
    ),
    windowStrokeColor = ColorFamily(
        color = white
    ),
    colorPrimary = ColorFamily(
        color = create(0x000B17)
    ),
    colorPrimaryVariant = ColorFamily(
        color = create(0x000B17)
    ),
    colorOnPrimary = ColorFamily(
        color = create(0x000B17)
    ),
    colorSecondary = ColorFamily(
        color = create(0x000B17)
    ),
    colorSecondaryVariant = ColorFamily(
        color = create(0x000B17)
    ),
    colorOnSecondary = ColorFamily(
        color = create(0x000B17)
    ),

    textColorPrimary = ColorFamily(
        color = create(0xFFD608)
    ),
    textColorSecondary = ColorFamily(
        color = white
    ),
    textColorTertiary = ColorFamily(
        color = create(0x2E5497)
    ),
    textColorBody = ColorFamily(
        color = create(0xA3A3A3)
    ),
    textColorBodyEmphasis = ColorFamily(
        color = white
    ),
    light_active = ColorFamily(
        color = create(0xFFD608)
    ),
    light_inactive = ColorFamily(
        color = create(0x40FFD608)
    ),
    light_off = ColorFamily(
        color = create(0xB7CBDC)
    ),
    switchColorOn = ColorFamily(
        color = create(0xFFD608)
    ),
    switchColorOff = ColorFamily(
        color = create(0xB7CBDC)
    ),
    strikethroughColor = ColorFamily(
        color = create(0xB7CBDC)
    ),
    circleColor = ColorFamily(
        color = create(0xFFD608)
    ),
    selectedColor = ColorFamily(
        color = create(0xFFD608)
    ),
    selectedColor2 = ColorFamily(
        color = create(0xFFD608)
    ),
    unselectedColor = ColorFamily(
        color = create(0xA3A3A3)
    ),
    positiveSelColor = ColorFamily(
        color = create(0xFFD608)
    ),
    neutralSelColor = ColorFamily(
        color = create(0xA3A3A3)
    ),
    negativeSelColor = ColorFamily(
        color = create(0xB7CBDC)
    ),

    mapPoiFillColor = ColorFamily(
        color = create(0xBFE9AD4C)
    ),
    mapRoomBorderColor = ColorFamily(
        color = create(0x94E9AD4C)
    ),
    mapRoomFillColor = ColorFamily(
        color = create(0x1AE9AD4C)
    ),

    progressBarColorStart = ColorFamily(
        color = create(0xB7CBDC)
    ),
    progressBarColorEnd = ColorFamily(
        color = create(0xDE2C38)
    ),
    sanityPieStartColor = ColorFamily(
        color = create(0xB7CBDC)
    ),
    sanityPieEndColor = ColorFamily(
        color = create(0xDE2C38)
    ),

    splashTextColor = ColorFamily(
        color = create(0x447CDE)
    ),

    codex1 = ColorFamily(
        color = create(0x001021)
    ),
    codex3 = ColorFamily(
        color = create(0xB7CBDC)
    ),
    codex2 = ColorFamily(
        color = create(0x001021)
    ),
    codex4 = ColorFamily(
        color = create(0xFFD608)
    ),
    codex5 = ColorFamily(
        color = create(0xA3A3A3)
    ),

    codex6_gridBackground = ColorFamily(
        color = create(0x40001021)
    ),

    navigationFooterIcon = ColorFamily(
        color = white
    ),
    navigationFooterIconAlt = ColorFamily(
        color = create(0xFFD608)
    ),

    backgroundColor_mapviewOverlay = ColorFamily(
        color = create(0xBF001021)
    ),

    inboxNotification = ColorFamily(
        color = create(0x95D2C8)
    ),

    buttonFilterOnPrimary = ColorFamily(
        color = create(0xB7CBDC)
    ),
    buttonBorderOnPrimary = ColorFamily(
        color = white
    ),
    buttonBackgroundOnPrimary = ColorFamily(
        color = create(0xB7CBDC)
    ),

    colorActionPrimary = ColorFamily(
        color = create(0xFFD608)
    ),
)

@Immutable
data class ExtendedColorScheme(

    val colorPrimary: ColorFamily = ColorFamily(), // colorPrimary
    val colorPrimaryVariant: ColorFamily = ColorFamily(), // colorPrimaryVariant
    val colorOnPrimary: ColorFamily = ColorFamily(), // colorOnPrimary
    val colorSecondary: ColorFamily = ColorFamily(), // colorSecondary
    val colorSecondaryVariant: ColorFamily = ColorFamily(), // colorSecondaryVariant
    val colorOnSecondary: ColorFamily = ColorFamily(), // colorOnSecondary

    val textColorPrimary: ColorFamily = ColorFamily(), // textColorPrimary
    val textColorSecondary: ColorFamily = ColorFamily(), // textColorSecondary
    val textColorTertiary: ColorFamily = ColorFamily(), // textColorTertiary
    val textColorBody: ColorFamily = ColorFamily(), // textColorBody
    val textColorBodyEmphasis: ColorFamily = ColorFamily(), // textColorBodyEmphasis
    val splashTextColor: ColorFamily = ColorFamily(), // splashTextColor
    val foregroundColor: ColorFamily = ColorFamily(), // foregroundColor
    val middlegroundColor: ColorFamily = ColorFamily(), // middlegroundColor
    val backgroundColor: ColorFamily = ColorFamily(), // backgroundColor
    val backgroundColor_mapviewOverlay: ColorFamily = ColorFamily(), // backgroundColor_mapviewOverlay
    val backgroundColorOnBackground: ColorFamily = ColorFamily(), // backgroundColorOnBackground
    val navigationBarColor: ColorFamily = backgroundColor, // navigationBarColor
    val statusBarColor: ColorFamily = backgroundColor, // statusBarColor

    val colorActionPrimary: ColorFamily = textColorBody, // colorActionPrimary
    val colorActionSecondary: ColorFamily = ColorFamily(), // colorActionSecondary
    val colorActionTertiary: ColorFamily = ColorFamily(), // colorActionTertiary

    val windowStrokeColor: ColorFamily = ColorFamily(), // windowStrokeColor
    val windowBackgroundColor: ColorFamily = ColorFamily(), // windowBackgroundColor
    val fragmentBackgroundColor: ColorFamily = ColorFamily(), // fragmentBackgroundColor

    val light_active: ColorFamily = ColorFamily(), // light_active
    val light_inactive: ColorFamily = ColorFamily(), // light_inactive
    val light_off: ColorFamily = ColorFamily(), // light_off

    val switchColorOn: ColorFamily = ColorFamily(), // switchColorOn
    val switchColorOff: ColorFamily = ColorFamily(), // switchColorOff
    val inboxNotification: ColorFamily = ColorFamily(), // inboxNotification

    val buttonFilterOnPrimary: ColorFamily = ColorFamily(), // buttonFilterOnPrimary
    val buttonBorderOnPrimary: ColorFamily = ColorFamily(), // buttonBorderOnPrimary
    val buttonBackgroundOnPrimary: ColorFamily = ColorFamily(), // buttonBackgroundOnPrimary

    val progressBarColorStart: ColorFamily = ColorFamily(), // progressBarColorStart
    val progressBarColorEnd: ColorFamily = ColorFamily(), // progressBarColorEnd
    val progressBarColorThumbGradientStart: ColorFamily = ColorFamily(), // progressBarColorThumbGradientStart
    val progressBarColorThumbGradientEnd: ColorFamily = ColorFamily(), // progressBarColorThumbGradientEnd
    val progressBarColorThumbOutline: ColorFamily = ColorFamily(), // progressBarColorThumbOutline

    val selectedColor: ColorFamily = ColorFamily(), // selectedColor
    val selectedColor2: ColorFamily = ColorFamily(), // selectedColor2
    val unselectedColor: ColorFamily = ColorFamily(), // unselectedColor

    val navigationFooterIcon: ColorFamily = unselectedColor, // navigationFooterIcon
    val navigationFooterIconAlt: ColorFamily = ColorFamily(), // navigationFooterIconAlt

    val positiveSelColor: ColorFamily = ColorFamily(), // positiveSelColor
    val negativeSelColor: ColorFamily = ColorFamily(), // negativeSelColor
    val neutralSelColor: ColorFamily = ColorFamily(), // neutralSelColor

    val strikethroughColor: ColorFamily = ColorFamily(), // strikethroughColor
    val circleColor: ColorFamily = ColorFamily(), // circleColor

    val mapPoiFillColor: ColorFamily = ColorFamily(), // mapPoiFillColor
    val mapRoomFillColor: ColorFamily = ColorFamily(), // mapRoomFillColor
    val mapRoomBorderColor: ColorFamily = ColorFamily(), // mapRoomBorderColor

    val theme_colorPrimary: ColorFamily = ColorFamily(), // theme_colorPrimary

    val sanityPieStartColor: ColorFamily = ColorFamily(), // sanityPieStartColor
    val sanityPieEndColor: ColorFamily = ColorFamily(), // sanityPieEndColor
    val sanityHeadBrainColor: ColorFamily = theme_colorPrimary, // sanityHeadBrainColor
    val sanityHeadSkullColor: ColorFamily = ColorFamily(), // sanityHeadSkullColor
    val sanityBorderColor: ColorFamily = ColorFamily(), // sanityBorderColor

    val a: ColorFamily = ColorFamily(), // a
    val b: ColorFamily = ColorFamily(), // b
    val c: ColorFamily = ColorFamily(), // c
    val d: ColorFamily = ColorFamily(), // d
    val e: ColorFamily = ColorFamily(), // e
    val f: ColorFamily = ColorFamily(), // f
    val g: ColorFamily = ColorFamily(), // g
    val h: ColorFamily = ColorFamily(), // h
    val i: ColorFamily = ColorFamily(), // i
    val j: ColorFamily = ColorFamily(), // j
    val k: ColorFamily = ColorFamily(), // k
    val l: ColorFamily = ColorFamily(), // l
    val m: ColorFamily = ColorFamily(), // m
    val n: ColorFamily = ColorFamily(), // n
    val o: ColorFamily = ColorFamily(), // o
    val p: ColorFamily = ColorFamily(), // p
    val q: ColorFamily = ColorFamily(), // q

    val theme_coreColor_1: ColorFamily = ColorFamily(), //  theme_coreColor_1
    val theme_coreColor_2: ColorFamily = ColorFamily(), //  theme_coreColor_2
    val theme_coreColor_3: ColorFamily = ColorFamily(), //  theme_coreColor_3
    val theme_coreColor_4: ColorFamily = ColorFamily(), //  theme_coreColor_4
    val theme_coreColor_5: ColorFamily = ColorFamily(), //  theme_coreColor_5
    val theme_coreColor_6: ColorFamily = ColorFamily(), //  theme_coreColor_6
    val theme_coreColor_7: ColorFamily = ColorFamily(), //  theme_coreColor_7
    val theme_coreColor_8: ColorFamily = ColorFamily(), //  theme_coreColor_8

    val buttonColor: ColorFamily = light_inactive, // buttonColor
    val colorOnButton: ColorFamily = textColorBody, // colorOnButton

    val codex1: ColorFamily = ColorFamily(), // codex1
    val codex2: ColorFamily = ColorFamily(), // codex2
    val codex3: ColorFamily = ColorFamily(), // codex3
    val codex4: ColorFamily = ColorFamily(), // codex4
    val codex5: ColorFamily = ColorFamily(), // codex5

    val codex2_headerBackground: ColorFamily = ColorFamily(), // codex2_headerBackground

    val codex1_itemBackground: ColorFamily = codex1, // codex1_itemBackground
    val codex2_itemBorder: ColorFamily = codex2, // codex2_itemBorder
    val codex3_itemBorder: ColorFamily = codex3, // codex3_itemBorder
    val codex2_tierNormal: ColorFamily = codex2, // codex2_tierNormal
    val codex4_tierAlt: ColorFamily = codex4, // codex4_tierAlt
    val codex3_tierBackground: ColorFamily = codex3, // codex3_tierBackground

    val codex3_groupHeaderText: ColorFamily = codex3, // codex3_groupHeaderText

    val codex3_throbber: ColorFamily = codex3, // codex3_throbber

    val codex1_gh0stBackground: ColorFamily = codex1, // codex1_gh0stBackground
    val codex3_gh0stTextNormal: ColorFamily = codex3, // codex3_gh0stTextNormal
    val codex4_gh0stTextAlt: ColorFamily = codex4, // codex4_gh0stTextAlt
    val codex1_adViewBackground: ColorFamily = codex1, // codex1_adViewBackground

    val codex1_cardArrow: ColorFamily = codex1, // codex1_cardArrow
    val codex3_cardBorder: ColorFamily = codex3, // codex3_cardBorder

    val codex3_navHeaderBackground: ColorFamily = codex3, // codex3_navHeaderBackground
    val codex5_navHeaderText: ColorFamily = codex5, // codex5_navHeaderText
    val codex2_navBackIcon: ColorFamily = codex2, // codex2_navBackIcon

    val codex3_popupHeaderText: ColorFamily = codex3, // codex3_popupHeaderText
    val codex3_popupCloseBackground: ColorFamily = codex3, // codex3_popupCloseBackground
    val codex2_popupCloseIcon: ColorFamily = codex2, // codex2_popupCloseIcon
    val codex3_popupAttrIcons: ColorFamily = codex3, // codex3_popupAttrIcons
    val codex3_popupAttrText: ColorFamily = codex3, // codex3_popupAttrText

    val codex3_other: ColorFamily = codex3, // codex3_other

    val codex5_sel: ColorFamily = codex5, // codex5_sel
    val codex4_unsel: ColorFamily = codex4, // codex4_unsel

    val codex4_border: ColorFamily = codex4, // codex4_border
    val codex4_background: ColorFamily = codex4, // codex4_background

    val codex6_gridBackground: ColorFamily = ColorFamily(), // codex6_gridBackground
    val codex7_gridStroke: ColorFamily = ColorFamily(), // codex7_gridStroke

    val codex3_buttonBackground: ColorFamily = codex3, // codex3_buttonBackground
    val codex5_header: ColorFamily = codex5, // codex5_header

    val actionMenuTextColor: ColorFamily = ColorFamily()
)

@Immutable
data class ColorFamily(
    val color: Color = Color.Unspecified,
    val onColor: Color = Color.Unspecified,
    val colorContainer: Color = Color.Unspecified,
    val onColorContainer: Color = Color.Unspecified
)

val LocalPalette = staticCompositionLocalOf { ExtendedColorScheme() }
