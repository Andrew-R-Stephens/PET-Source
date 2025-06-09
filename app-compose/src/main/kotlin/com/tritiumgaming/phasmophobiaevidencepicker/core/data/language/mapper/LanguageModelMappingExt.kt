package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.LanguageDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity

// External is domain layer
// Network is data network layer
// Local is data local layer

// Local to External
fun LanguageDto.toDomain(): LanguageEntity =
    LanguageEntity(
        localizedName = this@toDomain.localizedName,
        nativeName = nativeName,
        code = code
    )


fun List<LanguageDto>.toDomain(): List<LanguageEntity> =
    map ( LanguageDto::toDomain )