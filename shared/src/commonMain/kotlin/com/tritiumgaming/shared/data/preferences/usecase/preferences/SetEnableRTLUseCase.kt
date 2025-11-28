package com.tritiumgaming.shared.data.preferences.usecase.preferences

class SetEnableRTLUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    suspend operator fun invoke(enable: Boolean) =
        repository.setEnableRTL(enable)
}