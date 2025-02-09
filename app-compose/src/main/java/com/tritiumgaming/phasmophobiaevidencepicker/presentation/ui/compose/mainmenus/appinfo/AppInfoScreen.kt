package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.appinfo

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedStyleType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.DiscordIcon
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.FontUtils
import org.jetbrains.annotations.TestOnly

@Composable
private fun InfoScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        InfoScreen()
    }
}

@Composable
fun InfoScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { InfoContent() }
    )

}

@Composable
private fun InfoContent(
    navController: NavController = rememberNavController(),
    mainMenuViewModel: MainMenuViewModel = viewModel ( factory = MainMenuViewModel.Factory )
) {

    val context = LocalContext.current

    val versionSequence by remember {
        mutableStateOf(
            try { (context.packageManager.getPackageInfo(context.packageName, 0)).versionName }
            catch (_: Exception) { "Unknown" }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        NavigationHeaderComposable(
            params = NavHeaderComposableParams(
                centerTitleRes = R.string.aboutinfo_title_about,
                leftType = PETImageButtonType.BACK,
                leftOnClick = { navController.popBackStack() }
            )
        )

        Column(
            modifier = Modifier
                .padding(all = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            AutoResizedText(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE,
                style = LocalTypography.current.secondary.regular,
                text = "${stringResource(R.string.aboutinfo_version)}: $versionSequence",
                color = LocalPalette.current.textFamily.body,
            )

            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(.35f, false),
                style = LocalTypography.current.secondary.regular,
                fontSize = 24.sp,
                text =
                    AnnotatedString.Companion.fromHtml(
                        FontUtils.replaceHTMLFontColor(
                            stringResource(R.string.aboutinfo_aboutapp_info),
                            "CC3C3C",
                            LocalPalette.current.textFamily.emphasis
                        )
                    ),
                color = LocalPalette.current.textFamily.body,
                textAlign = TextAlign.Center
            )
        }

        AutoResizedText(
            containerModifier = Modifier
                .padding(top = 16.dp)
                .height(36.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            autoResizeStyle = AutoResizedStyleType.SQUEEZE,
            style = LocalTypography.current.primary.regular,
            color = LocalPalette.current.textFamily.primary,
            text = stringResource(R.string.aboutinfo_developerinfo_title)
        )

        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {

            AutoResizedText(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                style = LocalTypography.current.secondary.regular,
                text = stringResource(R.string.aboutinfo_developerinfo_developer),
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE
            )

            AutoResizedText(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                style = LocalTypography.current.secondary.regular,
                text = stringResource(R.string.aboutinfo_developerinfo_devname),
                color = LocalPalette.current.textFamily.body,
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE
            )

            AutoResizedText(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                style = LocalTypography.current.secondary.regular,
                text = stringResource(R.string.aboutinfo_developerinfo_contact),
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE
            )

            AutoResizedText(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                style = LocalTypography.current.secondary.regular,
                text = stringResource(R.string.aboutinfo_developerinfo_address),
                color = LocalPalette.current.textFamily.body,
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE
            )

        }

        VisitDiscordButton()

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {

            AutoResizedText(
                containerModifier = Modifier
                    .padding(top = 16.dp)
                    .height(36.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE,
                style = LocalTypography.current.primary.regular,
                color = LocalPalette.current.textFamily.primary,
                text = stringResource(R.string.aboutinfo_specialthanks_title)
            )

            val specialThanks = mainMenuViewModel.specialThanksList

            LazyColumn(
                modifier = Modifier
                    .weight(.2f, fill = true)

            ) {

                items(specialThanks.size) { index ->

                    AutoResizedText(
                        containerModifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp),
                        text = specialThanks[index],
                        color = LocalPalette.current.textFamily.body,
                        style = LocalTypography.current.quaternary.regular,
                        textAlign = TextAlign.Center,
                        autoResizeStyle = AutoResizedStyleType.SQUEEZE
                    )

                }

            }

        }

    }


}

@Composable
private fun VisitDiscordButton() {

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.aboutinfo_discordInvite)

    OutlinedButton(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(48.dp),
        onClick = {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "https://discord.gg/ $discordInvitation"
                    )
                )
            )
        },
        border = BorderStroke(2.dp, LocalPalette.current.light_inactive),
        shape = RoundedCornerShape(25)
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            DiscordIcon(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight(),
            )

            AutoResizedText(
                containerModifier = Modifier
                    .weight(1f, fill = true),
                text = stringResource(R.string.aboutinfo_joindiscord),
                color = LocalPalette.current.textFamily.body,
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.SQUEEZE
            )

        }

    }

}

@Composable
@Preview
@TestOnly
private fun VisitDiscordButtonPreview() {

    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        VisitDiscordButton()
    }

}
