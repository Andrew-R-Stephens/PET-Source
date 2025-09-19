package com.tritiumgaming.shared.mainmenu.domain.appinfo.usecase

import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.SpecialThanksContributor
import com.tritiumgaming.shared.mainmenu.domain.appinfo.repository.AppInfoRepository

class GetSpecialThanksUseCase(
        private val appInfoRepository: AppInfoRepository
    ) {
        operator fun invoke(): List<SpecialThanksContributor> {
            val result = appInfoRepository.getSpecialThanks()
            result.exceptionOrNull()?.printStackTrace()
            
            return result.getOrDefault(emptyList())
        }
    }
    