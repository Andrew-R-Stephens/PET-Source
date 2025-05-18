package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageObject

// External is domain layer
// Network is data network layer
// Local is data local layer

// Local to External
fun NetworkLanguageEntity.toExternal(): LanguageObject {
    return LanguageObject(
        name = name,
        nativeName = nativeName,
        abbreviation = abbreviation
    )
}

fun List<NetworkLanguageEntity>.toExternal(): List<LanguageObject> =
    map ( NetworkLanguageEntity::toExternal )