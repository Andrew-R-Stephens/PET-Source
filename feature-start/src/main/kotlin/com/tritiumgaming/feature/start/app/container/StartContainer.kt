package com.tritiumgaming.feature.start.app.container

import com.tritiumgaming.shared.data.challenge.usecase.GetCurrentChallengeUseCase
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
    internal val getFlowNewsletterDatastoreUseCase: GetFlowNewsletterDatastoreUseCase,
    internal val getFlowNewsletterInboxesUseCase: GetFlowNewsletterInboxesUseCase,
    internal val getNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    //val setupGlobalPreferencesUseCase: SetupUserPreferencesUseCase,
    internal val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    //val initReviewTrackerDataStoreUseCase: SetupReviewTrackerUseCase,
    internal val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
    internal val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
    internal val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
    internal val incrementAppTimesOpenedUseCase: IncrementAppTimesOpenedByUseCase,
    internal val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
    internal val getCurrentChallengeUseCase: GetCurrentChallengeUseCase
)
