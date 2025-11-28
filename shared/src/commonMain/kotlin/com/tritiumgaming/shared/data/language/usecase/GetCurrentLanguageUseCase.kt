package com.tritiumgaming.shared.data.language.usecase

class GetCurrentLanguageUseCase(
    private val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {
    operator fun invoke() = repository.getCurrentLanguageCode()
}