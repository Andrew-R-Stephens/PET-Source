package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypographiesMap

class MarketTypographyLocalDataSource: MarketTypographyDataSource<Map<String, ExtendedTypography>> {

    override fun getTypographies(): Result<Map<String, ExtendedTypography>> =
        Result.success(LocalTypographiesMap)

}
