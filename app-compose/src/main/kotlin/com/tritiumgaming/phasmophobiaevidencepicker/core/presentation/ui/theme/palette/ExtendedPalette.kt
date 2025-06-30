package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette

import androidx.compose.ui.graphics.Color
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.ExtendedTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common.CodexColorFamily
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common.CoreColorFamily
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common.ExtrasFamily
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common.M3ColorFamily
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common.SwitchColorFamily
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common.TextColorFamily
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common.ThemeColorFamily

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
) : ExtendedTheme()