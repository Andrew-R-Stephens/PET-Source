package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.applanguages

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedStyleType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import java.util.Locale


@Composable
@Preview
private fun LanguageScreenPreview() {
    LanguageScreen()
}

@Composable
fun LanguageScreen(
    navController: NavController = rememberNavController()
) {

    MainMenuScreen (
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

    val languageState by globalPreferencesViewModel.currentLanguageCode.collectAsStateWithLifecycle()
    val tempLanguageState by globalPreferencesViewModel.tempLanguageCode.collectAsStateWithLifecycle()

    var rememberLanguage by remember { mutableStateOf(tempLanguageState) }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        NavigationHeaderComposable(
            params = NavHeaderComposableParams(
                centerTitleRes = R.string.titlescreen_languages_label,
                leftType = PETImageButtonType.CANCEL,
                leftOnClick = {

                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.create(Locale.forLanguageTag(languageState))
                    )

                    navController.popBackStack()

                },
                rightType = PETImageButtonType.CONFIRM,
                rightOnClick = {

                    globalPreferencesViewModel.setCurrentLanguageCode(rememberLanguage)

                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.create(Locale.forLanguageTag(rememberLanguage))
                    )

                    navController.popBackStack()

                }
            )
        )

        LazyColumn(
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(globalPreferencesViewModel.languageList.size) {

                val language = globalPreferencesViewModel.languageList[it]

                LanguageItem(
                    language = language
                ) {

                    globalPreferencesViewModel.setTempLanguageCode(language.abbreviation)
                    rememberLanguage = language.abbreviation

                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.create(Locale.forLanguageTag(rememberLanguage))
                    )

                }

            }

        }

    }

}

@Composable
private fun LanguageItem(
    language: LanguageRepository.LanguageObject,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(true, onClick = {
                onClick()
            })
    ) {

        AutoResizedText(
            containerModifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            textAlign = TextAlign.Center,
            autoResizeStyle = AutoResizedStyleType.SQUEEZE,
            style = LocalTypography.current.secondary.regular,
            text = language.name,
            color = LocalPalette.current.textFamily.body,
        )

    }

}