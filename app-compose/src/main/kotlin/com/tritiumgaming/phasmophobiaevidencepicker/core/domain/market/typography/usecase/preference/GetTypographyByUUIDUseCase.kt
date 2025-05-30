package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

class GetTypographyByUUIDUseCase {

    operator fun invoke(
        typographies: Map<String, MarketTypography>,
        uuid: String, defaultTypography: ExtendedTypography
    ): ExtendedTypography =
        typographies[uuid]?.typography ?: defaultTypography

}