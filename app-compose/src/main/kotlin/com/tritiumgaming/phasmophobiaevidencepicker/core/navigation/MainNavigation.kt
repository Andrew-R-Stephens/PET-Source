package com.tritiumgaming.phasmophobiaevidencepicker.core.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.tritiumgaming.feature.home.ui.account.AccountScreenViewModel
import com.tritiumgaming.feature.home.ui.appinfo.AppInfoViewModel
import com.tritiumgaming.feature.home.ui.appinfo.InfoScreen
import com.tritiumgaming.feature.home.ui.applanguages.LanguageScreen
import com.tritiumgaming.feature.home.ui.applanguages.LanguageScreenViewModel
import com.tritiumgaming.feature.home.ui.appsettings.SettingsScreen
import com.tritiumgaming.feature.home.ui.marketplace.MarketplaceScreen
import com.tritiumgaming.feature.home.ui.marketplace.billing.MarketplaceBillingScreen
import com.tritiumgaming.feature.home.ui.newsletter.NewsletterViewModel
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsInboxesScreen
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsMessageScreen
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsMessagesScreen
import com.tritiumgaming.feature.home.ui.startscreen.StartScreen
import com.tritiumgaming.feature.operation.ui.OperationScreen
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexResources
import com.tritiumstudios.feature.codex.ui.CodexViewModel
import com.tritiumstudios.feature.codex.ui.catalog.CodexCatalogScreen
import com.tritiumstudios.feature.codex.ui.menu.CodexMenuScreen
import com.tritiumstudios.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumstudios.feature.investigation.ui.InvestigationSoloScreen
import com.tritiumstudios.feature.maps.ui.MapMenuScreen
import com.tritiumstudios.feature.maps.ui.MapsScreenViewModel
import com.tritiumstudios.feature.maps.ui.mapdisplay.MapViewerScreen
import com.tritiumstudios.feature.missions.ui.DifficultyUiState
import com.tritiumstudios.feature.missions.ui.ObjectivesScreen
import com.tritiumstudios.feature.missions.ui.ObjectivesViewModel

@Composable
fun RootNavigation(
    newsletterViewModel: NewsletterViewModel =
        viewModel(factory = NewsletterViewModel.Factory),
    investigationViewModel: InvestigationScreenViewModel =
        viewModel(factory = InvestigationScreenViewModel.Factory),
    objectivesViewModel: ObjectivesViewModel =
        viewModel(factory = ObjectivesViewModel.Factory),
    mapsScreenViewModel: MapsScreenViewModel =
        viewModel(factory = MapsScreenViewModel.Factory),
    windowInsets: WindowInsets = WindowInsets()
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
            newsletterViewModel = newsletterViewModel
        )

        operationNavigation(
            navController = navController,
            windowInsets = windowInsets,
            investigationViewModel = investigationViewModel,
            objectivesViewModel = objectivesViewModel,
            mapsScreenViewModel = mapsScreenViewModel
        )

    }
}

private fun NavGraphBuilder.homeNavigation(
    navController: NavHostController,
    newsletterViewModel: NewsletterViewModel
) {

    navigation(
        route = NavRoute.NAVIGATION_HOME.route,
        startDestination = NavRoute.SCREEN_HOME.route
    ) {

        composable(route = NavRoute.SCREEN_HOME.route) {
            StartScreen(
                newsletterViewModel = newsletterViewModel,
                navController = navController
            )
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
                viewModel = viewModel(factory = AppInfoViewModel.Factory)
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
                    accountViewModel = viewModel(factory = AccountScreenViewModel.Factory)
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
    windowInsets: WindowInsets,
    investigationViewModel: InvestigationScreenViewModel,
    objectivesViewModel: ObjectivesViewModel,
    mapsScreenViewModel: MapsScreenViewModel
) {
    navigation(
        route = NavRoute.NAVIGATION_INVESTIGATION.route,
        startDestination = NavRoute.SCREEN_INVESTIGATION.route
    ) {

        composable(route = NavRoute.SCREEN_INVESTIGATION.route) {
            OperationScreen(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                navController = navController,
                windowInsets = windowInsets
            ) {
                InvestigationSoloScreen(
                    navController = navController,
                    investigationViewModel = investigationViewModel
                )
            }
        }

        composable(route = NavRoute.SCREEN_MISSIONS.route) {
            OperationScreen(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                navController = navController,
                windowInsets = windowInsets
            ) {
                val collect by investigationViewModel.difficultyUiState.collectAsStateWithLifecycle()

                ObjectivesScreen(
                    navController = navController,
                    objectivesViewModel = objectivesViewModel,
                    difficultyUiState = DifficultyUiState(collect.responseType)
                )
            }
        }

        navigation(
            route = NavRoute.NAVIGATION_MAPS.route,
            startDestination = NavRoute.SCREEN_MAPS_MENU.route
        ) {

            composable(route = NavRoute.SCREEN_MAPS_MENU.route) {
                OperationScreen(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    navController = navController,
                    windowInsets = windowInsets
                ) {
                    MapMenuScreen(
                        navController = navController,
                        mapsScreenViewModel = mapsScreenViewModel
                    )
                }
            }

            composable(route = "${NavRoute.SCREEN_MAP_VIEWER.route}/{mapId}",
                arguments = listOf(
                    navArgument("mapId") { type = NavType.StringType }
                )) { navBackStackEntry ->

                val mapId = navBackStackEntry.arguments?.getString("mapId")

                Log.d("MainNavigation", "mapId: $mapId")

                if(mapId != null) {
                    OperationScreen(
                        navController = navController,
                        windowInsets = windowInsets
                    ) {
                        MapViewerScreen(
                            navController = navController,
                            mapsScreenViewModel = mapsScreenViewModel,
                            mapId = mapId
                        )
                    }
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
                OperationScreen(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    navController = navController,
                    windowInsets = windowInsets
                ) {
                    CodexMenuScreen(
                        navController = navController
                    )
                }
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
                    OperationScreen(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        navController = navController,
                        windowInsets = windowInsets
                    ) {
                        CodexCatalogScreen(
                            navController = navController,
                            codexViewModel = viewModel(factory = CodexViewModel.Factory),
                            category = category
                        )
                    }
                } ?: navController.popBackStack()

            }

        }

    }
}
