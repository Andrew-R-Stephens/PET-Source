package com.tritiumgaming.feature.account.ui

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalettesMap
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.indicator.InfiniteThrobber
import com.tritiumgaming.core.ui.widgets.label.LabeledValue
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderSideButton
import com.tritiumgaming.feature.account.ui.component.AccountBannerComposite
import com.tritiumgaming.feature.account.ui.component.AccountBannerExpanded
import com.tritiumgaming.feature.account.ui.component.Dialog
import com.tritiumgaming.shared.data.account.model.AccountPalette
import com.tritiumgaming.shared.data.account.model.SignInOptions
import kotlinx.coroutines.launch

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@DevicePreviews
@Composable
@Preview
private fun AccountPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(color = LocalPalette.current.surface) {
            AccountContent(
                currentUser = "uid123",
                userName = "John Doe",
                userEmail = "john.doe@example.com",
                earnedCredits = 250,
                unlockedPalettes = emptyList(),
                currentDialog = AccountOverviewDialog.NONE,
                isLoading = false,
                onBack = {},
                onLogoutRequest = {},
                onDeactivateRequest = {},
                onDismissDialog = {},
                onConfirmSignOut = {},
                onConfirmDeactivate = {},
                onSignInRequest = {}
            )
        }
    }
}

@Composable
fun AccountScreen(
    navController: NavController = rememberNavController(),
    accountViewModel: AccountScreenViewModel
) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val accountCreditsUiState by accountViewModel.accountCreditsUiState.collectAsStateWithLifecycle()
    val accountPalettesUiState by accountViewModel.accountUnlockedPalettesUiState.collectAsStateWithLifecycle()

    var rememberAccount by remember { mutableStateOf(Firebase.auth.currentUser?.uid) }
    var rememberDialog by remember { mutableStateOf(AccountOverviewDialog.NONE) }
    var isLoading by remember { mutableStateOf(false) }

    AccountContent(
        currentUser = rememberAccount,
        userName = Firebase.auth.currentUser?.displayName ?: "",
        userEmail = Firebase.auth.currentUser?.email ?: "",
        earnedCredits = accountCreditsUiState.earnedCredits,
        unlockedPalettes = accountPalettesUiState.unlockedPalettes,
        currentDialog = rememberDialog,
        isLoading = isLoading,
        onBack = { navController.popBackStack() },
        onLogoutRequest = { rememberDialog = AccountOverviewDialog.SIGN_OUT },
        onDeactivateRequest = { rememberDialog = AccountOverviewDialog.DEACTIVATE_ACCOUNT },
        onDismissDialog = { rememberDialog = AccountOverviewDialog.NONE },
        onConfirmSignOut = {
            accountViewModel.signOutAccount { success ->
                rememberAccount = Firebase.auth.currentUser?.uid
                rememberDialog = AccountOverviewDialog.NONE
                if (success) {
                    Toast.makeText(activity, activity?.getString(R.string.alert_account_logout_success), Toast.LENGTH_SHORT).show()
                }
            }
        },
        onConfirmDeactivate = {
            accountViewModel.deactivateAccount { success ->
                rememberAccount = Firebase.auth.currentUser?.uid
                rememberDialog = AccountOverviewDialog.NONE
                if (success) {
                    Toast.makeText(activity, activity?.getString(R.string.alert_account_remove_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, activity?.getString(R.string.alert_account_remove_failure), Toast.LENGTH_SHORT).show()
                }
            }
        },
        onSignInRequest = {
            isLoading = true
            accountViewModel.getSignInCredentials(SignInOptions.GOOGLE) { credentialOption ->
                coroutineScope.launch {
                    try {
                        accountViewModel.signInWithCredentials(activity!!, context, credentialOption) { result ->
                            rememberAccount = Firebase.auth.currentUser?.uid
                            isLoading = false
                            if (result) {
                                Toast.makeText(activity, "${activity.getString(R.string.alert_account_welcome)} ${Firebase.auth.currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, activity.getString(R.string.alert_account_login_failure), Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        isLoading = false
                        Toast.makeText(activity, "Sign-in failed.", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    )
}

@Composable
fun AccountContent(
    currentUser: String?,
    userName: String,
    userEmail: String,
    earnedCredits: Int,
    unlockedPalettes: List<AccountPalette>,
    currentDialog: AccountOverviewDialog,
    isLoading: Boolean,
    onBack: () -> Unit,
    onLogoutRequest: () -> Unit,
    onDeactivateRequest: () -> Unit,
    onDismissDialog: () -> Unit,
    onConfirmSignOut: () -> Unit,
    onConfirmDeactivate: () -> Unit,
    onSignInRequest: () -> Unit
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            NavigationHeader(
                onLeftClick = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                if (currentUser == null) {
                    SignInComponent(
                        onSignInRequest = onSignInRequest
                    )
                } else {
                    when(deviceConfiguration) {
                        DeviceConfiguration.MOBILE_PORTRAIT -> {
                            AccountComponentPortrait(
                                userName = userName,
                                userEmail = userEmail,
                                earnedCredits = earnedCredits,
                                unlockedPalettes = unlockedPalettes,
                                onLogoutClicked = onLogoutRequest,
                                onDeactivateClicked = onDeactivateRequest
                            )
                        }
                        else -> {
                            AccountComponentLandscape(
                                userName = userName,
                                userEmail = userEmail,
                                earnedCredits = earnedCredits,
                                onLogoutClicked = onLogoutRequest,
                                onDeactivateClicked = onDeactivateRequest
                            )
                        }
                    }
                }

            }

        }

        when(currentDialog) {
            AccountOverviewDialog.DEACTIVATE_ACCOUNT ->
                DeactivateAccountDialog(
                    confirmButton = { modifier ->
                        DialogButton(
                            modifier = modifier,
                            text = stringResource(id = R.string.account_deactivate_button_confirm),
                            textStyle = LocalTypography.current.quaternary.bold.copy(
                                fontSize = 18.sp,
                                color = LocalPalette.current.onErrorContainer
                            ),
                            buttonColors = ButtonColors(
                                contentColor = LocalPalette.current.onErrorContainer,
                                containerColor = LocalPalette.current.errorContainer,
                                disabledContentColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            onClick = onConfirmDeactivate
                        )
                    },
                    cancelButton = { modifier ->
                        DialogButton(
                            modifier = modifier,
                            text = stringResource(id = R.string.account_deactivate_button_cancel),
                            textStyle = LocalTypography.current.quaternary.bold.copy(
                                fontSize = 18.sp,
                                color = LocalPalette.current.onSecondaryContainer
                            ),
                            buttonColors = ButtonColors(
                                containerColor = LocalPalette.current.secondaryContainer,
                                contentColor = LocalPalette.current.onSecondaryContainer,
                                disabledContentColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            onClick = onDismissDialog
                        )
                    }
                )
            AccountOverviewDialog.SIGN_OUT ->
                LogoutDialog(
                    confirmButton = { modifier ->
                        DialogButton(
                            modifier = modifier,
                            text = stringResource(id = R.string.account_logout_button_confirm),
                            textStyle = LocalTypography.current.quaternary.bold.copy(
                                fontSize = 18.sp,
                                color = LocalPalette.current.onErrorContainer
                            ),
                            buttonColors = ButtonColors(
                                contentColor = LocalPalette.current.onErrorContainer,
                                containerColor = LocalPalette.current.errorContainer,
                                disabledContentColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            onClick = onConfirmSignOut
                        )
                    },
                    cancelButton = { modifier ->
                        DialogButton(
                            modifier = modifier,
                            text = stringResource(id = R.string.account_logout_button_cancel),
                            textStyle = LocalTypography.current.quaternary.bold.copy(
                                fontSize = 18.sp,
                                color = LocalPalette.current.onSecondaryContainer
                            ),
                            buttonColors = ButtonColors(
                                contentColor = LocalPalette.current.onSecondaryContainer,
                                containerColor = LocalPalette.current.secondaryContainer,
                                disabledContentColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            onClick = onDismissDialog
                        )
                    }
                )
            AccountOverviewDialog.NONE -> {}
        }

        InfiniteThrobber(
            color1 = LocalPalette.current.surfaceContainer,
            color2 = LocalPalette.current.surfaceContainerHigh,
            isLoading = isLoading
        )

    }
}

@Composable
private fun NavigationHeader(
    onLeftClick: () -> Unit = {}
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
                        text = stringResource(R.string.account_title),
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
private fun AccountComponentPortrait(
    userName: String,
    userEmail: String,
    earnedCredits: Int,
    unlockedPalettes: List<AccountPalette>,
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
            userName = userName,
            userEmail = userEmail,
            earnedCredits = earnedCredits
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
                unlockedPalettes = unlockedPalettes
            )
        }
    }
}

@Composable
private fun AccountComponentLandscape(
    userName: String,
    userEmail: String,
    earnedCredits: Int,
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
            userName = userName,
            userEmail = userEmail,
            earnedCredits = earnedCredits
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
    userName: String,
    userEmail: String,
    earnedCredits: Int,
) {

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AccountBannerExpanded(
            credits = earnedCredits
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(R.string.account_label_information),
            style = LocalTypography.current.quaternary.bold,
            color = LocalPalette.current.onSurface,
            fontSize = 18.sp,
            maxLines = 1,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_name)}:",
            value = userName,
            containerColor = LocalPalette.current.surfaceContainer,
            textColor = LocalPalette.current.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_email)}:",
            value = userEmail,
            containerColor = LocalPalette.current.surfaceContainer,
            textColor = LocalPalette.current.onSurface
        )

    }

}

@Composable
private fun AccountDetailsLandscapeComponent(
    modifier: Modifier = Modifier,
    userName: String,
    userEmail: String,
    earnedCredits: Int,
) {

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AccountBannerComposite(
            credits = earnedCredits
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(R.string.account_label_information),
            style = LocalTypography.current.quaternary.bold,
            color = LocalPalette.current.onSurface,
            fontSize = 18.sp,
            maxLines = 1,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_name)}:",
            value = userName,
            containerColor = LocalPalette.current.surfaceContainer,
            textColor = LocalPalette.current.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledValue(
            title = "${stringResource(R.string.account_label_email)}:",
            value = userEmail,
            containerColor = LocalPalette.current.surfaceContainer,
            textColor = LocalPalette.current.onSurface
        )

    }

}

@Composable
private fun UnlockHistoryPalettesComponent(
    unlockedPalettes: List<AccountPalette>
) {

    LazyColumn {

        items(items = unlockedPalettes) { palette ->

            val palette = LocalPalettesMap[palette.uuid]

            if(palette != null) {
                PaletteListItem(palette = palette)
            }

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
        color = LocalPalette.current.surfaceContainerHigh,
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
                    color = LocalPalette.current.onSurface,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp
                ),
            )
        }
    }
}

