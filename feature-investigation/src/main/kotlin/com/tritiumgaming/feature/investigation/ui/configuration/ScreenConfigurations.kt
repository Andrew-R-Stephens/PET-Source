package com.tritiumgaming.feature.investigation.ui.configuration

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.widgets.walkthrough.WalkthroughState
import com.tritiumgaming.core.ui.widgets.walkthrough.walkthroughTarget
import com.tritiumgaming.feature.investigation.ui.components.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.components.toolbar.operation.OperationToolRail
import com.tritiumgaming.feature.investigation.ui.components.toolbar.operation.OperationToolbar
import com.tritiumgaming.feature.investigation.ui.components.toolbar.operation.OperationToolbarUiState

@Composable
internal fun CompactPortraitContent(
    modifier: Modifier = Modifier,
    walkthroughState: WalkthroughState,
    toolbarState: OperationToolbarUiState,
    toolbarActions: ToolbarUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    bottomSheetComponent: @Composable (Modifier) -> Unit,
    journalComponent: @Composable (Modifier) -> Unit
) {

    val toolbarComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .walkthroughTarget(walkthroughState, "toolbar")
                .heightIn(min = 48.dp),
            category = toolbarState.category,
            onChangeToolbarCategory = { category, allowCollapse ->
                toolbarActions.onChangeToolbarCategory(category, allowCollapse)
            },
            onReset = toolbarActions.onReset,
            onStartTutorial = toolbarActions.onStartTutorial,
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        journalComponent(
            Modifier
                .weight(1f, false)
                .padding(horizontal = 8.dp)
        )

        statusBarComponent(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        HorizontalToolbar(
            modifier = Modifier
                .padding(8.dp)
                .walkthroughTarget(walkthroughState, "toolbar"),
            selectBarComponent = { modifier ->
                toolbarComponent(modifier) },
            content = { modifier ->
                bottomSheetComponent(
                    modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .then(
                            if (!toolbarState.isCollapsed)
                                Modifier
                                    .alpha(1f)
                                    .wrapContentHeight()
                            else
                                Modifier
                                    .height(0.dp)
                                    .alpha(0f)
                        )
                )
            }
        )
    }
}

@Composable
internal fun CompactLandscapeContent(
    modifier: Modifier = Modifier,
    walkthroughState: WalkthroughState,
    operationToolbarUiState: OperationToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    journalComponent: @Composable (Modifier) -> Unit,
    sideSheetComponent: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val toolbarContent: @Composable (Modifier) -> Unit = { modifier ->
            OperationToolRail(
                modifier = modifier
                    .walkthroughTarget(walkthroughState, "toolbar")
                    .widthIn(min = 48.dp),
                category = operationToolbarUiState.category,
                onChangeToolbarCategory = { category, allowCollapse ->
                    toolbarUiActions.onChangeToolbarCategory(category, allowCollapse)
                },
                onReset = toolbarUiActions.onReset,
                onStartTutorial = toolbarUiActions.onStartTutorial,
                containerColor = LocalPalette.current.surfaceContainerHigh
            )
        }

        VerticalToolbar(
            modifier = Modifier
                .walkthroughTarget(walkthroughState, "toolbar"),
            selectRailComponent = { modifier ->
                toolbarContent(modifier) },
            content = { modifier ->
                sideSheetComponent(
                    modifier
                        .fillMaxHeight()
                        .animateContentSize()
                        .then(
                            if (!operationToolbarUiState.isCollapsed)
                                Modifier
                                    .fillMaxWidth(.35f)
                                    .widthIn(max = 400.dp)
                                    .alpha(1f)
                            else
                                Modifier
                                    .width(0.dp)
                                    .alpha(0f)
                        )
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(1f, false),
            verticalArrangement = Arrangement.Top
        ) {

            statusBarComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            journalComponent(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

    }
}

@Composable
internal fun ExpandedLandscapeContent(
    modifier: Modifier = Modifier,
    walkthroughState: WalkthroughState,
    operationToolbarUiState: OperationToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    journalComponent: @Composable (Modifier) -> Unit,
    sideSheetComponent: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val toolbarContent: @Composable (Modifier) -> Unit = { modifier ->
            OperationToolRail(
                modifier = modifier
                    .walkthroughTarget(walkthroughState, "toolbar")
                    .widthIn(min = 48.dp),
                category = operationToolbarUiState.category,
                onChangeToolbarCategory = { category, allowCollapse ->
                    toolbarUiActions.onChangeToolbarCategory(category, allowCollapse)
                },
                onReset = toolbarUiActions.onReset,
                onStartTutorial = toolbarUiActions.onStartTutorial,
                containerColor = LocalPalette.current.surfaceContainerHigh
            )
        }

        VerticalToolbar(
            modifier = Modifier,
            selectRailComponent = { modifier ->
                toolbarContent(modifier) },
            content = { modifier ->
                sideSheetComponent(
                    modifier
                        .fillMaxWidth(.35f)
                        .widthIn(max = 400.dp)
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {

            statusBarComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            journalComponent(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

    }
}