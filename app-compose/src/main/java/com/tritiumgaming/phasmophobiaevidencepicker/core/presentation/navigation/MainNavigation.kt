package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.SettingsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.account.AccountScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.appinfo.InfoScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.applanguages.LanguageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.marketplace.MarketplaceScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.marketplace.billing.MarketplaceBillingScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.NewsInboxesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.NewsMessageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.NewsMessagesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.StartScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationSoloScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.MapMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.MapViewerScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.MissionsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.itemstore.children.CodexAchievementScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.itemstore.children.CodexEquipmentScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.itemstore.children.CodexPossessionsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.menu.CodexMenuScreen

@Composable
fun RootNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.NAVIGATION_MAIN_MENU.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {

        navigation(
            route = NavRoute.NAVIGATION_MAIN_MENU.route,
            //startDestination = "startScreen",
            startDestination = NavRoute.SCREEN_START.route
        ) {

            composable(route = NavRoute.SCREEN_START.route) {
                StartScreen(navController = navController)
            }

            composable(route = NavRoute.SCREEN_LANGUAGE.route) {
                LanguageScreen(navController = navController)
            }

            composable(route = NavRoute.SCREEN_APP_INFO.route) {
                InfoScreen(navController = navController)
            }

            composable(route = NavRoute.SCREEN_SETTINGS.route) {
                SettingsScreen(navController = navController)
            }

            navigation(
                route = NavRoute.NAVIGATION_NEWSLETTER.route,
                startDestination = NavRoute.SCREEN_NEWSLETTER_INBOX.route
            ) {

                composable(route = NavRoute.SCREEN_NEWSLETTER_INBOX.route) {
                    NewsInboxesScreen(navController)
                }

                composable(route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGES.route}/{inboxID}",
                    arguments = listOf(
                        navArgument("inboxID") { type = NavType.StringType }
                    )) { navBackStackEntry ->
                        val inboxID = navBackStackEntry.arguments?.getString("inboxID")

                    if(inboxID != null) {
                        NewsMessagesScreen(
                            navController = navController,
                            inboxID = inboxID
                        )
                    } else {
                        navController.popBackStack()
                    }

                }

                composable(route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGE.route}/{inboxID}/{messageID}",
                    arguments = listOf(
                        navArgument("inboxID") { type = NavType.StringType },
                        navArgument("messageID") { type = NavType.StringType }
                    )) { navBackStackEntry ->

                    val inboxID = navBackStackEntry.arguments?.getString("inboxID")
                    val messageID = navBackStackEntry.arguments?.getString("messageID")

                    if(inboxID != null && messageID != null) {
                        NewsMessageScreen(
                            navController = navController,
                            inboxID = inboxID,
                            messageID = messageID
                        )
                    } else {
                        navController.popBackStack()
                    }
                }

            }

            navigation(
                route = NavRoute.NAVIGATION_MARKETPLACE.route,
                startDestination = NavRoute.SCREEN_ACCOUNT_OVERVIEW.route
            ) {

                composable(route = NavRoute.SCREEN_ACCOUNT_OVERVIEW.route) {
                    AccountScreen()
                }

                composable(route = NavRoute.SCREEN_MARKETPLACE_UNLOCKS.route) {
                    MarketplaceScreen()
                }

                composable(route = NavRoute.SCREEN_MARKETPLACE_BILLABLE.route) {
                    MarketplaceBillingScreen()
                }

            }
        }

        navigation(
            route = NavRoute.NAVIGATION_INVESTIGATION.route,
            startDestination = NavRoute.SCREEN_INVESTIGATION.route
        ) {

            composable(route = NavRoute.SCREEN_INVESTIGATION.route) {
                InvestigationSoloScreen()
            }

            composable(route = NavRoute.SCREEN_MISSIONS.route) {
                MissionsScreen()
            }

            navigation(
                route = NavRoute.NAVIGATION_MAPS.route,
                startDestination = NavRoute.SCREEN_MAPS_MENU.route
            ) {

                composable(route = NavRoute.SCREEN_MAPS_MENU.route) {
                    MapMenuScreen()
                }

                composable(route = NavRoute.SCREEN_MAP_VIEWER.route) {
                    MapViewerScreen()
                }

            }

            navigation(
                route = NavRoute.NAVIGATION_CODEX.route,
                startDestination = NavRoute.SCREEN_CODEX_MENU.route
            ) {

                composable(route = NavRoute.SCREEN_CODEX_MENU.route) {
                    CodexMenuScreen()
                }

                composable(route = NavRoute.SCREEN_CODEX_EQUIPMENT.route) {
                    CodexEquipmentScreen()
                }

                composable(route = NavRoute.SCREEN_CODEX_POSSESSIONS.route) {
                    CodexPossessionsScreen()
                }

                composable(route = NavRoute.SCREEN_CODEX_ACHIEVEMENTS.route) {
                    CodexAchievementScreen()
                }

            }

        }
    }
}

enum class NavRoute(val route: String) {

    NAVIGATION_MAIN_MENU("MainMenuNavigation"),
    NAVIGATION_MARKETPLACE("MarketplaceNavigation"),
    NAVIGATION_NEWSLETTER("NewsletterNavigation"),
    NAVIGATION_INVESTIGATION("InvestigationNavigation"),
    NAVIGATION_MAPS ("MapsNavigation"),
    NAVIGATION_CODEX ("CodexNavigation"),

    SCREEN_ACCOUNT_OVERVIEW("AccountScreen"),
    SCREEN_APP_INFO("InfoScreen"),
    SCREEN_LANGUAGE("LanguageScreen"),
    SCREEN_SETTINGS("SettingsScreen"),
    SCREEN_START("StartScreen"),

    SCREEN_INVESTIGATION("InvestigationScreen"),
    SCREEN_MISSIONS("MissionsScreen"),
    SCREEN_MAPS_MENU("MapMenuScreen"),
    SCREEN_MAP_VIEWER("MapViewerScreen"),

    SCREEN_CODEX_MENU("CodexMenuScreen"),
    SCREEN_CODEX_EQUIPMENT("CodexEquipmentScreen"),
    SCREEN_CODEX_POSSESSIONS("CodexPossessionsScreen"),
    SCREEN_CODEX_ACHIEVEMENTS("CodexAchievementsScreen"),
    
    SCREEN_NEWSLETTER_INBOX("NewsInboxScreen"),
    SCREEN_NEWSLETTER_MESSAGES("NewsMessagesScreen"),
    SCREEN_NEWSLETTER_MESSAGE("NewsMessageScreen"),
    
    SCREEN_MARKETPLACE_UNLOCKS("MarketplaceUnlocksScreen"),
    SCREEN_MARKETPLACE_BILLABLE("MarketplaceBillableScreen")
}