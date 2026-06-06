package com.tritiumgaming.feature.investigation.ui.toolbar.operation

import androidx.annotation.FloatRange
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
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.ScrollableToolbar


@Composable
fun OperationToolbar(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onChangeToolbarCategory: (OperationToolbarUiState.Category) -> Unit,
    onReset: () -> Unit,
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
                    onCategoryChange = onChangeToolbarCategory
                )

                AnalyticsButton(
                    stickyContentModifier,
                    category = category,
                    onCategoryChange = onChangeToolbarCategory
                )

            }

        },
        stickyContentEnd = { stickyContentModifier ->

            ResetButton(
                stickyContentModifier,
                onReset = onReset
            )

        },
        scrollContent = { scrollContentModifier ->

            TraitsButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory
            )

            StopwatchButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory
            )

            BpmButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory
            )

        }
    )

}

@Composable
fun OperationToolRail(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onChangeToolbarCategory: (OperationToolbarUiState.Category) -> Unit,
    onReset: () -> Unit,
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
                    onCategoryChange = onChangeToolbarCategory
                )

                AnalyticsButton(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .aspectRatio(1f),
                    category = category,
                    onCategoryChange = onChangeToolbarCategory
                )

            }

        },
        stickyContentEnd = { stickyContentModifier ->

            ResetButton(
                stickyContentModifier
                    .padding(4.dp)
                    .aspectRatio(1f),
                onReset = onReset
            )

        },
        scrollContent = { scrollContentModifier ->

            TraitsButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory
            )

            StopwatchButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory
            )

            BpmButton(
                scrollContentModifier,
                category = category,
                onCategoryChange = onChangeToolbarCategory
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