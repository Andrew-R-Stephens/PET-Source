package com.tritiumgaming.phasmophobiaevidencepicker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity

@Composable
fun RootNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainMenuActivity") {
        navigation(
            route = "mainMenuActivity",
            startDestination = "titleScreenFragment"
        ) {

            composable(route = "titleScreenFragment") {
                // TitleScreenFragment
            }

            composable(route = "languageFragment") {
                // LanguageFragment
            }

            composable(route = "infoFragment") {
                // InfoFragment
            }

            composable(route = "settingsFragment") {
                // InfoFragment
            }

            navigation(
                route = "newsletterNavigation",
                startDestination = "inboxFragment"
            ) {

                composable(route = "newsInboxFragment") {
                    // InboxFragment
                }
                composable(route = "newsMessagesFragment") {
                    // MessagesFragment
                }
                composable(route = "newsMessageFragment") {
                    // MessageFragment
                }

            }

            navigation(
                route = "marketplaceNavigation",
                startDestination = "accountOverviewFragment"
            ) {

                composable(route = "accountOverviewFragment") {
                    // AccountOverviewFragment
                }

                composable(route = "marketplaceUnlocksFragment") {
                    // UnlocksFragment
                }

                composable(route = "marketplaceBillableFragment") {
                    // MarketplaceBillableFragment
                }

            }
        }
        composable(
            route = "investigationActivity"
        ) {
            InvestigationActivity()
        }
    }
}

