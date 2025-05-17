package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.dao.LanguageObjectDao
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageObject

interface LanguageRepository {
    var _languageList: List<LanguageObject>
    val languageList: List<LanguageObject>

    fun fetchLanguages(context: Context): List<LanguageObjectDao>
}