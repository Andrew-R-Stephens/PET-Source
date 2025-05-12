package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.popups

import android.content.Context
import android.content.res.Configuration
import android.text.Html
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.GhostPopupModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.views.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.util.FontUtils.replaceHTMLFontColor
import kotlin.math.min

class GhostPopupWindow : InvestigationPopupWindow {

    private var detailIndex = 0

    constructor(context: Context) :
            super(context) { initView() }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView() }

    override fun initView() {
        super.initView(R.layout.popup_info_ghost)
    }

    fun build(
        investigationViewModel: InvestigationViewModel,
        popupModel: GhostPopupModel?,
        groupIndex: Int,
        adRequest: AdRequest?
    ) {
        popupModel ?: return

        var adRequest = adRequest
        val evidenceIconContainer = findViewById<LinearLayoutCompat>(R.id.icon_container)

        val carouselContainer = findViewById<ConstraintLayout>(R.id.scrollView_swapping)
        val huntDataContainer = findViewById<ConstraintLayout>(R.id.scrollView_huntdata)
        val ghostNameTextView = findViewById<AppCompatTextView>(R.id.textView_title)

        val closeButton = findViewById<PETImageButton>(R.id.button_right)
        val bodyCons = findViewById<ConstraintLayout>(R.id.layout_contentbody)
        val left = findViewById<PETImageButton>(R.id.title_left)
        val right = findViewById<PETImageButton>(R.id.title_right)
        val title = findViewById<AppCompatTextView>(R.id.label_infoTitle)

        val carouselScrollView = carouselContainer.findViewById<ScrollView>(R.id.scrollView)
        val carouselIndicator = carouselContainer.findViewById<View>(R.id.scrollview_indicator)
        val carouselTextView = carouselScrollView.findViewById<AppCompatTextView>(R.id.label_info)

        val huntDataScrollView = huntDataContainer.findViewById<ScrollView>(R.id.scrollView)
        val huntDataIndicator = huntDataContainer.findViewById<View>(R.id.scrollview_indicator)
        val huntDataTextView = huntDataScrollView.findViewById<AppCompatTextView>(R.id.label_info)

        val titles = arrayOf(
            resources.getString(R.string.popup_ghost_info),
            resources.getString(R.string.popup_ghost_strength),
            resources.getString(R.string.popup_ghost_weakness)
        )

        investigationViewModel.ghostScores.value[groupIndex].let { ghostScoreModel ->
            val ghostModel = ghostScoreModel.ghostModel
            for (i in ghostModel.evidence.normalEvidenceList.indices) {
                val evidenceIcon = evidenceIconContainer.getChildAt(i) as AppCompatImageView
                ghostModel.evidence.normalEvidenceList[i].let {
                    evidenceIcon.setImageResource(it.icon) }
            }
            ghostNameTextView.text = context.getString(ghostModel.name)
        }
        
        //initialize info content scroller
        bodyCons.visibility = INVISIBLE

        val tempList: List<Int> = popupModel.getCycleDetails()

        val body = context.getString(tempList[detailIndex])

        // THEME
        @ColorInt val fontEmphasisColor =
            getColorFromAttribute(context, R.attr.textColorBodyEmphasis)

        title.text = titles[detailIndex]
        carouselTextView.text = Html.fromHtml(
            replaceHTMLFontColor(body, "#ff6161", fontEmphasisColor.toString()))
        huntDataTextView.text = Html.fromHtml(
            replaceHTMLFontColor(context.getString(popupModel.huntInfo),
                "#ff6161", fontEmphasisColor.toString()))

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            huntDataContainer.post {
                huntDataContainer.maxHeight = (bodyCons.height * .4f).toInt()
                fadeOutIndicatorAnimation(
                    huntDataContainer, huntDataContainer, huntDataScrollView, huntDataIndicator) }
        }

        fun invalidateContent() {
            title.text = titles[detailIndex]
            carouselTextView.text = Html.fromHtml(replaceHTMLFontColor(
                body, "#ff6161", fontEmphasisColor.toString()))
            fadeOutIndicatorAnimation(
                bodyCons, carouselContainer, carouselScrollView, carouselIndicator)
        }

        left.setOnClickListener {
            detailIndex = min(
                (((detailIndex - 1) % tempList.size) and (tempList.size)).toDouble(),
                (tempList.size - 1).toDouble()
            ).toInt()

            invalidateContent()
        }

        right.setOnClickListener {
            detailIndex = (detailIndex + 1) % tempList.size

            invalidateContent()
        }

        fadeOutIndicatorAnimation(
            bodyCons, carouselContainer, carouselScrollView, carouselIndicator)

        closeButton.setOnClickListener { popupWindow?.dismiss() }

        popupWindow?.showAtLocation(rootView, Gravity.CENTER_VERTICAL, 0, 0)

        MobileAds.initialize(context) { }
        val mAdView = findViewById<AdView>(R.id.adView)
        adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

}
