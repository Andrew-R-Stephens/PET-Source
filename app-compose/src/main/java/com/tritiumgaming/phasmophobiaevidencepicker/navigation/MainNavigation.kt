package com.tritiumgaming.phasmophobiaevidencepicker.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.NAVIGATION_CODEX
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.NAVIGATION_INVESTIGATION
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.NAVIGATION_MAIN_MENU
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.NAVIGATION_MAPS
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.NAVIGATION_MARKETPLACE
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.NAVIGATION_NEWSLETTER
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_ACCOUNT_OVERVIEW
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_APP_INFO
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_CODEX_ACHIEVEMENTS
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_CODEX_EQUIPMENT
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_CODEX_MENU
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_CODEX_POSSESSIONS
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_EVIDENCE
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_MAPS_MENU
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_MAP_VIEWER
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_MARKETPLACE_BILLABLE
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_MARKETPLACE_UNLOCKS
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_MISSIONS
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_NEWSLETTER_INBOX
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_NEWSLETTER_MESSAGE
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_NEWSLETTER_MESSAGES
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_SETTINGS
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute.SCREEN_START
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.evidence.EvidenceSoloScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.mapsmenu.MapMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.mapsmenu.mapdisplay.MapViewerScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.missions.MissionsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children.CodexAchievementScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children.CodexEquipmentScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children.CodexPossessionsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.utilities.codex.codexmenu.CodexMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.account.AccountScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.appinfo.InfoScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.applanguages.LanguageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.appsettings.SettingsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.marketplace.MarketplaceScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.marketplace.billing.MarketplaceBillingScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.newsletter.NewsInboxesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.newsletter.NewsMessageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.newsletter.NewsMessagesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.startscreen.StartScreen

@Composable
fun RootNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAVIGATION_MAIN_MENU.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {

        navigation(
            route = NAVIGATION_MAIN_MENU.route,
            //startDestination = "startScreen",
            startDestination = SCREEN_START.route
        ) {

            composable(route = SCREEN_START.route) {
                StartScreen(navController = navController)
            }

            composable(route = SCREEN_LANGUAGE.route) {
                LanguageScreen(navController = navController)
            }

            composable(route = SCREEN_APP_INFO.route) {
                InfoScreen(navController = navController)
            }

            composable(route = SCREEN_SETTINGS.route) {
                SettingsScreen(navController = navController)
            }

            navigation(
                route = NAVIGATION_NEWSLETTER.route,
                startDestination = SCREEN_NEWSLETTER_INBOX.route
            ) {

                composable(route = SCREEN_NEWSLETTER_INBOX.route) {
                    NewsInboxesScreen(navController)
                }

                composable(route = "${SCREEN_NEWSLETTER_MESSAGES.route}/{inboxID}",
                    arguments = listOf(
                        navArgument("inboxID") { type = NavType.IntType }
                    )) { navBackStackEntry ->
                        val inboxID = navBackStackEntry.arguments?.getInt("inboxID")

                    if(inboxID != null) {
                        NewsMessagesScreen(
                            navController = navController,
                            inboxID = inboxID
                        )
                    } else {
                        navController.popBackStack()
                    }

                }

                composable(route = "${SCREEN_NEWSLETTER_MESSAGE.route}/{inboxID}/{messageID}",
                    arguments = listOf(
                        navArgument("inboxID") { type = NavType.IntType },
                        navArgument("messageID") { type = NavType.IntType }
                    )) { navBackStackEntry ->

                    val inboxID = navBackStackEntry.arguments?.getInt("inboxID")
                    val messageID = navBackStackEntry.arguments?.getInt("messageID")

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
                route = NAVIGATION_MARKETPLACE.route,
                startDestination = SCREEN_ACCOUNT_OVERVIEW.route
            ) {

                composable(route = SCREEN_ACCOUNT_OVERVIEW.route) {
                    AccountScreen()
                }

                composable(route = SCREEN_MARKETPLACE_UNLOCKS.route) {
                    MarketplaceScreen()
                }

                composable(route = SCREEN_MARKETPLACE_BILLABLE.route) {
                    MarketplaceBillingScreen()
                }

            }
        }

        navigation(
            route = NAVIGATION_INVESTIGATION.route,
            startDestination = SCREEN_EVIDENCE.route
        ) {

            composable(route = SCREEN_EVIDENCE.route) {
                EvidenceSoloScreen()
            }

            composable(route = SCREEN_MISSIONS.route) {
                MissionsScreen()
            }

            navigation(
                route = NAVIGATION_MAPS.route,
                startDestination = SCREEN_MAPS_MENU.route
            ) {

                composable(route = SCREEN_MAPS_MENU.route) {
                    MapMenuScreen()
                }

                composable(route = SCREEN_MAP_VIEWER.route) {
                    MapViewerScreen()
                }

            }

            navigation(
                route = NAVIGATION_CODEX.route,
                startDestination = SCREEN_CODEX_MENU.route
            ) {

                composable(route = SCREEN_CODEX_MENU.route) {
                    CodexMenuScreen()
                }

                composable(route = SCREEN_CODEX_EQUIPMENT.route) {
                    CodexEquipmentScreen()
                }

                composable(route = SCREEN_CODEX_POSSESSIONS.route) {
                    CodexPossessionsScreen()
                }

                composable(route = SCREEN_CODEX_ACHIEVEMENTS.route) {
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

    SCREEN_EVIDENCE("EvidenceScreen"),
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