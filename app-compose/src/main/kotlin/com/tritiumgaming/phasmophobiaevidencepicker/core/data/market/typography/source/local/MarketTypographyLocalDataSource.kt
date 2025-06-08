package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.common.source.MarketLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypographiesMap

class MarketTypographyLocalDataSource:
    MarketLocalDataSource<Map<String, ExtendedTypography>> {

    override fun get(): Result<Map<String, ExtendedTypography>> =
        Result.success(LocalTypographiesMap)

}
