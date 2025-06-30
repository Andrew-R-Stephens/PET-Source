package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyResources.TypographyType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.AndroidTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.BrickTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.CleanTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.JetBrainsMonoTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.JournalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LongCangTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.NeuchaTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.NewTegominTypography

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