@Composable
private fun SignInComponent(
    onSignInRequest: () -> Unit = {}
) {

    Spacer(modifier = Modifier.height(16.dp))

    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .widthIn(max = 600.dp)
            .wrapContentHeight()
            .padding(8.dp),
        color = LocalPalette.current.surfaceContainer,
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
                    color = LocalPalette.current.onSurface,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(12.sp, 36.sp, 5.sp),
                maxLines = 2
            )

            SignInWithGoogleButton {
                onSignInRequest()
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

        AccountActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    if (rememberMaxWidth.intValue > it.width)
                        rememberMaxWidth.intValue = it.width
                }
                .padding(4.dp),
            text = stringResource(id = R.string.account_button_logout),
            textStyle = LocalTypography.current.quaternary.bold.copy(
                fontSize = 18.sp,
                color = LocalPalette.current.onSecondaryContainer
            ),
            buttonColors = ButtonColors(
                containerColor = LocalPalette.current.secondaryContainer,
                contentColor = LocalPalette.current.onSecondaryContainer,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            onLogoutClicked()
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
        )

        AccountActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    if (rememberMaxWidth.intValue > it.width)
                        rememberMaxWidth.intValue = it.width
                }
                .padding(4.dp),
            text = stringResource(id = R.string.account_button_deactivate),
            textStyle = LocalTypography.current.quaternary.bold.copy(
                fontSize = 18.sp,
                color = LocalPalette.current.onErrorContainer
            ),
            buttonColors = ButtonColors(
                containerColor = LocalPalette.current.errorContainer,
                contentColor = LocalPalette.current.onErrorContainer,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
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
private fun AccountActionButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = TextStyle(),
    buttonColors: ButtonColors,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        content = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text.uppercase(),
                maxLines = 1,
                style = textStyle,
                textAlign = TextAlign.Center,
                color = textStyle.color
            )
        },
        contentPadding = PaddingValues(8.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(percent = 20),
        onClick = { onClick() },
    )
}

