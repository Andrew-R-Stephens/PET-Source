package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.MarketAgreement

data class MarketAgreementDto(
    val isAgreementShown: Boolean = false
)

fun MarketAgreementDto.toDomain(): MarketAgreement =
    MarketAgreement(
        isAgreementShown = isAgreementShown
    )

fun MarketAgreement.toNetwork(): MarketAgreementDto =
    MarketAgreementDto(
        isAgreementShown = isAgreementShown
    )
