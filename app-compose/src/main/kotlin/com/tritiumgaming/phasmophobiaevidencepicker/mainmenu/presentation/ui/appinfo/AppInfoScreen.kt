package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appinfo

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.FontUtils
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common.DiscordIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.mainmenu.MainMenuViewModel
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
    navController: NavController = rememberNavController()
) {

    MainMenuScreen(
        content = { InfoContent(navController) }
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

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                text = "${stringResource(R.string.aboutinfo_version)} $versionSequence",
                style = LocalTypography.current.secondary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
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

        BasicText(
            modifier = Modifier
                .padding(top = 16.dp)
                .height(36.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.aboutinfo_developerinfo_title),
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
        )

        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                text = stringResource(R.string.aboutinfo_developerinfo_developer),
                style = LocalTypography.current.secondary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                text = stringResource(R.string.aboutinfo_developerinfo_devname),
                style = LocalTypography.current.secondary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                text = stringResource(R.string.aboutinfo_developerinfo_contact),
                style = LocalTypography.current.secondary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                text = stringResource(R.string.aboutinfo_developerinfo_address),
                style = LocalTypography.current.secondary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

        }

        VisitDiscordButton(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {

            BasicText(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(36.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.aboutinfo_specialthanks_title),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 10.sp, maxFontSize = 48.sp, stepSize = 1.6.sp)
            )

            val contributors = mainMenuViewModel.specialThanksList

            LazyColumn(
                modifier = Modifier
                    .weight(.2f, fill = true)

            ) {

                items(items = contributors) { contributor ->

                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp),
                        text = contributor.username,
                        style = LocalTypography.current.quaternary.regular.copy(
                            color = LocalPalette.current.textFamily.body,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(minFontSize = 10.sp, maxFontSize = 48.sp, stepSize = 1.6.sp)
                    )

                }

            }

        }

    }


}

@Composable
private fun VisitDiscordButton(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.aboutinfo_discordInvite)

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW, "https://discord.gg/ $discordInvitation".toUri()
                )
            )
        },
        shape = RoundedCornerShape(25),
        colors = ButtonDefaults.buttonColors(
            containerColor = LocalPalette.current.textFamily.primary
        ),
        contentPadding = PaddingValues(16.dp, 4.dp)
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
                colors = listOf(
                    LocalPalette.current.textFamily.primary,
                    LocalPalette.current.background.color
                )
            )

            BasicText(
                modifier = Modifier
                    .weight(1f, fill = true),
                text = stringResource(R.string.aboutinfo_discord_join),
                style = LocalTypography.current.primary.regular.copy(
                    fontWeight = FontWeight.Bold,
                    color = LocalPalette.current.background.color,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 1.6.sp)
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
