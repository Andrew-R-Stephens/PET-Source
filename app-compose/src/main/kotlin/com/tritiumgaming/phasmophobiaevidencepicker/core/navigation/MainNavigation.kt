package com.tritiumgaming.phasmophobiaevidencepicker.core.navigation

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
import com.tritiumgaming.feature.home.ui.account.AccountScreen
import com.tritiumgaming.feature.home.ui.appinfo.AppInfoViewModel
import com.tritiumgaming.feature.home.ui.appinfo.InfoScreen
import com.tritiumgaming.feature.home.ui.applanguages.LanguageScreen
import com.tritiumgaming.feature.home.ui.appsettings.SettingsScreen
import com.tritiumgaming.feature.home.ui.marketplace.MarketplaceScreen
import com.tritiumgaming.feature.home.ui.marketplace.billing.MarketplaceBillingScreen
import com.tritiumgaming.feature.home.ui.newsletter.NewsletterViewModel
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsInboxesScreen
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsMessageScreen
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsMessagesScreen
import com.tritiumgaming.feature.home.ui.startscreen.StartScreen
import com.tritiumgaming.feature.home.ui.account.AccountViewModel
import com.tritiumgaming.feature.home.ui.applanguages.LanguageScreenViewModel
import com.tritiumgaming.feature.operation.ui.codex.CodexViewModel
import com.tritiumgaming.feature.operation.ui.codex.catalog.CodexItemstoreScreen
import com.tritiumgaming.feature.operation.ui.codex.menu.CodexMenuScreen
import com.tritiumgaming.feature.operation.ui.investigation.InvestigationSoloScreen
import com.tritiumgaming.feature.operation.ui.investigation.InvestigationViewModel
import com.tritiumgaming.feature.operation.ui.mapsmenu.MapMenuScreen
import com.tritiumgaming.feature.operation.ui.mapsmenu.MapsViewModel
import com.tritiumgaming.feature.operation.ui.mapsmenu.mapdisplay.MapViewerScreen
import com.tritiumgaming.feature.operation.ui.missions.ObjectivesScreen
import com.tritiumgaming.feature.operation.ui.missions.ObjectivesViewModel
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexResources
import com.tritiumgaming.shared.core.navigation.NavRoute

@Composable
fun RootNavigation(
    /*permissionsViewModel: PermissionsViewModel =
        viewModel(factory = PermissionsViewModel.Factory),*/
    mainMenuViewModel: AppInfoViewModel =
        viewModel(factory = AppInfoViewModel.Factory),
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
        startDestination = NavRoute.NAVIGATION_HOME.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        homeNavigation(
            navController = navController,
            mainMenuViewModel = mainMenuViewModel,
            accountViewModel = accountViewModel,
            newsletterViewModel = newsletterViewModel
        )

        operationNavigation(
            navController = navController,
            investigationViewModel = investigationViewModel,
            objectivesViewModel = objectivesViewModel,
            mapsViewModel = mapsViewModel,
            codexViewModel = codexViewModel
        )

    }
}

private fun NavGraphBuilder.homeNavigation(
    navController: NavHostController,
    mainMenuViewModel: AppInfoViewModel,
    accountViewModel: AccountViewModel,
    newsletterViewModel: NewsletterViewModel
) {

    navigation(
        route = NavRoute.NAVIGATION_HOME.route,
        startDestination = NavRoute.SCREEN_HOME.route
    ) {

        composable(route = NavRoute.SCREEN_HOME.route) {
            StartScreen(navController = navController)
        }

        composable(route = NavRoute.SCREEN_LANGUAGE.route) {
            LanguageScreen(
                navController = navController,
                viewModel = viewModel(factory = LanguageScreenViewModel.Factory)
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
                navController = navController
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

private fun NavGraphBuilder.operationNavigation(
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
