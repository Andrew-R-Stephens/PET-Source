package com.tritiumgaming.shared.data.market.typography.mappers

import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType

class TypographyResources {

    enum class TypographyType {
        CLASSIC,
        ANDROID,
        JOURNAL,
        BRICK,
        CLEAN,
        LONG_CANG,
        NEW_TEGOMIN,
        NEUCHA,
        JETBRAINS_MONO
    }

}

fun TypographyType.asUuid(): String =
    when (this) {
        TypographyType.CLASSIC -> "c29cJglM92MLWN1RKRyK8qyAD"
        TypographyType.ANDROID -> "8Jk15N2GB6PBopXvmEluU2eoS"
        TypographyType.JOURNAL -> "7q1Nza1o0Nvt16YyNXNkJ590F"
        TypographyType.BRICK -> "3a1vXEZveFEWrf5RdVxTJI6pF"
        TypographyType.CLEAN -> "93Ph8a2SLU3YEupV54TKMKJAO"
        TypographyType.LONG_CANG -> "8UEl0G5HXx119AXh69OeIUPCB"
        TypographyType.NEW_TEGOMIN -> "8rX9hVOyV8eIZmz3ZQaHgrnan"
        TypographyType.NEUCHA -> "DPre8Bscm8Tf3pwyQw7HxBznt"
        TypographyType.JETBRAINS_MONO -> "3vAD75LdzvZN3zBjab5z19zpc"
    }

val LocalDefaultTypography = TypographyType.CLASSIC