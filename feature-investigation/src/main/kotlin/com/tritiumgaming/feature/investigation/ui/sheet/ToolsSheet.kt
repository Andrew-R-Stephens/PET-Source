package com.tritiumgaming.feature.investigation.ui.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState

private data class CategoryPair(
    val label: String,
    val component: @Composable (Modifier) -> Unit
)

@Composable
internal fun ToolsBottomSheetComponent(
    modifier: Modifier = Modifier,
    toolbarCategory: OperationToolbarUiState.Category,
    configComponent: @Composable (Modifier) -> Unit,
    traitsComponent: @Composable (Modifier) -> Unit,
    analyzerComponent: @Composable (Modifier) -> Unit,
    timersComponent: @Composable (Modifier) -> Unit,
    footstepComponent: @Composable (Modifier) -> Unit,
) {
    val categoryPair: CategoryPair = when (toolbarCategory) {
        OperationToolbarUiState.Category.TOOL_NONE -> {
            CategoryPair("") {}
        }
        OperationToolbarUiState.Category.TOOL_CONFIG -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_contract),
                component = { modifier ->
                    val scrollState = rememberScrollState()
                    configComponent(
                        modifier
                            .padding(top = 8.dp)
                            .height(IntrinsicSize.Max)
                            .verticalScroll(scrollState)
                    )
                }
            )
        }

        OperationToolbarUiState.Category.TOOL_TRAITS -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_secondary_evidence),
                component = { modifier ->
                    traitsComponent(
                        modifier
                            .padding(top = 8.dp)
                            .fillMaxHeight(.5f)
                    )
                }
            )
        }

        OperationToolbarUiState.Category.TOOL_ANALYZER -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_operation_details),
                component = { modifier ->
                    analyzerComponent(
                        modifier
                            .padding(top = 8.dp)
                            .height(IntrinsicSize.Max)
                    )
                }
            )
        }

        OperationToolbarUiState.Category.TOOL_TIMERS -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_timers),
                component = { modifier ->
                    val scrollState = rememberScrollState()
                    timersComponent(
                        modifier
                            .padding(8.dp)
                            .height(IntrinsicSize.Max)
                            .verticalScroll(scrollState)
                    )
                }
            )

        }

        OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_footstep),
                component = { modifier ->
                    val scrollState = rememberScrollState()
                    footstepComponent(
                        modifier
                            .padding(top = 8.dp)
                            .height(IntrinsicSize.Max)
                            .verticalScroll(scrollState)
                    )
                }
            )
        }
    }

    ToolComponent(
        modifier = modifier,
        label = categoryPair.label
    ) {
        categoryPair.component(Modifier)
    }
}

@Composable
internal fun ToolsSideSheetComponent(
    modifier: Modifier = Modifier,
    toolbarCategory: OperationToolbarUiState.Category,
    configComponent: @Composable (Modifier) -> Unit,
    traitsComponent: @Composable (Modifier) -> Unit,
    analyzerComponent: @Composable (Modifier) -> Unit,
    timersComponent: @Composable (Modifier) -> Unit,
    footstepComponent: @Composable (Modifier) -> Unit,
) {
    val categoryPair: CategoryPair = when (toolbarCategory) {
        OperationToolbarUiState.Category.TOOL_NONE -> {
            CategoryPair("") {}
        }
        OperationToolbarUiState.Category.TOOL_CONFIG -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_contract),
                component = { modifier ->
                    val scrollState = rememberScrollState()
                    configComponent(
                        modifier
                            .padding(top = 8.dp)
                            .verticalScroll(scrollState)
                    )
                }
            )
        }

        OperationToolbarUiState.Category.TOOL_TRAITS -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_secondary_evidence),
                component = { modifier ->
                    traitsComponent(
                        modifier
                            .padding(top = 8.dp)
                    )
                }
            )
        }

        OperationToolbarUiState.Category.TOOL_ANALYZER -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_operation_details),
                component = { modifier ->
                    analyzerComponent(
                        modifier
                            .padding(top = 8.dp)
                    )
                }
            )
        }

        OperationToolbarUiState.Category.TOOL_TIMERS -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_timers),
                component = { modifier ->
                    val scrollState = rememberScrollState()
                    timersComponent(
                        modifier
                            .padding(8.dp)
                            .verticalScroll(scrollState)
                    )
                }
            )

        }

        OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
            CategoryPair(
                label = stringResource(R.string.investigation_label_tool_footstep),
                component = { modifier ->
                    val scrollState = rememberScrollState()

                    footstepComponent(
                        modifier
                            .padding(8.dp)
                            .verticalScroll(scrollState)
                    )
                }
            )
        }
    }

    ToolComponent(
        modifier = modifier,
        label = categoryPair.label
    ) {
        categoryPair.component(Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolComponent(
    modifier: Modifier = Modifier,
    label: String,
    component: @Composable ColumnScope.() -> Unit = {},
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = label.uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }

        component()
    }
}
