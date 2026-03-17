package com.tritiumgaming.feature.investigation.ui.tool.traits

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.ghosttraits.toStringResource
import com.tritiumgaming.shared.data.investigation.model.TraitValidationType

@Composable
internal fun TraitConfig(
    modifier: Modifier = Modifier,
    state: TraitListUiState,
    actions: TraitListUiActions,
    colors: TraitListItemUiColors = TraitListItemUiColors()
) {


    val categories = state.options.category

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors().copy(
                containerColor = LocalPalette.current.surfaceContainer
            ),
            shape = RoundedCornerShape(8.dp)
        ) {

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { option ->
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable(onClick = {
                                option.data?.let { category ->
                                    actions.onSelectCategory(category)
                                }
                            }),
                        colors = CardDefaults.cardColors().copy(
                            containerColor =
                                if (option.isSelected) LocalPalette.current.surfaceContainerHigh
                                else LocalPalette.current.surfaceContainerLow
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        option.data?.let {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .wrapContentSize(),
                                text = stringResource(it.toStringResource()).uppercase(),
                                color = if (option.isSelected) colors.selectedOnColor
                                else colors.unselectedOnColor,
                                style = LocalTypography.current.quaternary.bold.copy(
                                    textAlign = TextAlign.Center
                                ),
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                        }

                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(
                items = state.list,
                key = { it.ghostTrait.id }
            ) { trait ->
                TraitListItem(
                    state = TraitListItemUiState(
                        item = trait
                    ),
                    actions = TraitListItemUiAction(
                        onToggle = { trait ->
                            actions.onSelectTrait(trait)
                        },
                        onInspect = { }
                    ),
                    colors = colors
                )
            }
        }
    }

}

@Composable
private fun TraitListItem(
    modifier: Modifier = Modifier,
    state: TraitListItemUiState,
    actions: TraitListItemUiAction,
    colors: TraitListItemUiColors = TraitListItemUiColors()
) {
    val selected = state.item.validationType == TraitValidationType.CONFIRMED

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(4.dp)
            .clickable(onClick = {
                actions.onToggle(state.item)
            }),
        colors = CardDefaults.cardColors().copy(
            containerColor = if(selected) colors.selectedColor else colors.unselectedColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {

        Text(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            text = stringResource(state.item.ghostTrait.description.toStringResource()),
            style = LocalTypography.current.quaternary.bold.copy(
                color = if(selected) colors.selectedOnColor else colors.unselectedOnColor,
                textAlign = TextAlign.Start
            ),
            softWrap = true,
            fontSize = 14.sp
        )

    }
}
