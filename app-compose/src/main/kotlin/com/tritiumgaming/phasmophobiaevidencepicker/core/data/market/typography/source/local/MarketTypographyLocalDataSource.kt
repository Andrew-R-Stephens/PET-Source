package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.AndroidTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.BrickTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.CleanTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.JetBrainsMonoTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.JournalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypographiesMap
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LongCangTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.NeuchaTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.NewTegominTypography

class MarketTypographyLocalDataSource: MarketTypographyDataSource<Map<String, ExtendedTypography>> {

    override fun getTypographies(): Map<String, ExtendedTypography> = LocalTypographiesMap

}
