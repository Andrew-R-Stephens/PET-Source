package com.tritiumgaming.shared.operation.domain.ghostname.model

import com.tritiumgaming.shared.operation.domain.ghostname.mappers.GhostNameResources

class GhostName(
    val name: GhostNameResources.Name,
    val priority: NamePriority = NamePriority.FIRST,
    val gender: Gender = Gender.MALE
) {
    enum class NamePriority {
        FIRST,
        SURNAME
    }

    enum class Gender {
        UNSPECIFIED, MALE, FEMALE
    }
}