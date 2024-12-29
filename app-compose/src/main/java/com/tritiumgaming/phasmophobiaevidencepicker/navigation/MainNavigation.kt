package com.tritiumgaming.phasmophobiaevidencepicker.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.evidence.EvidenceSoloScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.mapsmenu.MapMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.mapsmenu.mapdisplay.MapViewerScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.missions.MissionsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children.CodexAchievementScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children.CodexEquipmentScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children.CodexPossessionsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.codexmenu.CodexMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.appinfo.InfoScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.applanguages.LanguageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.appsettings.SettingsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.marketplace.MarketplaceScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.marketplace.billing.MarketplaceBillingScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.newsletter.NewsInboxesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.newsletter.NewsMessageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.newsletter.NewsMessagesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.startscreen.StartScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.account.AccountScreen
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoutes.*

@Composable
fun RootNavigation(
    /*investigationViewModel: InvestigationViewModel,
    onboardingViewModel: OnboardingViewModel,
    newsletterViewModel: NewsletterViewModel,
    mainMenuViewModel: MainMenuViewModel,
    globalPreferencesViewModel: GlobalPreferencesViewModel,
    permissionsViewModel: PermissionsViewModel*/
) {

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
                StartScreen(
                    navController = navController
                )
            }

            composable(route = SCREEN_LANGUAGE.route) {
                LanguageScreen()
            }

            composable(route = SCREEN_APP_INFO.route) {
                InfoScreen()
            }

            composable(route = SCREEN_SETTINGS.route) {
                SettingsScreen()
            }

            navigation(
                route = NAVIGATION_NEWSLETTER.route,
                startDestination = SCREEN_NEWSLETTER_INBOX.route
            ) {

                composable(route = SCREEN_NEWSLETTER_INBOX.route) {
                    NewsInboxesScreen()
                }
                composable(route = SCREEN_NEWSLETTER_MESSAGES.route) {
                    NewsMessagesScreen()
                }
                composable(route = SCREEN_NEWSLETTER_MESSAGE.route) {
                    NewsMessageScreen()
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

enum class NavRoutes(val route: String) {

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