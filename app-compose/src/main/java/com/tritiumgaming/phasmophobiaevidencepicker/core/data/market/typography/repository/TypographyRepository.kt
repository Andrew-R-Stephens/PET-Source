package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.MarketRemoteDataSourceInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.ThemeRepository.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalDefaultTypography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.typography.Typography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.typography.TypographyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TypographyRepository(
    context: Context,
    private val networkDataSource: MarketRemoteDataSourceInterface,
    private val localDataSource: TypographyLocalDataSource
): TypographyRepository {

    override val defaultTypography = TypographyEntity(
        uuid = LocalDefaultTypography.uuid,
        name = context.getString(LocalDefaultTypography.typography.extrasFamily.title),
        group = "Default",
        unlocked = true
    )

    override val marketTypographies: MutableMap<String, Typography> = mutableMapOf()
    override val localTypographies = fetchAllLocalTypographies().toMap()

    override var _allTypographies = MutableStateFlow(mutableMapOf<String, Typography>())
    override var allTypographies = _allTypographies.asStateFlow()

    override val marketBundles: MutableMap<String, Bundle> = mutableMapOf()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchAllTypographies()
        }
    }

    override suspend fun fetchAllTypographies() {
        populateTypographiesLocal()
        populateTypographiesRemote()

        unifyRemoteWithLocalTypographies()
    }

    override fun unifyRemoteWithLocalTypographies() {
        marketTypographies.toList().forEach {
            allTypographies.value[it.first] = it.second
        }

        allTypographies.value.toList()
            .sortedBy { it.second.marketTypographyEntity?.priority }
            .forEach {
                val entity = it.second.marketTypographyEntity
                val palette = it.second.typography
                Log.d(
                    "Palette",
                    "${it.first} -> ${entity?.uuid} ${entity?.name} / ${palette.extrasFamily.title}"
                )
            }
    }

    override suspend fun fetchAllRemoteTypographies(): List<TypographyEntity> =
        networkDataSource.fetchAllTypographies()

    override suspend fun populateTypographiesRemote() {
        val remoteEntities: List<TypographyEntity> = fetchAllRemoteTypographies()
        remoteEntities.forEach { remoteEntity ->
            localTypographies[remoteEntity.uuid]?.let { localTypography ->

                val typography = Typography(
                    marketTypographyEntity = remoteEntity,
                    typography = localTypography
                )

                marketTypographies[remoteEntity.uuid] = typography
            }
        }
    }

    override fun populateTypographiesLocal() {
        localTypographies.toList().forEach {
            allTypographies.value[it.first] = Typography(
                uuid = it.first, typography = it.second
            )
        }
    }

    override fun fetchAllLocalTypographies(): List<Pair<String, ExtendedTypography>> = localDataSource.typographies.toList()

    override fun findNextAvailable(
        currentUUID: StateFlow<String>,
        direction: IncrementDirection
    ): String {

        Log.d("Settings", "${currentUUID.value} ${allTypographies.value.size}")
        if(allTypographies.value.isEmpty()) return ""

        val filtered = allTypographies.value
            .filter {
                it.value.marketTypographyEntity == null
                        || it.value.marketTypographyEntity?.unlocked == true
            }

        Log.d("Settings", "Filtered: ${filtered.size}")
        if(filtered.isEmpty()) return ""


        val list = filtered.keys.toList()
        val currentIndex = list.indexOf(currentUUID.value)

        var increment = currentIndex + direction.value
        if(increment >= list.size) increment = 0
        if(increment < 0) increment = list.size - 1

        Log.d("Settings", "Move: $currentIndex $increment $direction")

        return list[increment]
    }

}
