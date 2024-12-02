package com.TritiumGaming.phasmophobiaevidencepicker.theme.types

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.sp
import com.TritiumGaming.phasmophobiaevidencepicker.theme.Type.Companion.DefaultTextStyle
import com.TritiumGaming.phasmophobiaevidencepicker.theme.Type.Companion.NorseTextStyle

val DefaultFont = Typography(
    displayLarge = DefaultTextStyle.copy(
        fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp
    ),
    displayMedium = DefaultTextStyle.copy(
        fontSize = 45.sp, lineHeight = 52.sp, letterSpacing = 0.sp
    ),
    displaySmall = DefaultTextStyle.copy(
        fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp
    ),
    headlineLarge = DefaultTextStyle.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    headlineMedium = DefaultTextStyle.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    headlineSmall = DefaultTextStyle.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    titleLarge = DefaultTextStyle.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    titleMedium = DefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Medium,
        lineBreak = LineBreak.Heading
    ),
    titleSmall = DefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium,
        lineBreak = LineBreak.Heading
    ),
    labelLarge = DefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = DefaultTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    labelSmall = DefaultTextStyle.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = DefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        lineBreak = LineBreak.Paragraph
    ),
    bodyMedium = DefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        lineBreak = LineBreak.Paragraph
    ),
    bodySmall = DefaultTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        lineBreak = LineBreak.Paragraph
    )
)

private val BaseFont = ExtendedTypography()

val Classic = BaseFont.copy(
    primary = CustomFontFamily(
        regular = NorseTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NorseTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),
    secondary = CustomFontFamily(
        regular = TextStyle(

        ),
        bold = TextStyle(

        ),
        narrow = TextStyle(

        ),
        boldNarrow = TextStyle(

        )
    ),
    tertiary = CustomFontFamily(
        regular = TextStyle(

        ),
        bold = TextStyle(

        ),
        narrow = TextStyle(

        ),
        boldNarrow = TextStyle(

        )
    ),
    quaternary = CustomFontFamily(
        regular = TextStyle(

        ),
        bold = TextStyle(

        ),
        narrow = TextStyle(

        ),
        boldNarrow = TextStyle(

        )
    )
)

/*
<style name="Fonts.Base" parent="Theme.PhasmophobiaEvidenceTool">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/NorseRegular_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/NorseRegular_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/NorseRegular_Manual</item>

<item name="primaryFont_Bold_Auto">@style/NorseBold_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/NorseBold_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/NorseBold_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/EastRegular_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/EastRegular_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/EastRegular_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/EastRegular_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/EastRegular_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/EastRegular_Manual</item>

<!-- Tertiary -->
<item name="tertiaryFont_Regular_Auto">@style/TimeRegular_Auto</item>
<item name="tertiaryFont_Regular_AutoSqueeze">@style/TimeRegular_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/TimeRegular_Manual</item>

<item name="tertiaryFont_Bold_Auto">@style/TimeBold_Auto</item>
<item name="tertiaryFont_Bold_AutoSqueeze">@style/TimeBold_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/TimeBold_Manual</item>

<item name="tertiaryFont_BoldNarrow_Auto">@style/TimeBoldNarrow_Auto</item>
<item name="tertiaryFont_BoldNarrow_AutoSqueeze">@style/TimeBoldNarrow_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/TimeBoldNarrow_Manual</item>

<!-- Quaternary -->
<item name="quaternaryFont_Regular_Auto">@style/TypewriterRegular_Auto</item>
<item name="quaternaryFont_Regular_AutoSqueeze">@style/TypewriterRegular_SqueezeText</item>
<item name="quaternaryFont_Regular_Manual">@style/TypewriterRegular_Manual</item>

<item name="quaternaryFont_Bold_Auto">@style/TypewriterBold_Auto</item>
<item name="quaternaryFont_Bold_AutoSqueeze">@style/TypewriterBold_SqueezeText</item>
<item name="quaternaryFont_Bold_Manual">@style/TypewriterBold_Manual</item>

</style>
*/

val AndroidTypography = BaseFont.copy()
/*
<style name="Android" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/RobotoMono_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/RobotoMono_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/RobotoMono_Manual</item>

<item name="primaryFont_Bold_Auto">@style/RobotoMonoBold_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/RobotoMonoBold_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/RobotoMonoBold_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/RobotoMono_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/RobotoMono_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/RobotoMono_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/RobotoMonoBold_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/RobotoMonoBold_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/RobotoMonoBold_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/RobotoMono</item>
<item name="tertiaryFont_Regular_Squeeze">@style/RobotoMono_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/RobotoMono_Manual</item>

<item name="tertiaryFont_Bold">@style/RobotoMonoBold</item>
<item name="tertiaryFont_Bold_Squeeze">@style/RobotoMonoBold_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/RobotoMonoBold_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/RobotoMonoBold</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/RobotoMonoBold_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/RobotoMonoBold_Manual</item>
-->
</style>

*/

