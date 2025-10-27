package com.tritiumgaming.core.ui.theme.palette

import androidx.compose.ui.graphics.Color
import com.tritiumgaming.core.ui.theme.ExtendedTheme
import com.tritiumgaming.core.ui.theme.palette.common.BrandingColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.CodexColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.CoreColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.ExtrasFamily
import com.tritiumgaming.core.ui.theme.palette.common.M3ColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.SwitchColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.TextColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.ThemeColorFamily

/** Based on Material Design 3 color roles. Further customization as necessary.
 * @see <a href="https://m3.material.io/styles/color/roles/">Style guide</a>*/

data class ExtendedPalette(

//    val primary: Color = Color.Unspecified,
//    val onPrimary: Color = Color.Unspecified,
//    val primaryContainer: Color = Color.Unspecified,
//    val onPrimaryContainer: Color = Color.Unspecified,
//
//    val secondary: Color = Color.Unspecified,
//    val onSecondary: Color = Color.Unspecified,
//    val secondaryContainer: Color = Color.Unspecified,
//    val onSecondaryContainer: Color = Color.Unspecified,
//
//    val tertiary: Color = Color.Unspecified,
//    val onTertiary: Color = Color.Unspecified,
//    val tertiaryContainer: Color = Color.Unspecified,
//    val onTertiaryContainer: Color = Color.Unspecified,

    val error: Color = Color.Unspecified,
    val onError: Color = Color.Unspecified,
    val errorContainer: Color = Color.Unspecified,
    val onErrorContainer: Color = Color.Unspecified,

    val primaryFixed: Color = Color.Unspecified,
    val primaryFixedDim: Color = Color.Unspecified,
    val onPrimaryFixed: Color = Color.Unspecified,
    val onPrimaryFixedVariant: Color = Color.Unspecified,

    val secondaryFixed: Color = Color.Unspecified,
    val secondaryFixedDim: Color = Color.Unspecified,
    val onSecondaryFixed: Color = Color.Unspecified,
    val onSecondaryFixedVariant: Color = Color.Unspecified,

    val tertiaryFixed: Color = Color.Unspecified,
    val tertiaryFixedDim: Color = Color.Unspecified,
    val onTertiaryFixed: Color = Color.Unspecified,
    val onTertiaryFixedVariant: Color = Color.Unspecified,

    val surfaceDim: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val surfaceBright: Color = Color.Unspecified,

    val surfaceContainerLowest: Color = Color.Unspecified,
    val surfaceContainerLow: Color = Color.Unspecified,
    val surfaceContainer: Color = Color.Unspecified,
    val surfaceContainerHigh: Color = Color.Unspecified,
    val surfaceContainerHighest: Color = Color.Unspecified,

    val onSurface: Color = Color.Unspecified,
    val onSurfaceVariant: Color = Color.Unspecified,
    val outline: Color = Color.Unspecified,
    val outlineVariant: Color = Color.Unspecified,

    val inverseSurface: Color = Color.Unspecified,
    val inverseOnSurface: Color = Color.Unspecified,
    val inversePrimary: Color = Color.Unspecified,

    val scrim: Color = Color.Unspecified,
    val shadow: Color = Color.Unspecified,

    val extrasFamily: ExtrasFamily = ExtrasFamily(),

    val themeFamily: ThemeColorFamily = ThemeColorFamily(),
    val coreFamily: CoreColorFamily = CoreColorFamily(),

    val primary: M3ColorFamily = M3ColorFamily(),
    val secondary: M3ColorFamily = M3ColorFamily(),
    val tertiary: M3ColorFamily = M3ColorFamily(),

    //val background: M3ColorFamily = M3ColorFamily(),
    //val surface: M3ColorFamily = M3ColorFamily(),

    val inverse: M3ColorFamily = M3ColorFamily(),

    val codexFamily: CodexColorFamily = CodexColorFamily(),
    val switchTheme: SwitchColorFamily = SwitchColorFamily(),
    val textFamily: TextColorFamily = TextColorFamily(),

    // val outline: Color = Color.Unspecified,

    // val surfaceTint: Color = Color.Unspecified,

    val splashTextColor: Color = Color.Unspecified,

    val backgroundColor_mapviewOverlay: Color = Color.Unspecified,

//    val navigationBarColor: Color = surface.color,
//    val statusBarColor: Color = surface.color,

    // val colorActionPrimary: Color = textFamily.body,
    // val colorActionSecondary: Color = Color.Unspecified,
    // val colorActionTertiary: Color = Color.Unspecified,

    val windowStrokeColor: Color = Color.Unspecified,
    // val windowBackgroundColor: Color = Color.Unspecified,
    // val fragmentBackgroundColor: Color = Color.Unspecified,

    val light_active: Color = Color.Unspecified,
    val light_inactive: Color = Color.Unspecified,
    val light_off: Color = Color.Unspecified,

    val inboxNotification: Color = Color.Unspecified,

    // val buttonFilterOnPrimary: Color = Color.Unspecified,
    // val buttonBorderOnPrimary: Color = Color.Unspecified,
    // val buttonBackgroundOnPrimary: Color = Color.Unspecified,

    val progressBarColorStart: Color = Color.Unspecified,
    val progressBarColorEnd: Color = Color.Unspecified,
    val progressBarColorThumbGradientStart: Color = Color.Unspecified,
    // val progressBarColorThumbGradientEnd: Color = Color.Unspecified,
    val progressBarColorThumbOutline: Color = Color.Unspecified,

    val selectedColor: Color = Color.Unspecified,
    val selectedColor2: Color = Color.Unspecified,
    val unselectedColor: Color = Color.Unspecified,

//     val navigationFooterIcon: Color = unselectedColor,
//     val navigationFooterIconAlt: Color = Color.Unspecified,

    val positiveSelColor: Color = Color.Unspecified,
    val negativeSelColor: Color = Color.Unspecified,
    val neutralSelColor: Color = Color.Unspecified,

    val strikethroughColor: Color = Color.Unspecified,
    // val circleColor: Color = Color.Unspecified,

    val mapPoiFillColor: Color = Color.Unspecified,
    val mapRoomFillColor: Color = Color.Unspecified,
    val mapRoomBorderColor: Color = Color.Unspecified,

    val sanityPieStartColor: Color = Color.Unspecified,
    val sanityPieEndColor: Color = Color.Unspecified,
    val sanityHeadBrainColor: Color = primary.color,
    val sanityHeadSkullColor: Color = Color.Unspecified,
    val sanityBorderColor: Color = Color.Unspecified,

    val buttonColor: Color = light_inactive,
    //val colorOnButton: Color = textFamily.body,

    //val actionMenuTextColor: Color = Color.Unspecified,

    val discordColor: BrandingColorFamily = BrandingColorFamily(),
    val patreonColor: BrandingColorFamily = BrandingColorFamily()

) : ExtendedTheme()


