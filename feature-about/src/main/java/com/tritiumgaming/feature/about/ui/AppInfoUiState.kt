package com.tritiumgaming.feature.about.ui

import com.tritiumgaming.shared.data.contributor.model.Contributor

data class AppInfoUiState(
    val contributors: List<Contributor>
)