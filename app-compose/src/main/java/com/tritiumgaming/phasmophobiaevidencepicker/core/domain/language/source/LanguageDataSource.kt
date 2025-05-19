package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.NetworkLanguageEntity

interface LanguageDataSource {

    fun fetchLanguages(): List<NetworkLanguageEntity>

}