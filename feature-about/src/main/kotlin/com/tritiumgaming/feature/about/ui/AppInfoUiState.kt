package com.tritiumgaming.feature.about.ui

import com.tritiumgaming.data.contributor.model.Contributor

data class AppInfoUiState(
    val contributors: List<Contributor>
)