package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageObject
import java.util.Locale

interface LanguageLocalDataSource {
    fun fetchLanguages(context: Context): List<LanguageObject>

    companion object {
        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }
}
