package com.tritiumgaming.feature.investigation.ui.toolbar.operation

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.AnalyticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.ConfigIcon
import com.tritiumgaming.core.ui.icon.impl.base.FootprintsIcon
import com.tritiumgaming.core.ui.icon.impl.base.GeneticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.StopwatchIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.collapsebutton.CollapseButton
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.ScrollableToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.annotation.FloatRange


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