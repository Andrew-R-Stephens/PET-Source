package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import android.os.Bundle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.repository.MarketRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.Typography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface TypographyRepository: MarketRepository {

    val defaultTypography: TypographyEntity
    val marketTypographies: MutableMap<String, Typography>
    val localTypographies: Map<String, ExtendedTypography>
    var _allTypographies: MutableStateFlow<MutableMap<String, Typography>>
    var allTypographies: StateFlow<MutableMap<String, Typography>>
    val marketBundles: MutableMap<String, Bundle>

    suspend fun fetchAllTypographies()
    fun unifyRemoteWithLocalTypographies()
    suspend fun fetchAllRemoteTypographies(): List<TypographyEntity>
    suspend fun populateTypographiesRemote()
    fun populateTypographiesLocal()
    fun fetchAllLocalTypographies(): List<Pair<String, ExtendedTypography>>

}