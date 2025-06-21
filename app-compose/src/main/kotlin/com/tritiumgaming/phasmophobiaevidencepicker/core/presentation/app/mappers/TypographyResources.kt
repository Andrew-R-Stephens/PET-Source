package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyResources.TypographyType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.AndroidTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.JournalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.BrickTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.CleanTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LongCangTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.NewTegominTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.NeuchaTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.JetBrainsMonoTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

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