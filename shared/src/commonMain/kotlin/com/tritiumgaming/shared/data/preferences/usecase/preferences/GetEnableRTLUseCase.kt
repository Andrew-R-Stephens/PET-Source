package com.tritiumgaming.shared.data.preferences.usecase.preferences

class GetEnableRTLUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    operator fun invoke() = repository.getEnableRTL()
}