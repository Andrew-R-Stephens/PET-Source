package com.tritiumgaming.feature.newsletter.app.container

import com.tritiumgaming.shared.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SetupNewsletterUseCase

class NewsletterContainer(
    val setupNewsletterUseCase: SetupNewsletterUseCase,
    val getFlowNewsletterDatastoreUseCase: GetFlowNewsletterDatastoreUseCase,
    val getFlowNewsletterInboxesUseCase: GetFlowNewsletterInboxesUseCase,
    val getNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    val saveNewsletterInboxLastReadDateUseCase: SaveNewsletterInboxLastReadDateUseCase,
)
