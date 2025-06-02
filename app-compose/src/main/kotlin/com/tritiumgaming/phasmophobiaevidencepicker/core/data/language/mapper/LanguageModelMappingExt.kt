package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.LanguageDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity

// External is domain layer
// Network is data network layer
// Local is data local layer

// Local to External
fun LanguageDto.toDomain(): LanguageEntity =
    LanguageEntity(
        name = name,
        nativeName = nativeName,
        abbreviation = abbreviation
    )


fun List<LanguageDto>.toDomain(): List<LanguageEntity> =
    map ( LanguageDto::toDomain )