package com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.popups

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
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.investigationmodels.InvestigationModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostPopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.utils.FontUtils.replaceHTMLFontColor
import com.tritiumgaming.phasmophobiaevidencepicker.views.global.PETImageButton
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

    fun build(investigationModel: InvestigationModel, ghostPopupData: GhostPopupRepository,
              groupIndex: Int, adRequest: AdRequest?
    ) {
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

        investigationModel.ghostRepository.getAt(groupIndex).let { ghostModel ->
            for (i in ghostModel.normalEvidenceList.indices) {
                val evidenceIcon = evidenceIconContainer.getChildAt(i) as AppCompatImageView
                ghostModel.normalEvidenceList[i].let { evidenceIcon.setImageResource(it.icon) }
            }
            ghostNameTextView.text = context.getString(ghostModel.name)
        }
        
        //initialize info content scroller
        bodyCons.visibility = INVISIBLE

        val tempList: List<String> = (ghostPopupData.getCycleDetails(context, groupIndex))
        val cycleDetails = tempList.toTypedArray<String>()

        // THEME
        @ColorInt val fontEmphasisColor =
            getColorFromAttribute(context, R.attr.textColorBodyEmphasis)

        title.text = titles[detailIndex]
        carouselTextView.text = Html.fromHtml(
            replaceHTMLFontColor(cycleDetails[detailIndex],
            "#ff6161", fontEmphasisColor.toString()))
        huntDataTextView.text = Html.fromHtml(
            replaceHTMLFontColor(context.getString(ghostPopupData.getHuntData(groupIndex)),
                "#ff6161", fontEmphasisColor.toString()))

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            huntDataContainer.post {
                huntDataContainer.maxHeight = (bodyCons.height * .4f).toInt()
                fadeOutIndicatorAnimation(
                    huntDataContainer, huntDataContainer, huntDataScrollView, huntDataIndicator) }
        }

        left.setOnClickListener {
            detailIndex = min(
                (((detailIndex - 1) % cycleDetails.size) and (cycleDetails.size)).toDouble(),
                (cycleDetails.size - 1).toDouble()
            ).toInt()

            title.text = titles[detailIndex]
            carouselTextView.text = Html.fromHtml(replaceHTMLFontColor(
                    cycleDetails[detailIndex], "#ff6161", fontEmphasisColor.toString()))

            fadeOutIndicatorAnimation(
                bodyCons, carouselContainer, carouselScrollView, carouselIndicator)
        }

        right.setOnClickListener {
            detailIndex = (detailIndex + 1) % cycleDetails.size
            title.text = titles[detailIndex]
            carouselTextView.text = Html.fromHtml(replaceHTMLFontColor(
                    cycleDetails[detailIndex], "#ff6161", fontEmphasisColor.toString()))
            fadeOutIndicatorAnimation(
                bodyCons, carouselContainer, carouselScrollView, carouselIndicator)
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
