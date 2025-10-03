package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appinfo

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.window.core.layout.WindowSizeClass
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.common.util.FontUtils
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.BadgeIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.MainMenuScreen
import com.tritiumgaming.shared.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.SpecialThanksContributor
import org.jetbrains.annotations.TestOnly

@Composable
private fun InfoScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        InfoScreen(
            mainMenuViewModel = viewModel(factory = AppInfoViewModel.Factory)
        )
    }
}

@Composable
fun InfoScreen(
    navController: NavController = rememberNavController(),
    mainMenuViewModel: AppInfoViewModel
) {

    MainMenuScreen {

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

            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

            when(deviceConfiguration) {
                DeviceConfiguration.MOBILE_PORTRAIT -> {
                    InfoContentPortrait(
                        contributors = mainMenuViewModel.specialThanksList
                    )
                }
                DeviceConfiguration.MOBILE_LANDSCAPE,
                DeviceConfiguration.TABLET_PORTRAIT,
                DeviceConfiguration.TABLET_LANDSCAPE,
                DeviceConfiguration.DESKTOP -> {
                    InfoContentLandscape(
                        contributors = mainMenuViewModel.specialThanksList
                    )
                }
            }

        }
    }

}

@Composable
private fun ColumnScope.InfoContentPortrait(
    contributors: List<SpecialThanksContributor>
) {

    val context = LocalContext.current

    val versionSequence by remember {
        mutableStateOf(
            try { (context.packageManager.getPackageInfo(context.packageName, 0)).versionName }
            catch (_: Exception) { "Unknown" }
        )
    }

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

    Column(
        modifier = Modifier
            .weight(1f, fill = true)
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            modifier = Modifier
                .padding(all = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier
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

        VisitDiscordButton(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {

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
                text = stringResource(R.string.contact_developerName),
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
                text = stringResource(R.string.contact_developerEmail),
                style = LocalTypography.current.secondary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

        }

        Column(
            modifier = Modifier
                .weight(1f, fill = true),
            verticalArrangement = Arrangement.Center
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
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 10.sp,
                    maxFontSize = 48.sp,
                    stepSize = 1.6.sp
                )
            )

            LazyColumn(
                modifier = Modifier
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
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 10.sp,
                            maxFontSize = 48.sp,
                            stepSize = 1.6.sp
                        )
                    )

                }

            }

        }
    }

}

@Composable
private fun ColumnScope.InfoContentLandscape(
    contributors: List<SpecialThanksContributor>,
) {

    val context = LocalContext.current

    val versionSequence by remember {
        mutableStateOf(
            try { (context.packageManager.getPackageInfo(context.packageName, 0)).versionName }
            catch (_: Exception) { "Unknown" }
        )
    }

    BasicText(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .align(Alignment.CenterHorizontally),
        text = "${stringResource(R.string.aboutinfo_version)} $versionSequence",
        style = LocalTypography.current.secondary.regular.copy(
            color = LocalPalette.current.textFamily.body,
            textAlign = TextAlign.Center
        ),
        maxLines = 1,
        autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
    )

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {

        val rememberScrollStateStart = rememberScrollState()

        Column(
            modifier = Modifier
                .weight(1f, true)
                //.fillMaxHeight()
                .padding(all = 8.dp)
                .verticalScroll(rememberScrollStateStart),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .widthIn(max = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND.dp)
                    .wrapContentHeight()
                    .padding(8.dp),
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

            Spacer(modifier = Modifier
                .height(32.dp)
                .widthIn(max = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND.dp)
            )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .widthIn(max = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND.dp),
                verticalArrangement = Arrangement.Center,
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

                Column(
                    modifier = Modifier
                ) {

                    contributors.forEach { contributor ->

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

        val rememberScrollStateEnd = rememberScrollState()

        Column(
            modifier = Modifier
                .weight(1f, true)
                //.fillMaxHeight()
                .padding(all = 8.dp)
                .verticalScroll(rememberScrollStateEnd),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .widthIn(max = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    text = stringResource(R.string.aboutinfo_developerinfo_developer),
                    style = LocalTypography.current.quaternary.bold.copy(
                        color = LocalPalette.current.textFamily.primary,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    fontSize = 18.sp
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp),
                    text = stringResource(R.string.contact_developerName),
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = LocalPalette.current.textFamily.body,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    text = stringResource(R.string.aboutinfo_developerinfo_contact),
                    style = LocalTypography.current.quaternary.bold.copy(
                        color = LocalPalette.current.textFamily.primary,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    fontSize = 18.sp
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp),
                    text = stringResource(R.string.contact_developerEmail),
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = LocalPalette.current.textFamily.body,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier
                .height(32.dp)
                .widthIn(max = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND.dp)
            )

            VisitDiscordButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .widthIn(max = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }

    }

}

@Composable
private fun VisitDiscordButton(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.link_discordInvite)

    Button(
        modifier = modifier
            .wrapContentWidth()
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
            modifier = Modifier
                .width(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BadgeIcon(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight(),
                baseComponent = {
                    IconResource.DISCORD.ToComposable(
                        colors = IconVectorColors.defaults(
                            fillColor = LocalPalette.current.textFamily.primary,
                            strokeColor = LocalPalette.current.background.color,
                        )
                    )
                }
            ) {
                OpenInNewIcon(
                    colors = IconVectorColors.defaults(
                        fillColor = LocalPalette.current.textFamily.body,
                    )
                )
            }

            Text(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .weight(1f, fill = true),
                text = stringResource(R.string.aboutinfo_discord_join),
                style = LocalTypography.current.primary.regular.copy(
                    fontWeight = FontWeight.Bold,
                    color = LocalPalette.current.background.color,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                fontSize = 24.sp,
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

@Composable
@Preview(showSystemUi = false, apiLevel = 35, device = "spec:parent=pixel_5,orientation=landscape")
@TestOnly
private fun LandscapePreview() {

    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Column {
            InfoContentLandscape(
                listOf(
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA"),
                    SpecialThanksContributor("AAAAAA")
                )
            )
        }
    }

}
