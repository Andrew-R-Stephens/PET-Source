package com.tritiumgaming.feature.home.ui.account

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.indicators.IndeterminateCircularIndicator
import com.tritiumgaming.core.ui.common.labels.DynamicContentRow
import com.tritiumgaming.core.ui.common.menus.NavHeaderComposableParams
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.PETImageButtonType
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalettesMap
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.home.ui.MainMenuScreen
import com.tritiumgaming.feature.home.ui.account.component.AccountBannerComposite
import com.tritiumgaming.feature.home.ui.account.component.AccountBannerExpanded
import com.tritiumgaming.feature.home.ui.account.component.Dialog
import com.tritiumgaming.shared.core.domain.user.model.SignInOptions
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    navController: NavController = rememberNavController(),
    accountViewModel: AccountScreenViewModel
) {

    MainMenuScreen {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                AccountContentPortrait(
                    navController = navController,
                    accountViewModel = accountViewModel
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                AccountContentLandscape(
                    navController = navController,
                    accountViewModel = accountViewModel
                )
            }
        }

    }

}

@Composable
private fun AccountContentPortrait(
    navController: NavController = rememberNavController(),
    accountViewModel: AccountScreenViewModel
) {
    val activity = LocalActivity.current

    var rememberAccount by remember { mutableStateOf(Firebase.auth.currentUser?.uid) }

    var rememberDialog by remember { mutableStateOf(AccountOverviewDialog.NONE) }

    var loadingState = false

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            NavigationHeaderComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                params = NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    centerTitleRes = R.string.account_title,
                    leftOnClick = {
                        navController.popBackStack()
                    }
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                if (rememberAccount == null) {
                    SignInComponent(
                        accountViewModel = accountViewModel,
                        onClick = {
                            loadingState = true
                        },
                        onFailure = {
                            loadingState = false
                        }
                    ) { result ->
                        rememberAccount = Firebase.auth.currentUser?.uid
                        loadingState = false

                        if(result) {
                            Toast.makeText(activity,
                                "${activity?.getString(R.string.alert_account_welcome)} ${Firebase.auth.currentUser?.displayName}",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, activity?.getString(R.string.alert_account_login_failure), Toast.LENGTH_SHORT).show()
                        }

                    }
                } else {
                    AccountComponentPortrait(
                        accountViewModel = accountViewModel,
                        onLogoutClicked = {
                            rememberDialog = AccountOverviewDialog.SIGN_OUT
                        },
                        onDeactivateClicked = {
                            rememberDialog = AccountOverviewDialog.DEACTIVATE_ACCOUNT
                        }
                    )
                }

            }

        }

        when(rememberDialog) {
            AccountOverviewDialog.DEACTIVATE_ACCOUNT ->
                DeactivateAccountDialog(
                    onConfirm = {

                        accountViewModel.deactivateAccount { result ->

                            rememberAccount = Firebase.auth.currentUser?.uid
                            rememberDialog = AccountOverviewDialog.NONE

                            if(result) {
                                Toast.makeText(activity, activity?.getString(R.string.alert_account_remove_success), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, activity?.getString(R.string.alert_account_remove_failure), Toast.LENGTH_SHORT).show()
                            }

                        }

                    },
                    onCancel = {
                        rememberDialog = AccountOverviewDialog.NONE
                    }
            )
            AccountOverviewDialog.SIGN_OUT ->
                LogoutDialog(
                    onConfirm = {
                        accountViewModel.signOutAccount { value ->

                            rememberAccount = Firebase.auth.currentUser?.uid
                            rememberDialog = AccountOverviewDialog.NONE

                            if(value) {
                                Toast.makeText(activity, activity?.getString(R.string.alert_account_logout_success), Toast.LENGTH_SHORT).show()
                            }

                        }

                    },
                    onCancel = {
                        rememberDialog = AccountOverviewDialog.NONE
                    }
                )
            AccountOverviewDialog.NONE -> {}
        }

        IndeterminateCircularIndicator(
            color1 = LocalPalette.current.textFamily.body,
            color2 = LocalPalette.current.surface.onColor,
            isLoading = loadingState
        )

    }
}

