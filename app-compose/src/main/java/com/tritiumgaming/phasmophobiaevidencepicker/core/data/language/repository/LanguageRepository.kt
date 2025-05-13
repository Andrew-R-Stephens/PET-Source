package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageObject
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.language.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local.language.LanguageLocalDataSource

class LanguageRepository(
    context: Context,
    private val languageLocalDataSource: LanguageLocalDataSource,
): LanguageRepository {

    override var _languageList = listOf<LanguageObject>()
    override val languageList: List<LanguageObject>
        get() = _languageList

    override fun fetchLanguages(context: Context): List<LanguageObject> =
        languageLocalDataSource.fetchLanguages(context)

    init {
        Log.d("Language", "Initializing Repository")

        _languageList = fetchLanguages(context)
    }
}
