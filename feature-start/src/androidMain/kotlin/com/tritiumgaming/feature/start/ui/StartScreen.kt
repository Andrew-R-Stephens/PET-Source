package com.tritiumgaming.feature.start.ui

import android.content.Intent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.preview.DevicePreviews
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import java.util.Locale

@DevicePreviews
@Composable
private fun StartScreenPreview() {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            StartContent(
                inboxNotificationState = true,
                canRequestReview = true,
                currentLanguage = "English",
                currentUser = null,
                onNavigate = {},
                onOpenPatreon = {},
                onOpenDiscord = {}
            )
        }
    }
}

@Composable
fun StartScreen(
    startScreenViewModel: StartScreenViewModel,
    navController: NavHostController
) {
    val newsletterInboxesUiState by startScreenViewModel.inboxesUiState.collectAsStateWithLifecycle()
    val reviewUiState by startScreenViewModel.reviewFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.link_discordInvite)
    val patreonInvitation = stringResource(R.string.link_patreonInvite)

    val inboxNotificationState = remember(newsletterInboxesUiState) {
        newsletterInboxesUiState.inboxes
            .sortedByDescending { it.lastReadDate }
            .firstOrNull { inboxUiState ->
                inboxUiState.inbox.compareDates(inboxUiState.lastReadDate)
            } != null
    }

    val rememberLocale = remember { Locale.getDefault() }
    var currentLanguage = rememberLocale.displayLanguage
    if (currentLanguage.isNotEmpty()) {
        currentLanguage = currentLanguage.substring(0, 1)
            .uppercase(rememberLocale) + currentLanguage.substring(1)
    }

    val currentUser = Firebase.auth.currentUser

    // Optimized with remember to prevent unnecessary recompositions of child components due to lambda reference changes
    val onNavigate = remember(navController) {
        { route: String ->
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    val onOpenPatreon = remember(context, patreonInvitation) {
        {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "https://patreon.com/ $patreonInvitation".toUri()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val onOpenDiscord = remember(context, discordInvitation) {
        {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "https://discord.gg/ $discordInvitation".toUri()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    StartContent(
        inboxNotificationState = inboxNotificationState,
        canRequestReview = reviewUiState.canRequestReview,
        currentLanguage = currentLanguage,
        currentUser = currentUser,
        onNavigate = onNavigate,
        onOpenPatreon = onOpenPatreon,
        onOpenDiscord = onOpenDiscord
    )
}
