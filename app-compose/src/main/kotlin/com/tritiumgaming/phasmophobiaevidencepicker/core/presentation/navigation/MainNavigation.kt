package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.permissions.PermissionsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.AccountScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appinfo.InfoScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.applanguages.LanguageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.SettingsScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.marketplace.MarketplaceScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.marketplace.billing.MarketplaceBillingScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.screen.NewsInboxesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.screen.NewsMessageScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.screen.NewsMessagesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.StartScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.account.AccountViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.mainmenu.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.codex.CodexViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.codex.catalog.CodexItemstoreScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.codex.menu.CodexMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationSoloScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.MapMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.MapsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.MapViewerScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.ObjectivesScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.ObjectivesViewModel
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexResources

@Composable
fun RootNavigation(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    permissionsViewModel: PermissionsViewModel =
        viewModel(factory = PermissionsViewModel.Factory),
    mainMenuViewModel: MainMenuViewModel =
        viewModel(factory = MainMenuViewModel.Factory),
    accountViewModel: AccountViewModel =
        viewModel(factory = AccountViewModel.Factory),
    newsletterViewModel: NewsletterViewModel =
        viewModel(factory = NewsletterViewModel.Factory),
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory),
    objectivesViewModel: ObjectivesViewModel =
        viewModel(factory = ObjectivesViewModel.Factory),
    mapsViewModel: MapsViewModel =
        viewModel(factory = MapsViewModel.Factory),
    codexViewModel: CodexViewModel =
        viewModel(factory = CodexViewModel.Factory)
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.NAVIGATION_MAIN_MENU.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        mainMenuNavigation(
            navController = navController,
            globalPreferencesViewModel = globalPreferencesViewModel,
            permissionsViewModel = permissionsViewModel,
            mainMenuViewModel = mainMenuViewModel,
            accountViewModel = accountViewModel,
            newsletterViewModel = newsletterViewModel
        )

        investigationNavigation(
            navController = navController,
            investigationViewModel = investigationViewModel,
            objectivesViewModel = objectivesViewModel,
            mapsViewModel = mapsViewModel,
            codexViewModel = codexViewModel
        )

    }
}

private fun NavGraphBuilder.mainMenuNavigation(
    navController: NavHostController,
    globalPreferencesViewModel: GlobalPreferencesViewModel,
    permissionsViewModel: PermissionsViewModel,
    mainMenuViewModel: MainMenuViewModel,
    accountViewModel: AccountViewModel,
    newsletterViewModel: NewsletterViewModel
) {

    navigation(
        route = NavRoute.NAVIGATION_MAIN_MENU.route,
        startDestination = NavRoute.SCREEN_START.route
    ) {

        composable(route = NavRoute.SCREEN_START.route) {
            StartScreen(navController = navController)
        }

        composable(route = NavRoute.SCREEN_LANGUAGE.route) {
            LanguageScreen(
                navController = navController,
                globalPreferencesViewModel = globalPreferencesViewModel
            )
        }

        composable(route = NavRoute.SCREEN_APP_INFO.route) {
            InfoScreen(
                navController = navController,
                mainMenuViewModel = mainMenuViewModel
            )
        }

        composable(route = NavRoute.SCREEN_SETTINGS.route) {
            SettingsScreen(
                navController = navController,
                globalPreferencesViewModel = globalPreferencesViewModel,
                permissionsViewModel = permissionsViewModel
            )
        }

        navigation(
            route = NavRoute.NAVIGATION_NEWSLETTER.route,
            startDestination = NavRoute.SCREEN_NEWSLETTER_INBOX.route
        ) {

            composable(route = NavRoute.SCREEN_NEWSLETTER_INBOX.route) {
                NewsInboxesScreen(
                    navController = navController,
                    newsletterViewModel = newsletterViewModel
                )
            }

            composable(
                route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGES.route}/{inboxID}",
                arguments = listOf(
                    navArgument("inboxID") { type = NavType.StringType }
                )) { navBackStackEntry ->
                val inboxID = navBackStackEntry.arguments?.getString("inboxID")

                if (inboxID != null) {
                    NewsMessagesScreen(
                        navController = navController,
                        newsletterViewModel = newsletterViewModel,
                        inboxID = inboxID
                    )
                } else {
                    navController.popBackStack()
                }

            }

            composable(
                route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGE.route}/{inboxID}/{messageID}",
                arguments = listOf(
                    navArgument("inboxID") { type = NavType.StringType },
                    navArgument("messageID") { type = NavType.StringType }
                )) { navBackStackEntry ->

                val inboxID = navBackStackEntry.arguments?.getString("inboxID")
                val messageID = navBackStackEntry.arguments?.getString("messageID")

                if (inboxID != null && messageID != null) {
                    NewsMessageScreen(
                        navController = navController,
                        newsletterViewModel = newsletterViewModel,
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
                AccountScreen(
                    navController = navController,
                    accountViewModel = accountViewModel
                )
            }

            composable(route = NavRoute.SCREEN_MARKETPLACE_UNLOCKS.route) {
                MarketplaceScreen()
            }

            composable(route = NavRoute.SCREEN_MARKETPLACE_BILLABLE.route) {
                MarketplaceBillingScreen()
            }

        }
    }
}

