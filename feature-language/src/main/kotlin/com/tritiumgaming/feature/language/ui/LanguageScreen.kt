package com.tritiumgaming.feature.language.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.labels.DynamicContentRow
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderSideButton
import com.tritiumgaming.core.ui.mappers.toStringResource
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.data.language.model.LanguageEntity

@Composable
@Preview
private fun LanguageScreenPreview() {
    LanguageScreen(
        viewModel = viewModel( factory = LanguageScreenViewModel.Factory ))
}

@Composable
fun LanguageScreen(
    navController: NavController = rememberNavController(),
    viewModel: LanguageScreenViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavigationHeader(
            onLeftClick = { navController.popBackStack() }
        )

        LanguageContent(
            viewModel = viewModel
        )
    }

}

@Composable
private fun NavigationHeader(
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    NavigationHeaderComposable(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 64.dp),
        leftContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
                iconContent = { iconModifier ->
                    Image(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.ic_arrow_60_left),
                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                        contentDescription = ""
                    )
                }
            ) { onLeftClick() }
        },
        rightContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
            )
        },
        centerContent = { outerModifier ->
            NavigationHeaderCenter(
                modifier = outerModifier,
                textContent = { modifier ->
                    BasicText(
                        modifier = modifier,
                        text = stringResource(R.string.titlescreen_languages_label),
                        style = LocalTypography.current.primary.regular.copy(
                            color = LocalPalette.current.primary,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
                    )
                }
            )
        }
    )
}

@Composable
private fun ColumnScope.LanguageContent(
    viewModel: LanguageScreenViewModel
) {
    val currentLanguageState = viewModel.currentLanguageCode.collectAsStateWithLifecycle()
    val currentLanguage = currentLanguageState.value.languageCode

    val columnState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .padding(all = 8.dp)
            .weight(1f)
            .widthIn(max = 600.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = columnState
    ){
        items(
            items = viewModel.languageList.toList(),
            key = { it.code }
        ) {
            val languageCode = it.code

            val isSelected = languageCode == currentLanguage

            LanguageItem(
                language = it,
                isSelected = isSelected
            ) {
                viewModel.setCurrentLanguageCode(languageCode)
            }

        }

    }
}

@Composable
private fun LanguageItem(
    language: LanguageEntity,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val rememberName by remember{
        mutableIntStateOf(language.localizedName.toStringResource()) }

    var textStyleStart: TextStyle
    var textStyleEnd: TextStyle
    var containerColor: Color

    when(isSelected) {
        false -> {
            textStyleStart = LocalTypography.current.quaternary.bold.copy(
                color = LocalPalette.current.onSurface.copy(alpha = .75f),
                textAlign = TextAlign.Start
            )
            textStyleEnd = LocalTypography.current.quaternary.regular.copy(
                color = LocalPalette.current.onSurface,
                textAlign = TextAlign.End
            )
            containerColor = LocalPalette.current.surfaceContainerHighest
        }
        true -> {
            textStyleStart = LocalTypography.current.quaternary.bold.copy(
                color = LocalPalette.current.onTertiaryContainer,
                textAlign = TextAlign.Start
            )
            textStyleEnd = LocalTypography.current.quaternary.bold.copy(
                color = LocalPalette.current.onTertiaryContainer,
                textAlign = TextAlign.End
            )
            containerColor = LocalPalette.current.tertiaryContainer
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .wrapContentHeight()
            .heightIn(min = 48.dp)
            .padding(4.dp)
            .clickable(!isSelected, onClick = { onClick() }),
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        border =
            if(isSelected) null
            else BorderStroke(1.dp,
                LocalPalette.current.outline.copy(alpha=.5f))
    ) {

        DynamicContentRow(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            startComponent = {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start),
                    text = stringResource(rememberName),
                    style = textStyleStart,
                    maxLines = 1,
                    fontSize = 18.sp
                )
            },
            endComponent = {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End),
                    text = "( ${stringResource(language.nativeName.toStringResource())} )",
                    style = textStyleEnd,
                    maxLines = 1,
                    fontSize = 18.sp
                )
            }
        )

    }

}