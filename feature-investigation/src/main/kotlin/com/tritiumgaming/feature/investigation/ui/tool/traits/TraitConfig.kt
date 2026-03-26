package com.tritiumgaming.feature.investigation.ui.tool.traits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.JetBrainsMonoTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toGhostTitle
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.ghosttraits.toStringResource
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState.CONFIRM
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState.REJECT
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight.DEFINITIVE
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight.PROBABLE
import com.tritiumgaming.shared.data.investigation.model.TraitValidationType

@Composable
internal fun TraitConfig(
    modifier: Modifier = Modifier,
    state: TraitListUiState,
    actions: TraitListUiActions,
    colors: TraitListItemUiColors = TraitListItemUiColors()
) {

    val traits = state.list
    val uniqueOnly = state.options.uniqueOnly
    val categories = state.options.category

    Column(
        modifier = modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = stringResource(R.string.evidence_trait_label_evidence).uppercase(),
            color = LocalPalette.current.primary,
            style = LocalTypography.current.quaternary.bold.copy(
                textAlign = TextAlign.Start
            ),
            fontSize = 18.sp,
            maxLines = 1
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp)
        ) {

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentHeight()
                        .heightIn(min = 36.dp)
                        .clickable(onClick = {
                            actions.onToggleUniqueOnly()
                        }),
                    color = when(uniqueOnly) {
                        true -> LocalPalette.current.surfaceContainerLow
                        else -> LocalPalette.current.surfaceContainerHigh
                    },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(8.dp)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.evidence_trait_category_unique).uppercase(),
                        color = if (uniqueOnly == true) colors.selectedOnColor else
                            colors.unselectedOnColor,
                        style = LocalTypography.current.quaternary.bold.copy(
                            textAlign = TextAlign.Center
                        ),
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }

                VerticalDivider(
                    modifier = Modifier
                        .height(36.dp),
                    thickness = 2.dp,
                    color = LocalPalette.current.surfaceContainerHighest
                )

                categories.forEach { option ->
                    Surface(
                        modifier = Modifier
                            .wrapContentHeight()
                            .heightIn(min = 36.dp)
                            .clickable(onClick = {
                                option.data?.let { category ->
                                    actions.onSelectCategory(category)
                                }
                            }),
                        color = if (option.state) LocalPalette.current.surfaceContainerLow
                        else LocalPalette.current.surfaceContainerHigh,
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        option.data?.let {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(8.dp)
                                    .align(Alignment.CenterVertically),
                                text = stringResource(it.toStringResource()).uppercase(),
                                color = if (option.state) colors.selectedOnColor
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

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                //verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                item(key = "anchor_spacer") { }

                itemsIndexed(
                    items = traits,
                    key = { index, item -> item.ghostTrait.id }
                ) { index, trait ->

                    TraitListItem(
                        modifier = Modifier
                            .padding(
                                bottom = if (index < traits.lastIndex) 8.dp else 0.dp
                            )
                            .animateItem(),
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
            .clickable(onClick = {
                actions.onToggle(state.item)
            }),
        colors = CardDefaults.cardColors().copy(
            containerColor = if(selected) colors.selectedColor else colors.unselectedColor
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if(selected) colors.selectedColor else Color.Transparent
        )
    ) {

        Column (
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 2.dp),
                text = stringResource(state.item.ghostTrait.description.toStringResource()),
                style = JetBrainsMonoTypography.primary.regular.copy(
                    color = if (selected) colors.selectedOnColor else colors.unselectedOnColor,
                    textAlign = TextAlign.Start
                ),
                softWrap = true,
                fontSize = 14.sp
            )

            FlowRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    modifier = Modifier,
                    text = state.item.ghostTrait.affectedGhosts.map {
                            stringResource(it.toGhostTitle().toStringResource())
                        }.joinToString(", ").uppercase(),
                    style = JetBrainsMonoTypography.primary.bold.copy(
                        color = colors.unselectedOnColor,
                        textAlign = TextAlign.Start
                    ),
                    softWrap = true,
                    fontSize = 11.sp
                )

                val uniqueText = if (state.item.ghostTrait.isUnique) {
                    "${stringResource(R.string.evidence_trait_category_unique)} " } else ""

                val dataText = when(state.item.ghostTrait.weight) {
                     DEFINITIVE -> {
                        when(state.item.ghostTrait.state) {
                            CONFIRM -> stringResource(R.string.evidence_trait_state_confirm)
                            REJECT -> stringResource(R.string.evidence_trait_state_reject)
                        }
                    }
                    PROBABLE -> {
                        when (state.item.ghostTrait.state) {
                            CONFIRM -> stringResource(R.string.evidence_trait_weight_likely)
                            REJECT -> stringResource(R.string.evidence_trait_weight_unlikely)
                        }
                    }
                }

                Text(
                    modifier = Modifier,
                    text = "$uniqueText$dataText",
                    style = JetBrainsMonoTypography.primary.regular.copy(
                        color = colors.unselectedOnColor,
                        textAlign = TextAlign.Start
                    ),
                    softWrap = true,
                    fontSize = 10.sp
                )

            }

        }

    }

}
