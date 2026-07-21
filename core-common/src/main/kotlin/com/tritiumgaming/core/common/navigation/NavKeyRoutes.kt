package com.tritiumgaming.core.common.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavKeyRoute : NavKey

// Home Navigation
@Serializable data object HomeNavigation : NavKeyRoute
@Serializable data object MarketplaceNavigation : NavKeyRoute
@Serializable data object NewsletterNavigation : NavKeyRoute

@Serializable data object AccountOverviewScreen : NavKeyRoute
@Serializable data object AppInfoScreen : NavKeyRoute
@Serializable data object LanguageScreen : NavKeyRoute
@Serializable data object SettingsScreen : NavKeyRoute
@Serializable data object HomeScreen : NavKeyRoute

@Serializable data object NewsletterInboxScreen : NavKeyRoute
@Serializable data class NewsletterMessagesScreen(val inboxID: String) : NavKeyRoute
@Serializable data class NewsletterMessageScreen(val inboxID: String, val messageID: String) : NavKeyRoute

@Serializable data object MarketplaceHomeScreen : NavKeyRoute
@Serializable data object MarketplaceUnlocksScreen : NavKeyRoute
@Serializable data object MarketplaceTypographyScreen : NavKeyRoute
@Serializable data object MarketplaceBillableScreen : NavKeyRoute

// Operation Navigation
@Serializable data object OperationNavigation : NavKeyRoute
@Serializable data object MapsNavigation : NavKeyRoute
@Serializable data object CodexNavigation : NavKeyRoute

@Serializable data object InvestigationScreen : NavKeyRoute
@Serializable data object ObjectiveBoardScreen : NavKeyRoute
@Serializable data object CustomDifficultyScreen : NavKeyRoute
@Serializable data object MapsMenuScreen : NavKeyRoute
@Serializable data class MapViewerScreen(val mapId: String) : NavKeyRoute

@Serializable data object CodexMenuScreen : NavKeyRoute
@Serializable data class CodexItemScreen(val categoryId: Int) : NavKeyRoute
