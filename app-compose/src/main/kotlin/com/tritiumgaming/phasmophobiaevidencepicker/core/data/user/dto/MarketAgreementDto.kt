package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto

import com.tritiumgaming.shared.core.domain.user.model.AccountMarketAgreement


data class MarketAgreementDto(
    val isAgreementShown: Boolean = false
)

fun MarketAgreementDto.toDomain(): AccountMarketAgreement =
    AccountMarketAgreement(
        isAgreementShown = isAgreementShown
    )

fun AccountMarketAgreement.toNetwork(): MarketAgreementDto =
    MarketAgreementDto(
        isAgreementShown = isAgreementShown
    )
