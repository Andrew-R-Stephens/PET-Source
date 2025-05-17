package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.dao

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageObject


data class LanguageObjectDao(
    val name: Int,
    val nativeName: Int,
    val abbreviation: String
)

fun LanguageObjectDao.toLanguageObject(): LanguageObject {
    return LanguageObject(
            name = name,
            nativeName = nativeName,
            abbreviation = abbreviation
    )
}