@Composable
private fun DialogButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = TextStyle(),
    buttonColors: ButtonColors,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        content = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text.uppercase(),
                maxLines = 1,
                style = textStyle,
                textAlign = TextAlign.Center,
                color = textStyle.color
            )
        },
        contentPadding = PaddingValues(8.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(percent = 20),
        onClick = { onClick() },
    )
}

@Composable
private fun DeactivateAccountDialog(
    contentColor: Color = LocalPalette.current.onSurface,
    containerColor: Color = LocalPalette.current.surfaceContainer,
    scrimColor: Color = LocalPalette.current.scrim,
    confirmButton: @Composable (modifier: Modifier) -> Unit = { },
    cancelButton: @Composable (modifier: Modifier) -> Unit = { },
) {

    val title = stringResource(id = R.string.account_deactivate_title)

    val content: @Composable () -> Unit = {

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.account_deactivate_warning),
            style = LocalTypography.current.quaternary.bold.copy(
                fontSize = 14.sp,
                color = contentColor
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
                style = LocalTypography.current.quaternary.bold.copy(
                    fontSize = 14.sp,
                    color = contentColor
                )
            )

            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_2),
                style = LocalTypography.current.quaternary.bold.copy(
                    fontSize = 14.sp,
                    color = contentColor
                )
            )

            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_3),
                style = LocalTypography.current.quaternary.bold.copy(
                    fontSize = 14.sp,
                    color = contentColor
                )
            )
        }
    }

    Dialog(
        title = title,
        content = { content() },
        cancelButton = { cancelButton(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) },
        confirmButton = { confirmButton(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) },
        backgroundColor = containerColor,
        scrimColor = scrimColor
    )

}

@Composable
private fun LogoutDialog(
    contentColor: Color = LocalPalette.current.onSurface,
    containerColor: Color = LocalPalette.current.surfaceContainer,
    scrimColor: Color = LocalPalette.current.scrim,
    confirmButton: @Composable (modifier: Modifier) -> Unit = { },
    cancelButton: @Composable (modifier: Modifier) -> Unit = { }
) {

    val title = stringResource(id = R.string.account_logout_title)

    val content: @Composable () -> Unit = {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.account_logout_warning),
            style = LocalTypography.current.quaternary.bold.copy(
                fontSize = 14.sp,
                color = contentColor
            )
        )
    }

    Dialog(
        title = title,
        content = { content() },
        cancelButton = { cancelButton(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) },
        confirmButton = { confirmButton(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) },
        backgroundColor = containerColor,
        scrimColor = scrimColor
    )
}

enum class AccountOverviewDialog {
    NONE,
    SIGN_OUT,
    DEACTIVATE_ACCOUNT
}
