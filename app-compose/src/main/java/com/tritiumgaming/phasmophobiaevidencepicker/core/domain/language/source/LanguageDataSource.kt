package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.dao.LanguageObjectDao
import java.util.Locale

interface LanguageDataSource {
    fun fetchLanguages(context: Context): List<LanguageObjectDao>

    companion object {
        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }
}