package com.tritiumgaming.shared.data.market.typography.mappers

import kotlinx.serialization.Serializable

class TypographyResources {

    @Serializable
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

fun TypographyResources.TypographyType.asUuid(): String =
    when (this) {
        TypographyResources.TypographyType.CLASSIC -> "c29cJglM92MLWN1RKRyK8qyAD"
        TypographyResources.TypographyType.ANDROID -> "8Jk15N2GB6PBopXvmEluU2eoS"
        TypographyResources.TypographyType.JOURNAL -> "7q1Nza1o0Nvt16YyNXNkJ590F"
        TypographyResources.TypographyType.BRICK -> "3a1vXEZveFEWrf5RdVxTJI6pF"
        TypographyResources.TypographyType.CLEAN -> "93Ph8a2SLU3YEupV54TKMKJAO"
        TypographyResources.TypographyType.LONG_CANG -> "8UEl0G5HXx119AXh69OeIUPCB"
        TypographyResources.TypographyType.NEW_TEGOMIN -> "8rX9hVOyV8eIZmz3ZQaHgrnan"
        TypographyResources.TypographyType.NEUCHA -> "DPre8Bscm8Tf3pwyQw7HxBznt"
        TypographyResources.TypographyType.JETBRAINS_MONO -> "3vAD75LdzvZN3zBjab5z19zpc"
    }

val LocalDefaultTypography = TypographyResources.TypographyType.CLASSIC
