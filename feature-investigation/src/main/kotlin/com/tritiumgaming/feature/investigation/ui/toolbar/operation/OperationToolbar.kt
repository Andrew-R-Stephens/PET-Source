package com.tritiumgaming.feature.investigation.ui.toolbar.operation

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.ScrollableToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions


@Composable
fun OperationToolbar(
    modifier: Modifier = Modifier,
    operationToolbarUiState: OperationToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    containerColor: Color
) {

    ScrollableToolbar(
        modifier = modifier,
        surfaceColor = containerColor,
        stickyContentStart = { modifier ->

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                ConfigButton(
                    modifier,
                    toolbarUiActions,
                    operationToolbarUiState
                )

                AnalyticsButton(
                    modifier,
                    toolbarUiActions,
                    operationToolbarUiState
                )

            }

        },
        stickyContentEnd = { modifier ->

            ResetButton(
                modifier,
                toolbarUiActions
            )

        },
        scrollContent = { modifier ->

            TraitsButton(
                modifier,
                toolbarUiActions,
                operationToolbarUiState
            )

            StopwatchButton(
                modifier,
                toolbarUiActions,
                operationToolbarUiState
            )

            BpmButton(
                modifier,
                toolbarUiActions,
                operationToolbarUiState
            )

        }
    )

}

@Composable
fun OperationToolRail(
    modifier: Modifier = Modifier,
    operationToolbarUiState: OperationToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    containerColor: Color
) {

    InvestigationToolRail(
        modifier = modifier,
        surfaceColor = containerColor,
        stickyContentStart = { modifier ->

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                ConfigButton(
                    modifier,
                    toolbarUiActions,
                    operationToolbarUiState
                )

                AnalyticsButton(
                    modifier,
                    toolbarUiActions,
                    operationToolbarUiState
                )

            }

        },
        stickyContentEnd = { modifier ->

            ResetButton(
                modifier,
                toolbarUiActions)

        },
        scrollContent = { modifier ->

            TraitsButton(
                modifier,
                toolbarUiActions,
                operationToolbarUiState
            )

            StopwatchButton(
                modifier,
                toolbarUiActions,
                operationToolbarUiState
            )

            BpmButton(
                modifier,
                toolbarUiActions,
                operationToolbarUiState
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

}