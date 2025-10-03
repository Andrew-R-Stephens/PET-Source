package com.tritiumgaming.shared.mainmenu.domain.appinfo.usecase

import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.Contributor
import com.tritiumgaming.shared.mainmenu.domain.appinfo.repository.AppInfoRepository

class GetSpecialThanksUseCase(
        private val appInfoRepository: AppInfoRepository
    ) {
        operator fun invoke(): List<Contributor> {
            val result = appInfoRepository.getSpecialThanks()
            result.exceptionOrNull()?.printStackTrace()
            
            return result.getOrDefault(emptyList())
        }
    }
    