val JournalTypography = BaseFont.copy()
/*
<style name="Journal" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/LazyDog_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/LazyDog_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/LazyDog_Manual</item>

<item name="primaryFont_Bold_Auto">@style/LazyDogBold_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/LazyDogBold_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/LazyDogBold_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/LazyDog_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/LazyDog_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/LazyDog_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/LazyDogBold_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/LazyDogBold_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/LazyDogBold_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/LazyDog</item>
<item name="tertiaryFont_Regular_Squeeze">@style/LazyDog_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/LazyDog_Manual</item>

<item name="tertiaryFont_Bold">@style/LazyDogBold</item>
<item name="tertiaryFont_Bold_Squeeze">@style/LazyDogBold_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/LazyDogBold_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/LazyDogBold</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/LazyDogBold_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/LazyDogBold_Manual</item>
-->
</style>
*/

val BrickTypography = BaseFont.copy()
/*
<style name="Brick" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/BrickRegular_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/BrickRegular_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/BrickRegular_Manual</item>

<item name="primaryFont_Bold_Auto">@style/BrickRegular_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/BrickRegular_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/BrickRegular_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/BrickRegular_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/BrickRegular_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/BrickRegular_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/BrickRegular_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/BrickRegular_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/BrickRegular_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/BrickRegular</item>
<item name="tertiaryFont_Regular_Squeeze">@style/BrickRegular_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/BrickRegular_Manual</item>

<item name="tertiaryFont_Bold">@style/BrickRegular</item>
<item name="tertiaryFont_Bold_Squeeze">@style/BrickRegular_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/BrickRegular_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/BrickRegular</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/BrickRegular_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/BrickRegular_Manual</item>
-->

</style>
*/

val CleanTypography = BaseFont.copy()
/*

<style name="Clean" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/CleanRegular_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/CleanRegular_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/CleanRegular_Manual</item>

<item name="primaryFont_Bold_Auto">@style/CleanRegular_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/CleanRegular_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/CleanRegular_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/CleanRegular_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/CleanRegular_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/CleanRegular_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/CleanRegular_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/CleanRegular_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/CleanRegular_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/CleanRegular</item>
<item name="tertiaryFont_Regular_Squeeze">@style/CleanRegular_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/CleanRegular_Manual</item>

<item name="tertiaryFont_Bold">@style/CleanRegular</item>
<item name="tertiaryFont_Bold_Squeeze">@style/CleanRegular_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/CleanRegular_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/CleanRegular</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/CleanRegular_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/CleanRegular_Manual</item>
-->
</style>
*/

val LongCangTypography = BaseFont.copy()
/*

<style name="LongCang" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/LongCangRegular_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/LongCangRegular_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/LongCangRegular_Manual</item>

<item name="primaryFont_Bold_Auto">@style/LongCangRegular_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/LongCangRegular_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/LongCangRegular_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/LongCangRegular_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/LongCangRegular_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/LongCangRegular_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/LongCangRegular_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/LongCangRegular_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/LongCangRegular_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/LongCangRegular</item>
<item name="tertiaryFont_Regular_Squeeze">@style/LongCangRegular_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/LongCangRegular_Manual</item>

<item name="tertiaryFont_Bold">@style/LongCangRegular</item>
<item name="tertiaryFont_Bold_Squeeze">@style/LongCangRegular_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/LongCangRegular_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/LongCangRegular</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/LongCangRegular_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/LongCangRegular_Manual</item>
-->

<!-- Quaternary -->
<item name="quaternaryFont_Regular_Auto">@style/LongCangRegular_Auto</item>
<item name="quaternaryFont_Regular_AutoSqueeze">@style/LongCangRegular_SqueezeText</item>
<item name="quaternaryFont_Regular_Manual">@style/LongCangRegular_Manual</item>

<item name="quaternaryFont_Bold_Auto">@style/LongCangRegular_Auto</item>
<item name="quaternaryFont_Bold_AutoSqueeze">@style/LongCangRegular_SqueezeText</item>
<item name="quaternaryFont_Bold_Manual">@style/LongCangRegular_Manual</item>

</style>
*/

val NewTegominTypography = BaseFont.copy()
/*

<style name="NewTegomin" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/NewTegominRegular_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/NewTegominRegular_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/NewTegominRegular_Manual</item>

<item name="primaryFont_Bold_Auto">@style/NewTegominRegular_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/NewTegominRegular_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/NewTegominRegular_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/NewTegominRegular_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/NewTegominRegular_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/NewTegominRegular_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/NewTegominRegular_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/NewTegominRegular_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/NewTegominRegular_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/NewTegominRegular</item>
<item name="tertiaryFont_Regular_Squeeze">@style/NewTegominRegular_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/NewTegominRegular_Manual</item>

<item name="tertiaryFont_Bold">@style/NewTegominRegular</item>
<item name="tertiaryFont_Bold_Squeeze">@style/NewTegominRegular_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/NewTegominRegular_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/NewTegominRegular</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/NewTegominRegular_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/NewTegominRegular_Manual</item>
-->

<!-- Quaternary -->
<item name="quaternaryFont_Regular_Auto">@style/NewTegominRegular_Auto</item>
<item name="quaternaryFont_Regular_AutoSqueeze">@style/NewTegominRegular_SqueezeText</item>
<item name="quaternaryFont_Regular_Manual">@style/NewTegominRegular_Manual</item>

<item name="quaternaryFont_Bold_Auto">@style/NewTegominRegular_Auto</item>
<item name="quaternaryFont_Bold_AutoSqueeze">@style/NewTegominRegular_SqueezeText</item>
<item name="quaternaryFont_Bold_Manual">@style/NewTegominRegular_Manual</item>

</style>
*/

