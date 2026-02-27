package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostListUiItemActions
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions

data class InvestigationUiActions(
    val evidenceListUi: EvidenceListUiActions,
    val ghostListUi: GhostListUiActions,
    val ghostListItemUi: GhostListUiItemActions,
    val toolbarUi: ToolbarUiActions,
    val bpmUi: BpmToolUiActions
)