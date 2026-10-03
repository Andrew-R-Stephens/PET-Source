package com.tritiumgaming.feature.start.app.container

import com.tritiumgaming.data.challenge.usecase.GetCurrentChallengeUseCase
import com.tritiumgaming.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.data.review.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.data.review.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.data.review.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.data.review.usecase.timesopened.IncrementAppTimesOpenedByUseCase
import com.tritiumgaming.data.review.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.data.usecase.SetAllowIntroductionUseCase

class StartContainer(
    internal val getFlowNewsletterDatastoreUseCase: GetFlowNewsletterDatastoreUseCase,
    internal val getFlowNewsletterInboxesUseCase: GetFlowNewsletterInboxesUseCase,
    internal val getNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    internal val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    internal val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
    internal val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
    internal val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
    internal val incrementAppTimesOpenedUseCase: IncrementAppTimesOpenedByUseCase,
    internal val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
    internal val getCurrentChallengeUseCase: GetCurrentChallengeUseCase
)
