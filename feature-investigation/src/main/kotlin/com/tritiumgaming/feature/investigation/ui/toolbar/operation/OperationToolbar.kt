package com.tritiumgaming.feature.investigation.ui.toolbar.operation

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.ScrollableToolbar


@Composable
fun OperationToolbar(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onChangeToolbarCategory: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit,
    onStartTutorial: () -> Unit,
    containerColor: Color
) {

    ScrollableToolbar(
        modifier = modifier,
        surfaceColor = containerColor,
        stickyContentStart = { stickyContentModifier ->

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                ConfigButton(
                    stickyContentModifier,
                    category = category,
                    onCategoryChange = onChangeToolbarCategory,
                    onReset = onReset
                )

                AnalyticsButton(
                    stickyContentModifier,
                    category = category,
                    onCategoryChange = onChangeToolbarCategory,
                    onReset = onReset
                )

                /*TutorialButton(
                    stickyContentModifier,
                    onStartTutorial = onStartTutorial
                )*/

            }

        },
        stickyContentEnd = { stickyContentModifier ->

            ResetButton(
                stickyContentModifier,
                category = category,
                onReset = onReset
            )

        },
        scrollContent = { scrollContentModifier ->

            TraitsButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory,
                onReset = onReset
            )

            StopwatchButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory,
                onReset = onReset
            )

            BpmButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory,
                onReset = onReset
            )

        }
    )

}

@Composable
fun OperationToolRail(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onChangeToolbarCategory: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit,
    onStartTutorial: () -> Unit,
    containerColor: Color
) {

    InvestigationToolRail(
        modifier = modifier,
        surfaceColor = containerColor,
        stickyContentStart = { stickyContentModifier ->

            Column(
                modifier = stickyContentModifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                ConfigButton(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .aspectRatio(1f),
                    category = category,
                    onCategoryChange = onChangeToolbarCategory,
                    onReset = onReset
                )

                AnalyticsButton(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .aspectRatio(1f),
                    category = category,
                    onCategoryChange = onChangeToolbarCategory,
                    onReset = onReset
                )

                /*TutorialButton(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .aspectRatio(1f),
                    onStartTutorial = onStartTutorial
                )*/

            }

        },
        stickyContentEnd = { stickyContentModifier ->

            ResetButton(
                stickyContentModifier
                    .padding(4.dp)
                    .aspectRatio(1f),
                category = category,
                onReset = onReset
            )

        },
        scrollContent = { scrollContentModifier ->

            TraitsButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory,
                onReset = onReset
            )

            StopwatchButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory,
                onReset = onReset
            )

            BpmButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory,
                onReset = onReset
            )

        }
    )
}

data class OperationToolbarUiState(
    internal val isCollapsed: Boolean = false,
    internal val category: Category = Category.TOOL_CONFIG,
    @param:FloatRange(from = 0.0, to = 1.0)
    internal val openWidth: Float = 0.5f
) {

    enum class Category {
        TOOL_NONE,
        TOOL_CONFIG,
        TOOL_TRAITS,
        TOOL_ANALYZER,
        TOOL_FOOTSTEP,
        TOOL_TIMERS
    }

    enum class ResetOption(@StringRes val title: Int) {
        TOOL_CONFIG(R.string.investigation_label_contract),
        TOOL_TRAITS(R.string.investigation_label_tool_secondary_evidence),
        TOOL_ANALYZER(R.string.investigation_label_tool_operation_details),
        TOOL_FOOTSTEP(R.string.investigation_label_tool_footstep),
        TOOL_TIMERS(R.string.investigation_label_tool_timers),
        JOURNAL(R.string.investigation_label_journal),
        OBJECTIVE_BOARD(R.string.objectives_title_objective_board),
        ALL(R.string.investigation_label_all),
    }

}