@Composable
private fun AccountContentLandscape(
    navController: NavController = rememberNavController(),
    accountViewModel: AccountScreenViewModel
) {
    val activity = LocalActivity.current

    var rememberAccount by remember { mutableStateOf(Firebase.auth.currentUser?.uid) }

    var rememberDialog by remember { mutableStateOf(AccountOverviewDialog.NONE) }

    var loadingState = false

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            NavigationHeaderComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                params = NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    centerTitleRes = R.string.account_title,
                    leftOnClick = {
                        navController.popBackStack()
                    }
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (rememberAccount == null) {
                    SignInComponent(
                        accountViewModel = accountViewModel,
                        onClick = {
                            loadingState = true
                        },
                        onFailure = {
                            loadingState = false
                        }
                    ) { result ->
                        rememberAccount = Firebase.auth.currentUser?.uid
                        loadingState = false

                        if(result) {
                            Toast.makeText(activity,
                                "${activity?.getString(R.string.alert_account_welcome)} ${Firebase.auth.currentUser?.displayName}",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, activity?.getString(R.string.alert_account_login_failure), Toast.LENGTH_SHORT).show()
                        }

                    }
                } else {
                    AccountComponentLandscape(
                        accountViewModel = accountViewModel,
                        onLogoutClicked = {
                            rememberDialog = AccountOverviewDialog.SIGN_OUT
                        },
                        onDeactivateClicked = {
                            rememberDialog = AccountOverviewDialog.DEACTIVATE_ACCOUNT
                        }
                    )
                }

            }

        }

        when(rememberDialog) {
            AccountOverviewDialog.DEACTIVATE_ACCOUNT ->
                DeactivateAccountDialog(
                    onConfirm = {

                        accountViewModel.deactivateAccount { result ->

                            rememberAccount = Firebase.auth.currentUser?.uid
                            rememberDialog = AccountOverviewDialog.NONE

                            if(result) {
                                Toast.makeText(activity, activity?.getString(R.string.alert_account_remove_success), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, activity?.getString(R.string.alert_account_remove_failure), Toast.LENGTH_SHORT).show()
                            }

                        }

                    },
                    onCancel = {
                        rememberDialog = AccountOverviewDialog.NONE
                    }
                )
            AccountOverviewDialog.SIGN_OUT ->
                LogoutDialog(
                    onConfirm = {
                        accountViewModel.signOutAccount { value ->

                            rememberAccount = Firebase.auth.currentUser?.uid
                            rememberDialog = AccountOverviewDialog.NONE

                            if(value) {
                                Toast.makeText(activity, activity?.getString(R.string.alert_account_logout_success), Toast.LENGTH_SHORT).show()
                            }

                        }

                    },
                    onCancel = {
                        rememberDialog = AccountOverviewDialog.NONE
                    }
                )
            AccountOverviewDialog.NONE -> {}
        }

        IndeterminateCircularIndicator(
            color1 = LocalPalette.current.textFamily.body,
            color2 = LocalPalette.current.surface.onColor,
            isLoading = loadingState
        )

    }
}

@Composable
private fun AccountComponentPortrait(
    accountViewModel: AccountScreenViewModel,
    onLogoutClicked: () -> Unit = {},
    onDeactivateClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AccountDetailsPortraitComponent(
            accountViewModel = accountViewModel,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SignOutComponent(
            onLogoutClicked = {
                onLogoutClicked()
            },
            onDeactivateClicked = {
                onDeactivateClicked()
            },
        )

        Column {
            UnlockHistoryPalettesComponent(
                accountViewModel = accountViewModel
            )
        }
    }
}

@Composable
private fun AccountComponentLandscape(
    accountViewModel: AccountScreenViewModel,
    onLogoutClicked: () -> Unit = {},
    onDeactivateClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {

        AccountDetailsLandscapeComponent(
            modifier = Modifier
                .weight(1f, fill = true),
            accountViewModel = accountViewModel,
        )

        SignOutComponent(
            modifier = Modifier
                .weight(1f, fill = true)
                .align(Alignment.CenterVertically),
            onLogoutClicked = {
                onLogoutClicked()
            },
            onDeactivateClicked = {
                onDeactivateClicked()
            }
        )

    }
}

@Composable
private fun AccountDetailsPortraitComponent(
    modifier: Modifier = Modifier,
    accountViewModel: AccountScreenViewModel = viewModel(factory = AccountScreenViewModel.Factory),
) {

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val accountUiState = accountViewModel.accountCreditsUiState.collectAsStateWithLifecycle()

        AccountBannerExpanded(
            credits = accountUiState.value.earnedCredits
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(R.string.account_label_information),
            style = LocalTypography.current.quaternary.bold,
            color = LocalPalette.current.textFamily.body,
            fontSize = 18.sp,
            maxLines = 1,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_name)}:",
            value = Firebase.auth.currentUser?.displayName ?: ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_email)}:",
            value = Firebase.auth.currentUser?.email ?: ""
        )

    }

}

