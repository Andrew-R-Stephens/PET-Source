package com.tritiumgaming.core.ui.mappers

import com.tritiumgaming.core.ui.theme.type.AndroidTypography
import com.tritiumgaming.core.ui.theme.type.BrickTypography
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.CleanTypography
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.JetBrainsMonoTypography
import com.tritiumgaming.core.ui.theme.type.JournalTypography
import com.tritiumgaming.core.ui.theme.type.LongCangTypography
import com.tritiumgaming.core.ui.theme.type.NeuchaTypography
import com.tritiumgaming.core.ui.theme.type.NewTegominTypography
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType

fun TypographyType.toTypographyResource(): ExtendedTypography =
    when (this) {
        TypographyType.CLASSIC -> ClassicTypography
        TypographyType.ANDROID -> AndroidTypography
        TypographyType.JOURNAL -> JournalTypography
        TypographyType.BRICK -> BrickTypography
        TypographyType.CLEAN -> CleanTypography
        TypographyType.LONG_CANG -> LongCangTypography
        TypographyType.NEW_TEGOMIN -> NewTegominTypography
        TypographyType.NEUCHA -> NeuchaTypography
        TypographyType.JETBRAINS_MONO -> JetBrainsMonoTypography
    }