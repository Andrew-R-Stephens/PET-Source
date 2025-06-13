package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapNameUseCase(
        private val simpleMapsRepository: SimpleMapRepository
    ) {
        @StringRes operator fun invoke(index: Int): Int {
            val result = simpleMapsRepository.getMaps()
            
            result.exceptionOrNull()
            
            return result.getOrNull()?.getOrNull(index)?.mapName ?: 0
        }
    }
    