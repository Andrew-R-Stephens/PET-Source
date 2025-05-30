package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

class GetAvailablePalettesUseCase(
    private val repository: MarketPaletteRepository
) {

    suspend operator fun invoke(): List<MarketPalette> {
        val local: List<MarketPalette> = repository.getLocalPalettes().toExternal()
        val remote: List<MarketPalette> = repository.getRemotePalettes().toExternal()

        val mergedModels = remote.fold(local) { localList, remoteEntity ->
            localList.map { localEntity ->
                if (localEntity.uuid == remoteEntity.uuid) localEntity.copy(
                    uuid = localEntity.uuid,
                    name = remoteEntity.name,
                    group = remoteEntity.group,
                    buyCredits = remoteEntity.buyCredits,
                    priority = remoteEntity.priority,
                    unlocked = remoteEntity.unlocked,
                    palette = remoteEntity.palette ?: ExtendedPalette()
                )
                else localEntity
            }
        }
        Log.d("Palette", "Fetched ${mergedModels.size} palettes")
        mergedModels.forEach {
            Log.d("Palette", "Fetched $it")
        }
        return mergedModels
    }

}