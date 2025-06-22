package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.ActionPan
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.ActionZoom
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Arrow60Left
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Arrow60Right
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.ButtonScratched
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Gear
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Globe
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.IconLogoApp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.IconLogoPhasmophobia
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Info
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.News
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Notify
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Person
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Review

@Composable
fun IconResource.ToComposable(
    modifier: Modifier = Modifier
) =
    when (this) {
        IconResource.ICON_LOGO_APP -> IconLogoApp()
    }
