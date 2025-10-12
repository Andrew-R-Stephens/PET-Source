package com.tritiumgaming.shared.home.domain.appinfo.usecase

import com.tritiumgaming.shared.home.domain.appinfo.model.Contributor
import com.tritiumgaming.shared.home.domain.appinfo.repository.ContributorRepository

class ContributorUseCase(
        private val appInfoRepository: ContributorRepository
    ) {
        operator fun invoke(): List<Contributor> {
            val result = appInfoRepository.getSpecialThanks()
            result.exceptionOrNull()?.printStackTrace()
            
            return result.getOrDefault(emptyList())
        }
    }
    