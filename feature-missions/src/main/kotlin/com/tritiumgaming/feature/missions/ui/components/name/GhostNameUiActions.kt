package com.tritiumgaming.feature.missions.ui.components.name

import com.tritiumgaming.shared.data.ghostname.model.GhostName

data class GhostNameUiActions(
    val onSelectFirstName: (ghostName: GhostName) -> Unit,
    val onSelectSurname: (ghostName: GhostName) -> Unit
)
