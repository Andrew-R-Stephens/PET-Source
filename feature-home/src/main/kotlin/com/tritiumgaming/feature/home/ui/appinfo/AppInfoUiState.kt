package com.tritiumgaming.feature.home.ui.appinfo

import com.tritiumgaming.shared.home.domain.appinfo.model.Contributor

data class AppInfoUiState(
    val contributors: List<Contributor>
)