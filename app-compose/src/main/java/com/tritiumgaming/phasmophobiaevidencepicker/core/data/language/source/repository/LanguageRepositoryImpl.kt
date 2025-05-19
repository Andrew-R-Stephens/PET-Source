package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageObject
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource

class LanguageRepositoryImpl(
    override val dataSource: LanguageDataSource,
): LanguageRepository {

    override fun getLanguages(): List<LanguageObject> =
        dataSource.fetchLanguages().toExternal()

}
