package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.LanguageObject

interface LanguageRepository {
    var _languageList: List<LanguageObject>
    val languageList: List<LanguageObject>

    fun fetchLanguages(context: Context): List<LanguageObject>
}