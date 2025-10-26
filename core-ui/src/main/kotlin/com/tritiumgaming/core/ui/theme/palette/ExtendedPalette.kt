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

data class ExtendedPalette(

    /*val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val inversePrimary: Color,

    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,

    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,

    val background: Color,
    val onBackground: Color,

    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceTint: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,

    val outline: Color,
    val outlineVariant: Color,
    val scrim: Color,

    val surfaceBright: Color,
    val surfaceDim: Color,

    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainerLowest: Color,

    val secondaryFixed: Color,
    val secondaryFixedDim: Color,
    val onSecondaryFixed: Color,
    val onSecondaryFixedVariant: Color,

    val tertiaryFixed: Color,
    val tertiaryFixedDim: Color,
    val onTertiaryFixed: Color,
    val onTertiaryFixedVariant: Color,*/

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
