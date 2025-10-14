package com.tritiumgaming.data.account.dto

import com.tritiumgaming.shared.core.domain.user.model.AccountMarketAgreement


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
