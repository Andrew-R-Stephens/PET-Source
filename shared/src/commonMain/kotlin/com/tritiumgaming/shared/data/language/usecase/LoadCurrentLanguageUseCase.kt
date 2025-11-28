package com.tritiumgaming.shared.data.language.usecase

class LoadCurrentLanguageUseCase(
    private val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {
    suspend operator fun invoke() = repository.loadCurrentLanguageCode()
}