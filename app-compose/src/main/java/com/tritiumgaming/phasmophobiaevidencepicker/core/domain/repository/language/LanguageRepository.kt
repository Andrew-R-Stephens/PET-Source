package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.language

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageObject

interface LanguageRepository {
    var _languageList: List<LanguageObject>
    val languageList: List<LanguageObject>

    fun fetchLanguages(context: Context): List<LanguageObject>
}