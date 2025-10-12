package com.tritiumgaming.feature.home.ui.applanguages

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
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
import com.tritiumgaming.core.ui.common.menus.NavHeaderComposableParams
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.PETImageButtonType
import com.tritiumgaming.core.ui.mappers.toStringResource
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.home.ui.MainMenuScreen
import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity


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

    MainMenuScreen {

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NavigationHeaderComposable(
                params = NavHeaderComposableParams(
                    centerTitleRes = R.string.titlescreen_languages_label,
                    leftType = PETImageButtonType.BACK,
                    leftOnClick = {
                        navController.popBackStack()
                    }
                )
            )

            LanguageContent(
                viewModel = viewModel
            )
        }

    }

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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = columnState
    ){
        items(
            items = viewModel.languageList.toList(),
            key = { it.code }
        ) {
            val languageCode = it.code

            val isSelected = languageCode == currentLanguage
            /*AppCompatDelegate.getApplicationLocales()[0]?.language*/

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

    val color =
        if(isSelected) {
            LocalPalette.current.textFamily.emphasis
        } else {
            LocalPalette.current.textFamily.body
        }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .wrapContentHeight()
            .heightIn(min = 48.dp)
            .padding(vertical = 4.dp)
            .clickable(true, onClick = {
                onClick()
            }),
        shape = RoundedCornerShape(8.dp),
        color = LocalPalette.current.surface.onColor,
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
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = color,
                        textAlign = TextAlign.Start
                    ),
                    maxLines = 1,
                    fontSize = 18.sp
                )
            },
            endComponent = {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .alpha(.75f),
                    text = "( ${stringResource(language.nativeName.toStringResource())} )",
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = color,
                        textAlign = TextAlign.End
                    ),
                    maxLines = 1,
                    fontSize = 18.sp
                )
            }
        )

    }

}