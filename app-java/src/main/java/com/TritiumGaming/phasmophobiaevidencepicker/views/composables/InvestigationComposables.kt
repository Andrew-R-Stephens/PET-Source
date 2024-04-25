package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.TritiumGaming.phasmophobiaevidencepicker.R

@Composable
@Preview
fun InvestigationRuling(
    rulingType: Int = 0,
    rulingState: Boolean = false
) {

    val drawable = R.drawable.icon_selector_inc_sel

    Box(
        modifier = Modifier.size(48.dp)
    ) {
        Image(
            painterResource(id = R.drawable.icon_ts_news),
            contentDescription = ""
        )

        Image(
            painterResource(id = R.drawable.icon_ts_notify),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(fraction = .5f)
                .align(Alignment.BottomEnd)
        )
    }
    
}

/*
<item>
<shape>
<solid android:color="@color/transparent"/>
<size android:height="48dp" android:width="48dp"/>
</shape>
</item>

<item
android:left="1dp"
android:top="1dp"
android:right="1dp"
android:bottom="1dp">
<level-list>
<item
android:minLevel="0"
android:maxLevel="1">
<selector>
<item app:state_selected="true">
<bitmap
android:src="@drawable/icon_ev_negative_sel"
android:tintMode="multiply"
android:tint="?attr/negativeSelColor">
</bitmap>
</item>
<item app:state_selected="false">
<bitmap
android:src="@drawable/icon_ev_negative_unsel"
android:tintMode="multiply"
android:tint="?attr/neutralSelColor">
</bitmap>
</item>
</selector>
</item>
<item
android:minLevel="2"
android:maxLevel="2">
<selector>
<item app:state_selected="true">
<bitmap
android:src="@drawable/icon_ev_inconclusive_sel"
android:tintMode="multiply"
android:tint="?attr/neutralSelColor">
</bitmap>
</item>
<item app:state_selected="false">
<bitmap
android:src="@drawable/icon_ev_inconclusive_unsel"
android:tintMode="multiply"
android:tint="?attr/neutralSelColor"/>
</item>
</selector>
</item>
<item
android:minLevel="3"
android:maxLevel="3">
<selector>
<item app:state_selected="true">
<bitmap
android:src="@drawable/icon_ev_positive_sel"
android:tintMode="multiply"
android:tint="?attr/positiveSelColor">
</bitmap>
</item>
<item app:state_selected="false">
<bitmap
android:src="@drawable/icon_ev_positive_unsel"
android:tintMode="multiply"
android:tint="?attr/neutralSelColor">
</bitmap>
</item>
</selector>
</item>
</level-list>
</item>*/
