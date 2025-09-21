package com.tritiumgaming.core.ui.theme.type

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.CuyabraTextStyle
import com.tritiumgaming.core.ui.theme.DefaultTextStyle
import com.tritiumgaming.core.ui.theme.DigitalDreamTextStyle
import com.tritiumgaming.core.ui.theme.EastSeaDokdoTextStyle
import com.tritiumgaming.core.ui.theme.GeoTextStyle
import com.tritiumgaming.core.ui.theme.JetbrainsMonoTextStyle
import com.tritiumgaming.core.ui.theme.LazyDogTextStyle
import com.tritiumgaming.core.ui.theme.LongCangTextStyle
import com.tritiumgaming.core.ui.theme.NeuchaTextStyle
import com.tritiumgaming.core.ui.theme.NewTegominTextStyle
import com.tritiumgaming.core.ui.theme.NorseTextStyle
import com.tritiumgaming.core.ui.theme.RobotoMonoTextStyle
import com.tritiumgaming.core.ui.theme.TypewriterTextStyle
import com.tritiumgaming.core.ui.theme.title_android
import com.tritiumgaming.core.ui.theme.title_brick
import com.tritiumgaming.core.ui.theme.title_classic
import com.tritiumgaming.core.ui.theme.title_clean
import com.tritiumgaming.core.ui.theme.title_jetbrainsmono
import com.tritiumgaming.core.ui.theme.title_journal
import com.tritiumgaming.core.ui.theme.title_longcang
import com.tritiumgaming.core.ui.theme.title_neucha
import com.tritiumgaming.core.ui.theme.title_newtegmon
import com.tritiumgaming.core.ui.theme.type.common.CustomFontFamily
import com.tritiumgaming.core.ui.theme.type.common.ExtrasFamily

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

private val BaseFont = ExtendedTypography(
    extrasFamily = ExtrasFamily(
        title = title_classic
    ),

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
        regular = EastSeaDokdoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        bold = EastSeaDokdoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = CustomFontFamily(
        regular = DigitalDreamTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = DigitalDreamTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        boldNarrow = DigitalDreamTextStyle.copy(
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
        )
    ),

    quaternary = CustomFontFamily(
        regular = TypewriterTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = TypewriterTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    )
)

val ClassicTypography = BaseFont.copy(
    extrasFamily = BaseFont.extrasFamily.copy(),

    primary = BaseFont.primary.copy(),
    secondary = BaseFont.secondary.copy(),
    tertiary = BaseFont.tertiary.copy(),
    quaternary = BaseFont.quaternary.copy()
)

val AndroidTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_android
    ),

    primary = BaseFont.primary.copy(
        regular = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        boldNarrow = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    )
)

val JournalTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_journal
    ),

    primary = BaseFont.primary.copy(
        regular = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        boldNarrow = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    )
)

val BrickTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_brick
    ),

    primary = BaseFont.primary.copy(
        regular = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    )
)

val CleanTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_clean
    ),

    primary = BaseFont.primary.copy(
        regular = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    )
)

val LongCangTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_longcang
    ),

    primary = BaseFont.primary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    quaternary = BaseFont.quaternary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
    )

)

val NewTegominTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_newtegmon
    ),

    primary = BaseFont.primary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    quaternary = BaseFont.quaternary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    )

)

val NeuchaTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_neucha
    ),

    primary = BaseFont.primary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    quaternary = BaseFont.quaternary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
    )
)

val JetBrainsMonoTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_jetbrainsmono
    ),

    primary = BaseFont.primary.copy(
        regular = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Italic
        ),
        bold = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Italic
        ),
        boldNarrow = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Italic
        )
    )
)


val LocalTypography = staticCompositionLocalOf { ExtendedTypography() }

val LocalTypographiesMap = mapOf(
    Pair("c29cJglM92MLWN1RKRyK8qyAD", ClassicTypography),
    Pair("8Jk15N2GB6PBopXvmEluU2eoS", AndroidTypography),
    Pair("7q1Nza1o0Nvt16YyNXNkJ590F", JournalTypography),
    Pair("3a1vXEZveFEWrf5RdVxTJI6pF", BrickTypography),
    Pair("93Ph8a2SLU3YEupV54TKMKJAO", CleanTypography),
    Pair("8UEl0G5HXx119AXh69OeIUPCB", LongCangTypography),
    Pair("8rX9hVOyV8eIZmz3ZQaHgrnan", NewTegominTypography),
    Pair("DPre8Bscm8Tf3pwyQw7HxBznt", NeuchaTypography),
    Pair("3vAD75LdzvZN3zBjab5z19zpc", JetBrainsMonoTypography),
)

val LocalTypographiesList = LocalTypographiesMap.toList()

val LocalDefaultTypography = SimpleUniqueTypography(
    LocalTypographiesList[0].first,
    LocalTypographiesList[0].second
)

data class SimpleUniqueTypography(val uuid: String, val typography: ExtendedTypography)
