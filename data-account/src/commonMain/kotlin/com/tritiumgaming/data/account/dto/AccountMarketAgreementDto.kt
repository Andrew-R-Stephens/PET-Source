package com.tritiumgaming.data.account.dto

import com.tritiumgaming.shared.data.account.model.AccountMarketAgreement

data class AccountMarketAgreementDto(
    val isAgreementShown: Boolean = false
)

fun AccountMarketAgreementDto.toDomain(): AccountMarketAgreement =
    AccountMarketAgreement(
        isAgreementShown = isAgreementShown
    )

fun AccountMarketAgreement.toNetwork(): AccountMarketAgreementDto =
    AccountMarketAgreementDto(
        isAgreementShown = isAgreementShown
    )
