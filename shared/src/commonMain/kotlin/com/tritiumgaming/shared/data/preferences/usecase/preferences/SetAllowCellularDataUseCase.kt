package com.tritiumgaming.shared.data.preferences.usecase.preferences

class SetAllowCellularDataUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowCellularData(allow)
}