package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository.AppInfoRepository

class GetSpecialThanksUseCase(
        private val appInfoRepository: AppInfoRepository
    ) {
        operator fun invoke(): List<String> {
            val result = appInfoRepository.getSpecialThanks()
            result.exceptionOrNull()?.printStackTrace()
            
            return result.getOrDefault(emptyList())
        }
    }
    