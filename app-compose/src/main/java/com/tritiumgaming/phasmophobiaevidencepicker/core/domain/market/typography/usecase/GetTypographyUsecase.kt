package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

class GetTypographyUsecase(
    private val repository: TypographyRepository
) {

    suspend fun invoke(): List<MarketTypography> {
        val local: List<MarketTypography> = repository.getLocalTypographies().toExternal()
        val remote: List<MarketTypography> = repository.getRemoteTypographies().toExternal()

        val mergedModels = remote.fold(local) { localList, remoteEntity ->
            localList.map { localEntity ->
                if (localEntity.uuid == remoteEntity.uuid) localEntity.copy(
                    uuid = localEntity.uuid,
                    name = remoteEntity.name,
                    group = remoteEntity.group,
                    buyCredits = remoteEntity.buyCredits,
                    priority = remoteEntity.priority,
                    unlocked = remoteEntity.unlocked,
                    typography = remoteEntity.typography ?: ExtendedTypography()
                )
                else localEntity
            }
        }
        Log.d("Typography", "Fetched ${mergedModels.size} typographies")
        mergedModels.forEach {
            Log.d("Typography", "Fetched $it")
        }
        return mergedModels
    }

}