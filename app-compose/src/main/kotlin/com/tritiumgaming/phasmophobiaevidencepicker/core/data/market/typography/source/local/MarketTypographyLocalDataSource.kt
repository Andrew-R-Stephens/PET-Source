package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local

import com.tritiumgaming.shared.core.domain.market.common.source.MarketLocalDataSource
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.ANDROID
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.BRICK
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.CLASSIC
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.CLEAN
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.JETBRAINS_MONO
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.JOURNAL
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.LONG_CANG
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.NEUCHA
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType.NEW_TEGOMIN

class MarketTypographyLocalDataSource:
    MarketLocalDataSource<Map<String, TypographyType>> {

    val typographyResources = mapOf(
        Pair("c29cJglM92MLWN1RKRyK8qyAD", CLASSIC),
        Pair("8Jk15N2GB6PBopXvmEluU2eoS", ANDROID),
        Pair("7q1Nza1o0Nvt16YyNXNkJ590F", JOURNAL),
        Pair("3a1vXEZveFEWrf5RdVxTJI6pF", BRICK),
        Pair("93Ph8a2SLU3YEupV54TKMKJAO", CLEAN),
        Pair("8UEl0G5HXx119AXh69OeIUPCB", LONG_CANG),
        Pair("8rX9hVOyV8eIZmz3ZQaHgrnan", NEW_TEGOMIN),
        Pair("DPre8Bscm8Tf3pwyQw7HxBznt", NEUCHA),
        Pair("3vAD75LdzvZN3zBjab5z19zpc", JETBRAINS_MONO),
    )

    override fun get(): Result<Map<String, TypographyType>> =
        Result.success(typographyResources)

}
