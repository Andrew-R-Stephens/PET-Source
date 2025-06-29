package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.AccountManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.SignInCredentialManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.component.Dialog
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common.IndeterminateCircularIndicator
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuFirebaseScreen

@Composable
@Preview
private fun AccountScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountScreen()
    }
}

@Composable
fun AccountScreen(
    navController: NavController = rememberNavController()
) {

    MainMenuFirebaseScreen(
        content = { AccountContent(navController = navController) }
    )

}


@Composable
private fun AccountContent(
    navController: NavController = rememberNavController()
) {
    var rememberAccount by remember { mutableStateOf<String?>(Firebase.auth.currentUser?.uid) }

    var rememberShowLogoutDialog by remember { mutableStateOf(false) }
    var rememberShowDeactivateAccountDialog by remember { mutableStateOf(false) }

    Box {

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
                    SignInComponent {
                        rememberAccount = Firebase.auth.currentUser?.uid
                    }
                } else {
                    //TODO Account Details here

                    SignOutComponent(
                        onLogoutClicked = {
                            rememberShowLogoutDialog = true
                        },
                        onDeactivateClicked = {
                            rememberShowDeactivateAccountDialog = true
                        }
                    )
                }

            }

            IndeterminateCircularIndicator(
                color1 = LocalPalette.current.textFamily.body,
                color2 = LocalPalette.current.surface.onColor,
                isLoading = false
            )

        }


        if(rememberShowDeactivateAccountDialog) {
            DeactivateAccountDialog(
                onConfirm = {
                    AccountManager().delete(
                        onSuccess = {
                            Log.d("Firebase", "Account deleted")
                            rememberAccount = Firebase.auth.currentUser?.uid
                            rememberShowDeactivateAccountDialog = false
                        },
                        onFailure = {
                            Log.e("Firebase", "Error deleting account")
                            rememberShowDeactivateAccountDialog = false
                        },
                        onComplete = {
                            rememberShowDeactivateAccountDialog = false
                        }
                    )
                },
                onCancel = {
                    rememberShowDeactivateAccountDialog = false
                }
            )
        }

        if(rememberShowLogoutDialog) {
            LogoutDialog(
                onConfirm = {
                    AccountManager().signOut(
                        onSuccess = {
                            Log.d("Firebase", "Signed out current user")
                            rememberAccount = Firebase.auth.currentUser?.uid
                            rememberShowLogoutDialog = false
                        }
                    )
                },
                onCancel = {
                    rememberShowLogoutDialog = false
                }
            )
        }

    }
}

@Composable
private fun SignInComponent(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel ( factory = GlobalPreferencesViewModel.Factory ),
    onSignIn: () -> Unit = {}
) {

    Spacer(modifier = Modifier.height(16.dp))

    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
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

            val context = LocalContext.current
            SignInWithGoogleButton {

                AccountManager().signIn(
                    activity = context as PETActivity,
                    option = SignInCredentialManager.SignInOptions.GOOGLE,
                    onSuccess = {
                        Log.d("Firebase", "Signed in as: ${Firebase.auth.currentUser?.displayName}")
                        onSignIn()
                    }
                )

            }

        }

    }

}

@Composable
private fun SignOutComponent(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel ( factory = GlobalPreferencesViewModel.Factory ),
    onLogoutClicked: () -> Unit = {},
    onDeactivateClicked: () -> Unit = {}
) {

    Spacer(modifier = Modifier.height(16.dp))

    val rememberMaxWidth = remember { mutableIntStateOf(200) }

    Column(
        modifier = Modifier
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
        ) {
            onLogoutClicked()
        }

        DeactivateAccountButton(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    if (rememberMaxWidth.intValue > it.width)
                        rememberMaxWidth.intValue = it.width
                }
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
            containerColor = LocalPalette.current.codexFamily.codex3_buttonBackground
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
            style = LocalTypography.current.quaternary.bold,
            color = LocalPalette.current.background.color,
            fontSize = 18.sp
        )

    }
}

@Preview
@Composable
fun SignOutButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        SignOutButton()
    }
}

@Preview
@Composable
fun DeactivateAccountButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        DeactivateAccountButton()
    }
}

@Composable
fun DeactivateAccountDialog(
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
fun LogoutDialog(
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