private fun NavGraphBuilder.investigationNavigation(
    navController: NavHostController,
    investigationViewModel: InvestigationViewModel,
    objectivesViewModel: ObjectivesViewModel,
    mapsViewModel: MapsViewModel,
    codexViewModel: CodexViewModel
) {
    navigation(
        route = NavRoute.NAVIGATION_INVESTIGATION.route,
        startDestination = NavRoute.SCREEN_INVESTIGATION.route
    ) {

        composable(route = NavRoute.SCREEN_INVESTIGATION.route) {
            InvestigationSoloScreen(
                navController = navController,
                investigationViewModel = investigationViewModel
            )
        }

        composable(route = NavRoute.SCREEN_MISSIONS.route) {
            ObjectivesScreen(
                navController = navController,
                objectivesViewModel = objectivesViewModel,
                investigationViewModel = investigationViewModel
            )
        }

        navigation(
            route = NavRoute.NAVIGATION_MAPS.route,
            startDestination = NavRoute.SCREEN_MAPS_MENU.route
        ) {

            composable(route = NavRoute.SCREEN_MAPS_MENU.route) {
                MapMenuScreen(
                    navController = navController,
                    mapsViewModel = mapsViewModel
                )
            }

            composable(route = "${NavRoute.SCREEN_MAP_VIEWER.route}/{mapId}",
                arguments = listOf(
                    navArgument("mapId") { type = NavType.StringType }
                )) { navBackStackEntry ->

                val mapId = navBackStackEntry.arguments?.getString("mapId")

                Log.d("MainNavigation", "mapId: $mapId")

                if(mapId != null) {
                    MapViewerScreen(
                        navController = navController,
                        mapsViewModel = mapsViewModel,
                        mapId = mapId
                    )
                } else {
                    navController.popBackStack()
                }
            }

        }

        navigation(
            route = NavRoute.NAVIGATION_CODEX.route,
            startDestination = NavRoute.SCREEN_CODEX_MENU.route
        ) {

            composable(route = NavRoute.SCREEN_CODEX_MENU.route) {
                CodexMenuScreen(
                    navController = navController
                )
            }

            composable(
                route = "${NavRoute.SCREEN_CODEX_ITEM_SCREEN.route}/{categoryId}",
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.IntType }
                )) { navBackStackEntry ->

                val categoryId = navBackStackEntry.arguments?.getInt("categoryId")

                Log.d("MainNavigation", "categoryId: $categoryId")

                val category = CodexResources.Category.entries.firstOrNull { entry ->
                    entry.id == categoryId }

                category?.let {
                    CodexItemstoreScreen(
                        navController = navController,
                        codexViewModel = codexViewModel,
                        category = category
                    )
                } ?: navController.popBackStack()

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
    SCREEN_CODEX_ITEM_SCREEN("CodexItemstoreScreen"),
    
    SCREEN_NEWSLETTER_INBOX("NewsInboxScreen"),
    SCREEN_NEWSLETTER_MESSAGES("NewsMessagesScreen"),
    SCREEN_NEWSLETTER_MESSAGE("NewsMessageScreen"),
    
    SCREEN_MARKETPLACE_UNLOCKS("MarketplaceUnlocksScreen"),
    SCREEN_MARKETPLACE_BILLABLE("MarketplaceBillableScreen")

}