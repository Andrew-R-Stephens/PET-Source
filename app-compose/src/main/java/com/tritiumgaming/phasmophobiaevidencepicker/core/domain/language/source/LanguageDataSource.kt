package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.model.NetworkLanguageEntity

interface LanguageDataSource {

    fun fetchLanguages(): List<NetworkLanguageEntity>

}