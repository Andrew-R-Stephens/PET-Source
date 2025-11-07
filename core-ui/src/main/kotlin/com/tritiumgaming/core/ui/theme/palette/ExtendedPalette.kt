package com.tritiumgaming.core.ui.theme.palette

import androidx.compose.ui.graphics.Color
import com.tritiumgaming.core.ui.theme.ExtendedTheme
import com.tritiumgaming.core.ui.theme.palette.common.BrandingColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.CodexColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.CoreColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.ExtrasFamily
import com.tritiumgaming.core.ui.theme.palette.common.TextColorFamily
import com.tritiumgaming.core.ui.theme.palette.common.ThemeColorFamily

/** Based on Material Design 3 color roles. Further customization as necessary.
 * @see <a href="https://m3.material.io/styles/color/roles/">Style guide</a>*/

data class ExtendedPalette(

    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val primaryContainer: Color = Color.Unspecified,
    val onPrimaryContainer: Color = Color.Unspecified,

    val secondary: Color = Color.Unspecified,
    val onSecondary: Color = Color.Unspecified,
    val secondaryContainer: Color = Color.Unspecified,
    val onSecondaryContainer: Color = Color.Unspecified,

    val tertiary: Color = Color.Unspecified,
    val onTertiary: Color = Color.Unspecified,
    val tertiaryContainer: Color = Color.Unspecified,
    val onTertiaryContainer: Color = Color.Unspecified,

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

    val codexFamily: CodexColorFamily = CodexColorFamily(),
    val textFamily: TextColorFamily = TextColorFamily(),

    val discordColor: BrandingColorFamily = BrandingColorFamily(),
    val patreonColor: BrandingColorFamily = BrandingColorFamily(),

    //val backgroundColor_mapviewOverlay: Color = Color.Unspecified, // TODO Remove

    val light_active: Color = Color.Unspecified, // TODO Remove
    val light_inactive: Color = Color.Unspecified, // TODO Remove
    val light_off: Color = Color.Unspecified, // TODO Remove

    val progressBarColorStart: Color = Color.Unspecified, // TODO Remove
    val progressBarColorThumbGradientStart: Color = Color.Unspecified, // TODO Remove

    val selectedColor: Color = Color.Unspecified, // TODO Remove
    val selectedColor2: Color = Color.Unspecified, // TODO Remove
    val unselectedColor: Color = Color.Unspecified, // TODO Remove

    val positiveSelColor: Color = Color.Unspecified, // TODO Remove
    val negativeSelColor: Color = Color.Unspecified, // TODO Remove
    val neutralSelColor: Color = Color.Unspecified, // TODO Remove

    //val strikethroughColor: Color = Color.Unspecified, // TODO Remove

    val mapPoiFillColor: Color = Color.Unspecified, // TODO Remove
    val mapRoomFillColor: Color = Color.Unspecified, // TODO Remove
    val mapRoomBorderColor: Color = Color.Unspecified, // TODO Remove

    val sanityPieStartColor: Color = Color.Unspecified, // TODO Remove
    val sanityPieEndColor: Color = Color.Unspecified, // TODO Remove
    val sanityHeadSkullColor: Color = Color.Unspecified, // TODO Remove
    val sanityBorderColor: Color = Color.Unspecified, // TODO Remove

) : ExtendedTheme()