val NeuchaTypography = BaseFont.copy()
/*
<style name="Neucha" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/NeuchaRegular_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/NeuchaRegular_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/NeuchaRegular_Manual</item>

<item name="primaryFont_Bold_Auto">@style/NeuchaBold_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/NeuchaBold_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/NeuchaBold_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/NeuchaRegular_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/NeuchaRegular_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/NeuchaRegular_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/NeuchaBold_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/NeuchaBold_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/NeuchaBold_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/NeuchaRegular</item>
<item name="tertiaryFont_Regular_Squeeze">@style/NeuchaRegular_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/NeuchaRegular_Manual</item>

<item name="tertiaryFont_Bold">@style/NeuchaRegular</item>
<item name="tertiaryFont_Bold_Squeeze">@style/NeuchaRegular_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/NeuchaRegular_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/NeuchaRegular</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/NeuchaRegular_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/NeuchaRegular_Manual</item>
-->

<!-- Quaternary -->
<item name="quaternaryFont_Regular_Auto">@style/NeuchaRegular_Auto</item>
<item name="quaternaryFont_Regular_AutoSqueeze">@style/NeuchaRegular_SqueezeText</item>
<item name="quaternaryFont_Regular_Manual">@style/NeuchaRegular_Manual</item>

<item name="quaternaryFont_Bold_Auto">@style/NeuchaBold_Auto</item>
<item name="quaternaryFont_Bold_AutoSqueeze">@style/NeuchaBold_SqueezeText</item>
<item name="quaternaryFont_Bold_Manual">@style/NeuchaBold_Manual</item>
</style>
*/

val JetBrainsMono = BaseFont.copy()
/*
<style name="JetBrainsMono" parent="Fonts.Base">

<!-- Primary -->
<item name="primaryFont_Regular_Auto">@style/JetbrainsMonoRegular_Auto</item>
<item name="primaryFont_Regular_AutoSqueeze">@style/JetbrainsMonoRegular_SqueezeText</item>
<item name="primaryFont_Regular_Manual">@style/JetbrainsMonoRegular_Manual</item>

<item name="primaryFont_Bold_Auto">@style/JetbrainsMonoBold_Auto</item>
<item name="primaryFont_Bold_AutoSqueeze">@style/JetbrainsMonoBold_SqueezeText</item>
<item name="primaryFont_Bold_Manual">@style/JetbrainsMonoBold_Manual</item>

<!-- Secondary -->
<item name="secondaryFont_Regular_Auto">@style/JetbrainsMonoRegular_Auto</item>
<item name="secondaryFont_Regular_AutoSqueeze">@style/JetbrainsMonoRegular_SqueezeText</item>
<item name="secondaryFont_Regular_Manual">@style/JetbrainsMonoRegular_Manual</item>

<item name="secondaryFont_Bold_Auto">@style/JetbrainsMonoBold_Auto</item>
<item name="secondaryFont_Bold_AutoSqueeze">@style/JetbrainsMonoBold_SqueezeText</item>
<item name="secondaryFont_Bold_Manual">@style/JetbrainsMonoBold_Manual</item>

<!-- Tertiary -->
<!--
<item name="tertiaryFont_Regular">@style/JetbrainsMonoItalic</item>
<item name="tertiaryFont_Regular_Squeeze">@style/JetbrainsMonoItalic_SqueezeText</item>
<item name="tertiaryFont_Regular_Manual">@style/JetbrainsMonoItalic_Manual</item>

<item name="tertiaryFont_Bold">@style/JetbrainsMonoItalic</item>
<item name="tertiaryFont_Bold_Squeeze">@style/JetbrainsMonoItalic_SqueezeText</item>
<item name="tertiaryFont_Bold_Manual">@style/JetbrainsMonoItalic_Manual</item>

<item name="tertiaryFont_BoldNarrow">@style/JetbrainsMonoItalic</item>
<item name="tertiaryFont_BoldNarrow_Squeeze">@style/JetbrainsMonoItalic_SqueezeText</item>
<item name="tertiaryFont_BoldNarrow_Manual">@style/JetbrainsMonoItalic_Manual</item>
-->
</style>
*/

@Immutable
data class ExtendedTypography(
    val primary: CustomFontFamily = CustomFontFamily(),
    val secondary: CustomFontFamily = CustomFontFamily(),
    val tertiary: CustomFontFamily = CustomFontFamily(),
    val quaternary: CustomFontFamily = CustomFontFamily()
)

@Immutable
data class CustomFontFamily(
    val regular: TextStyle = TextStyle(),
    val bold: TextStyle = TextStyle(),
    val narrow: TextStyle = TextStyle(),
    val boldNarrow: TextStyle = TextStyle()
)

val LocalCustomFonts = staticCompositionLocalOf { ExtendedTypography() }
