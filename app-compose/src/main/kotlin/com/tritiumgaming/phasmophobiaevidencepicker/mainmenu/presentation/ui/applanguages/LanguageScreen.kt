package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.applanguages

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen


@Composable
@Preview
private fun LanguageScreenPreview() {
    LanguageScreen()
}

@Composable
fun LanguageScreen(
    navController: NavController = rememberNavController()
) {

    MainMenuScreen(
        content = {

            LanguageContent(
                navController = navController
            )

        }
    )

}


@Composable
private fun LanguageContent(
    navController: NavController = rememberNavController(),
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel( factory = GlobalPreferencesViewModel.Factory )
) {

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
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

        val columnState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            state = columnState
        ){
            items(
                items = globalPreferencesViewModel.languageList.toList(),
                key = { it.code }
            ) {
                val languageCode = stringResource(it.code)
                LanguageItem(
                    language = it
                ) {
                    globalPreferencesViewModel.setCurrentLanguageCode(languageCode)
                }

            }

        }

    }

}

@Composable
private fun LanguageItem(
    language: LanguageEntity,
    onClick: () -> Unit
) {
    val rememberName by remember{ mutableIntStateOf(language.localizedName) }
    val languageCode = stringResource(language.code)

    val color =
        if(languageCode == AppCompatDelegate.getApplicationLocales()[0]?.language) {
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
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start),
                text = stringResource(rememberName),
                style = LocalTypography.current.secondary.regular.copy(
                    color = color,
                    textAlign = TextAlign.Start
                ),
                maxLines = 1,
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .alpha(.75f),
                text = "( ${stringResource(language.nativeName)} )",
                style = LocalTypography.current.secondary.regular.copy(
                    color = color,
                    textAlign = TextAlign.End
                ),
                maxLines = 1,
                fontSize = 18.sp
            )

        }
    }

}