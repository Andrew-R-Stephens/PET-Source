package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.NetworkBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository.BundleRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BundleManager(
    private val bundleRepository: BundleRepository,
    private val paletteRepository: PaletteRepository,
    private val typographyRepository: TypographyRepository
) {

    private val _bundles = MutableStateFlow(listOf<NetworkBundleEntity>())
    val bundles = _bundles.asStateFlow()
    /*suspend fun setRemoteBundles() {
        _bundles.update { bundleRepository.getRemoteBundles().toLocal() }
    }*/

}