@Composable
private fun AccountDetailsLandscapeComponent(
    modifier: Modifier = Modifier,
    accountViewModel: AccountScreenViewModel = viewModel(factory = AccountScreenViewModel.Factory),
) {

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val accountUiState = accountViewModel.accountCreditsUiState.collectAsStateWithLifecycle()

        AccountBannerComposite(
            credits = accountUiState.value.earnedCredits
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(R.string.account_label_information),
            style = LocalTypography.current.quaternary.bold,
            color = LocalPalette.current.textFamily.body,
            fontSize = 18.sp,
            maxLines = 1,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_name)}:",
            value = Firebase.auth.currentUser?.displayName ?: ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_email)}:",
            value = Firebase.auth.currentUser?.email ?: ""
        )

    }

}

@Composable
private fun LabeledValue(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = ""
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(8.dp),
    ) {
        DynamicContentRow(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            startComponent = {
                Text(
                    text = title,
                    style = LocalTypography.current.quaternary.bold,
                    color = LocalPalette.current.textFamily.body,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
            },
            endComponent = {
                Text(
                    text = value,
                    style = LocalTypography.current.quaternary.bold,
                    color = LocalPalette.current.textFamily.body,
                    fontSize = 18.sp,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .basicMarquee(
                            animationMode = MarqueeAnimationMode.Immediately,
                            iterations = Integer.MAX_VALUE,
                            initialDelayMillis = 3000,
                            repeatDelayMillis = 3000
                        )
                        .padding(start = 4.dp)
                )
            }
        )
    }
}

@Composable
private fun UnlockHistoryPalettesComponent(
    accountViewModel: AccountScreenViewModel = viewModel(factory = AccountScreenViewModel.Factory)
) {

    val accountPalettes = accountViewModel.accountUnlockedPalettesUiState.collectAsStateWithLifecycle()

    LazyColumn {

        items(items = accountPalettes.value.unlockedPalettes) { palette ->

            val palette = LocalPalettesMap[palette.uuid]

            if(palette != null) {
                PaletteListItem(palette = palette)
            }

        }

    }

}

@Preview
@Composable
private fun AccountDetailsComponentPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        PaletteListItem(ClassicPalette)
    }

}

@Preview
@Composable
private fun LabeledValuePreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {

        Column {

            Spacer(modifier = Modifier.height(128.dp))

            LabeledValue(
                title = "ds:",
                value = "d"
            )

        }
    }

}

@Composable
private fun PaletteListItem(
    palette: ExtendedPalette
) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(8.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .aspectRatio(1f),
                painter = painterResource(palette.extrasFamily.badge),
                contentDescription = "",
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.width(32.dp))

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = stringResource(palette.extrasFamily.title),
                style = LocalTypography.current.quaternary.bold.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp
                ),
            )
        }
    }
}

@Composable
private fun SignInComponent(
    accountViewModel: AccountScreenViewModel = viewModel(factory = AccountScreenViewModel.Factory),
    onClick: () -> Unit = {},
    onFailure: () -> Unit = {},
    onSignIn: (result: Boolean) -> Unit = {}
) {

    val rememberCoroutineScope = rememberCoroutineScope()

    val activity = LocalActivity.current
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(16.dp))

    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .widthIn(max = 600.dp)
            .wrapContentHeight()
            .padding(8.dp),
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(8.dp),
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BasicText(
                modifier = Modifier
                    .height(36.dp),
                text = stringResource(R.string.marketplace_error_login_required),
                style = LocalTypography.current.quaternary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(12.sp, 36.sp, 5.sp),
                maxLines = 2
            )

            SignInWithGoogleButton {

                onClick()

                accountViewModel.getSignInCredentials(
                    signInOption = SignInOptions.GOOGLE
                ) { credentialOption ->
                    Log.d("AccountScreen", "Attempting Sign-in init.")

                    rememberCoroutineScope.launch {
                        activity ?: return@launch

                        try {

                            accountViewModel.signInWithCredentials(
                                activity = activity,
                                context = context,
                                credentialOption = credentialOption
                            ) { result ->
                                onSignIn(result)
                            }

                        } catch (e: Exception) {
                            Toast.makeText(activity,
                                "Sign-in failed.",
                                Toast.LENGTH_SHORT)
                                .show()
                            e.printStackTrace()

                            onFailure()
                        }

                    }

                }

            }

        }

    }

}

