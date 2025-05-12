package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.LanguageObject
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local.LanguageLocalDataSource

class LanguageRepository(
    context: Context,
    private val languageLocalDataSource: LanguageLocalDataSource,
): LanguageRepository {

    override var _languageList = listOf<LanguageObject>()
    override val languageList: List<LanguageObject> = _languageList

    override fun fetchLanguages(context: Context): List<LanguageObject> =
        languageLocalDataSource.fetchLanguages(context)

    init {
        Log.d("LanguagesRepository", "Initializing")

        _languageList = fetchLanguages(context)
    }
}
