package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.AndroidTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.BrickTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.CleanTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.JetBrainsMonoTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.JournalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LongCangTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.NeuchaTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.NewTegominTypography

class MarketTypographyLocalDataSource: MarketTypographyDataSource<Map<String, ExtendedTypography>> {

    val localTypographiesMap = mapOf(
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

    override suspend fun fetchAll(): Map<String, ExtendedTypography> = localTypographiesMap

}