@Composable
private fun SignOutComponent(
    modifier: Modifier = Modifier,
    onLogoutClicked: () -> Unit = {},
    onDeactivateClicked: () -> Unit = {},
) {

    Spacer(modifier = Modifier.height(16.dp))

    val rememberMaxWidth = remember { mutableIntStateOf(200) }

    Column(
        modifier = modifier
            .padding(8.dp)
            .width(rememberMaxWidth.intValue.dp)
            .wrapContentHeight()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SignOutButton(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    if (rememberMaxWidth.intValue > it.width)
                        rememberMaxWidth.intValue = it.width
                }
                .padding(4.dp)
        ) {
            onLogoutClicked()
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
        )

        DeactivateAccountButton(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    if (rememberMaxWidth.intValue > it.width)
                        rememberMaxWidth.intValue = it.width
                }
                .padding(4.dp)
        ) {
            onDeactivateClicked()
        }

    }

}

/** @see <a href="https://developers.google.com/identity/branding-guidelines">
 * Google Branding Guidelines</a> */
@Composable
private fun SignInWithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(.5f)),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_android_light_rd_si),
            contentDescription = null,
        )

    }
}

@Composable
private fun SignOutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = LocalPalette.current.codexFamily.codex3
        ),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp)
    ) {

        Text(
            text = stringResource(R.string.account_button_logout),
            style = LocalTypography.current.quaternary.bold,
            color = LocalPalette.current.surface.onColor,
            fontSize = 18.sp
        )

    }
}

@Composable
private fun DeactivateAccountButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = LocalPalette.current.coreFamily.primary
        ),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp)
    ) {

        Text(
            text = stringResource(R.string.account_button_deactivate),
            style = LocalTypography.current.quaternary.bold.copy(
                textAlign = TextAlign.Center
            ),
            color = LocalPalette.current.background.color,
            fontSize = 18.sp
        )

    }
}

@Composable
private fun DeactivateAccountDialog(
    onConfirm: () -> Unit = { },
    onCancel: () -> Unit = { }
) {

    val title = stringResource(id = R.string.account_deactivate_title)

    val content: @Composable () -> Unit = {

        Text(
            text = stringResource(id = R.string.account_deactivate_warning),
            style = TextStyle(
                fontSize = 14.sp,
                color = LocalPalette.current.textFamily.body
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_1),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalPalette.current.textFamily.body
                )
            )

            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_2),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalPalette.current.textFamily.body
                )
            )

            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_3),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalPalette.current.textFamily.body
                )
            )
        }
    }

    val cancelButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_deactivate_button_cancel),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = LocalPalette.current.surface.onColor,
                containerColor = LocalPalette.current.textFamily.body,
                disabledContentColor = Color.Blue,
                disabledContainerColor = Color.Green
            ),
            shape = RoundedCornerShape(percent = 20),
            onClick = { onCancel() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }

    val confirmButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_deactivate_button_confirm),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = LocalPalette.current.background.color,
                containerColor = LocalPalette.current.coreFamily.primary,
                disabledContentColor = Color.Blue,
                disabledContainerColor = Color.Green
            ),
            shape = RoundedCornerShape(percent = 20),
            onClick = { onConfirm() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }

    Dialog(
        title = title,
        content = { content() },
        cancelButton = { cancelButton() },
        confirmButton = { confirmButton() }
    )

}

@Composable
private fun LogoutDialog(
    onConfirm: () -> Unit = { },
    onCancel: () -> Unit = { }
) {

    val title = stringResource(id = R.string.account_logout_title)

    val content: @Composable () -> Unit = {
        Text(
            text = stringResource(id = R.string.account_logout_warning),
            style = TextStyle(
                fontSize = 18.sp,
                color = LocalPalette.current.textFamily.body
            )
        )
    }

    val cancelButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_logout_button_cancel),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = LocalPalette.current.surface.onColor,
                containerColor = LocalPalette.current.textFamily.body,
                disabledContentColor = Color.Blue,
                disabledContainerColor = Color.Green
            ),
            shape = RoundedCornerShape(percent = 20),
            onClick = { onCancel() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }

    val confirmButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_logout_button_confirm),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = LocalPalette.current.background.color,
                containerColor = LocalPalette.current.coreFamily.primary,
                disabledContentColor = Color.Blue,
                disabledContainerColor = Color.Green
            ),
            shape = RoundedCornerShape(percent = 20),
            onClick = { onConfirm() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }

    Dialog(
        title = title,
        content = { content() },
        cancelButton = { cancelButton() },
        confirmButton = { confirmButton() }
    )
}

@Preview
@Composable
private fun SignOutButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        SignOutButton()
    }
}

@Preview
@Composable
private fun DeactivateAccountButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        DeactivateAccountButton()
    }
}

private enum class AccountOverviewDialog {
    NONE,
    SIGN_OUT,
    DEACTIVATE_ACCOUNT
}
