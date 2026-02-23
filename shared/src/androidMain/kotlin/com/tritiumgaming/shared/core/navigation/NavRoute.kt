package com.tritiumgaming.shared.core.navigation

enum class NavRoute(val route: String) {

    // Home Navigation
    NAVIGATION_HOME(route = "HomeNavigation"),
    NAVIGATION_MARKETPLACE(route = "MarketplaceNavigation"),
    NAVIGATION_NEWSLETTER(route = "NewsletterNavigation"),

    SCREEN_ACCOUNT_OVERVIEW(route = "AccountScreen"),
    SCREEN_APP_INFO(route = "InfoScreen"),
    SCREEN_LANGUAGE(route = "LanguageScreen"),
    SCREEN_SETTINGS(route = "SettingsScreen"),
    SCREEN_HOME(route = "HomeScreen"),

    SCREEN_NEWSLETTER_INBOX(route = "NewsInboxScreen"),
    SCREEN_NEWSLETTER_MESSAGES(route = "NewsMessagesScreen"),
    SCREEN_NEWSLETTER_MESSAGE(route = "NewsMessageScreen"),

    SCREEN_MARKETPLACE_UNLOCKS(route = "MarketplaceUnlocksScreen"),
    SCREEN_MARKETPLACE_BILLABLE(route = "MarketplaceBillableScreen"),

    // Operation Navigation
    NAVIGATION_INVESTIGATION(route = "OperationNavigation"),
    NAVIGATION_MAPS (route = "MapsNavigation"),
    NAVIGATION_CODEX (route = "CodexNavigation"),

    SCREEN_INVESTIGATION(route = "InvestigationScreen"),
    SCREEN_MISSIONS(route = "MissionsScreen"),
    SCREEN_MAPS_MENU(route = "MapMenuScreen"),
    SCREEN_MAP_VIEWER(route = "MapViewerScreen"),

    SCREEN_CODEX_MENU(route = "CodexMenuScreen"),
    SCREEN_CODEX_ITEM_SCREEN(route = "CodexItemstoreScreen"),


}