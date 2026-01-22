package com.tritiumgaming.feature.start.app.container

import com.tritiumgaming.shared.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.review.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.shared.data.review.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.IncrementAppTimesOpenedByUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.SetAppTimesOpenedUseCase

class StartContainer(
    //val setupNewsletterUseCase: SetupNewsletterUseCase,
    val getFlowNewsletterDatastoreUseCase: GetFlowNewsletterDatastoreUseCase,
    val getFlowNewsletterInboxesUseCase: GetFlowNewsletterInboxesUseCase,
    val getNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    //val setupGlobalPreferencesUseCase: SetupUserPreferencesUseCase,
    val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    //val initReviewTrackerDataStoreUseCase: SetupReviewTrackerUseCase,
    val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
    val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
    val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
    val incrementAppTimesOpenedUseCase: IncrementAppTimesOpenedByUseCase,
    val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
)