/*
data class ExtendedPalette(

    val extrasFamily: ExtrasFamily = ExtrasFamily(),

    val themeFamily: ThemeColorFamily = ThemeColorFamily(),
    val coreFamily: CoreColorFamily = CoreColorFamily(),

    val primary: M3ColorFamily = M3ColorFamily(),
    val secondary: M3ColorFamily = M3ColorFamily(),
    val tertiary: M3ColorFamily = M3ColorFamily(),

    val background: M3ColorFamily = M3ColorFamily(),
    val surface: M3ColorFamily = M3ColorFamily(),

    val inverse: M3ColorFamily = M3ColorFamily(),
    val error: M3ColorFamily = M3ColorFamily(),

    val codexFamily: CodexColorFamily = CodexColorFamily(),
    val switchTheme: SwitchColorFamily = SwitchColorFamily(),
    val textFamily: TextColorFamily = TextColorFamily(),

    // val outline: Color = Color.Unspecified,

    // val surfaceTint: Color = Color.Unspecified,

    val splashTextColor: Color = Color.Unspecified,

    val backgroundColor_mapviewOverlay: Color = Color.Unspecified,

    val navigationBarColor: Color = surface.color,
    val statusBarColor: Color = surface.color,

    // val colorActionPrimary: Color = textFamily.body,
    // val colorActionSecondary: Color = Color.Unspecified,
    // val colorActionTertiary: Color = Color.Unspecified,

    val windowStrokeColor: Color = Color.Unspecified,
    // val windowBackgroundColor: Color = Color.Unspecified,
    // val fragmentBackgroundColor: Color = Color.Unspecified,

    val light_active: Color = Color.Unspecified,
    val light_inactive: Color = Color.Unspecified,
    val light_off: Color = Color.Unspecified,

    val inboxNotification: Color = Color.Unspecified,

    // val buttonFilterOnPrimary: Color = Color.Unspecified,
    // val buttonBorderOnPrimary: Color = Color.Unspecified,
    // val buttonBackgroundOnPrimary: Color = Color.Unspecified,

    val progressBarColorStart: Color = Color.Unspecified,
    val progressBarColorEnd: Color = Color.Unspecified,
    val progressBarColorThumbGradientStart: Color = Color.Unspecified,
    // val progressBarColorThumbGradientEnd: Color = Color.Unspecified,
    val progressBarColorThumbOutline: Color = Color.Unspecified,

    val selectedColor: Color = Color.Unspecified,
    val selectedColor2: Color = Color.Unspecified,
    val unselectedColor: Color = Color.Unspecified,

    // val navigationFooterIcon: Color = unselectedColor,
    // val navigationFooterIconAlt: Color = Color.Unspecified,

    val positiveSelColor: Color = Color.Unspecified,
    val negativeSelColor: Color = Color.Unspecified,
    val neutralSelColor: Color = Color.Unspecified,

    val strikethroughColor: Color = Color.Unspecified,
    // val circleColor: Color = Color.Unspecified,

    val mapPoiFillColor: Color = Color.Unspecified,
    val mapRoomFillColor: Color = Color.Unspecified,
    val mapRoomBorderColor: Color = Color.Unspecified,

    val sanityPieStartColor: Color = Color.Unspecified,
    val sanityPieEndColor: Color = Color.Unspecified,
    val sanityHeadBrainColor: Color = primary.color,
    val sanityHeadSkullColor: Color = Color.Unspecified,
    val sanityBorderColor: Color = Color.Unspecified,

    val buttonColor: Color = light_inactive,
    //val colorOnButton: Color = textFamily.body,

    //val actionMenuTextColor: Color = Color.Unspecified,

    val discordColor: BrandingColorFamily = BrandingColorFamily(),
    val patreonColor: BrandingColorFamily = BrandingColorFamily()

) : ExtendedTheme()*/
