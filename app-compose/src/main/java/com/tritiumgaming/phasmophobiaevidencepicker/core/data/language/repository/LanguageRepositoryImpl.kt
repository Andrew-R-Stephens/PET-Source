package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.dao.LanguageObjectDao
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.dao.toLanguageObject
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageObject
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource

class LanguageRepositoryImpl(
    context: Context,
    private val languageLocalDataSource: LanguageDataSource,
): LanguageRepository {

    override var _languageList = listOf<LanguageObject>()
    override val languageList: List<LanguageObject>
        get() = _languageList

    override fun fetchLanguages(context: Context): List<LanguageObjectDao> =
        languageLocalDataSource.fetchLanguages(context)

    init {
        Log.d("Language", "Initializing Repository")

        fetchLanguages(context).forEach { dao ->
            _languageList += dao.toLanguageObject()
        }
    }
}
