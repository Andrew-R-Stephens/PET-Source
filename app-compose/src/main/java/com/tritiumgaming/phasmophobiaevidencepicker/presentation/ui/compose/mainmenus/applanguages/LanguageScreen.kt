package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.applanguages

import android.widget.Toast
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
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
        content = { LanguageContent(
            navController = navController
        ) }
    )

}


@Composable
private fun LanguageContent(
    navController: NavController = rememberNavController(),
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel( factory = GlobalPreferencesViewModel.Factory )
) {

    val savedLanguageState = globalPreferencesViewModel.currentLanguageCode.collectAsState()
    var rememberLanguage by remember {
        mutableStateOf(savedLanguageState.value)
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        NavigationHeaderComposable(
            params = NavHeaderComposableParams(
                centerTitleRes = R.string.titlescreen_languages_label,
                leftType = PETImageButtonType.CANCEL,
                leftOnClick = { navController.popBackStack() },
                rightType = PETImageButtonType.CONFIRM,
                rightOnClick = { navController.popBackStack() }
            )
        )

        val context = LocalContext.current

        LazyColumn(
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ){

            items(globalPreferencesViewModel.languageList.size) {

                Row(
                    modifier = Modifier
                        .clickable(true, onClick = {

                            rememberLanguage = globalPreferencesViewModel.languageList[it].abbreviation

                            Toast.makeText(context, rememberLanguage, Toast.LENGTH_SHORT).show()

                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.create(Locale.forLanguageTag(rememberLanguage))
                            )

                        })
                ) {
                    AutoResizedText(
                        containerModifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp),
                        textAlign = TextAlign.Center,
                        autoResizeStyle = AutoResizedStyleType.SQUEEZE,
                        style = LocalTypography.current.secondary.regular,
                        text = globalPreferencesViewModel.languageList[it].name,
                        color = LocalPalette.current.textFamily.body,
                    )
                }

            }

        }

    }

}