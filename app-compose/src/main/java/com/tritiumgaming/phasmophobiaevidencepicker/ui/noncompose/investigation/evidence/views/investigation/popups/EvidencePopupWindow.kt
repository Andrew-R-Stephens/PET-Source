package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.evidence.views.investigation.popups

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.text.Html
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.EvidencePopupRepository.EvidencePopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.utils.FontUtils.replaceHTMLFontColor
import pl.droidsonroids.gif.GifImageView

class EvidencePopupWindow : InvestigationPopupWindow {
    private val detailIndex = 0

    constructor(context: Context) :
            super(context) { initView() }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView() }

    override fun initView() {
        super.initView(R.layout.popup_info_evidence)
    }

    fun build(
        investigationViewModel: InvestigationViewModel?,
        evidenceRecord: EvidencePopupRecord,
        adRequest: AdRequest?
    ) {
        // THEME

        var adRequest = adRequest
        @ColorInt val fontEmphasisColor =
            getColorFromAttribute(context, R.attr.textColorBodyEmphasis)

        val closeButton = findViewById<AppCompatImageButton>(R.id.button_right)
        val label = findViewById<AppCompatTextView>(R.id.textView_title)

        val info = findViewById<AppCompatTextView>(R.id.label_info)

        val select_overview = findViewById<AppCompatImageView>(R.id.textView_overview_image)
        val select_tiers = findViewById<AppCompatImageView>(R.id.textView_tiers_image)

        val select_tier_1 = findViewById<AppCompatImageView>(R.id.textView_tiers_1_image)
        val select_tier_2 = findViewById<AppCompatImageView>(R.id.textView_tiers_2_image)
        val select_tier_3 = findViewById<AppCompatImageView>(R.id.textView_tiers_3_image)

        val scroller = findViewById<ScrollView>(R.id.scrollView)
        val indicator = findViewById<View>(R.id.scrollview_indicator)
        val animation = findViewById<GifImageView>(R.id.animation_evidence)
        val animation_fullscreen = findViewById<GifImageView>(R.id.animation_evidence_fullscreen)

        val layout_overview =
            findViewById<ConstraintLayout>(R.id.constraintLayout_evidence_overview)
        val layout_tiers = findViewById<ConstraintLayout>(R.id.constraintLayout_evidence_tiers)
        val label_cost = findViewById<AppCompatTextView>(R.id.label_cost)

        // Init
        label_cost.text = Html.fromHtml(
            replaceHTMLFontColor(
                context.getString(R.string.evidence_requirement_cost_title) + " $" + evidenceRecord.getCost(
                    context
                ),
                "#ff6161", fontEmphasisColor.toString()
            )
        )

        //MAIN STATES
        select_overview.setImageLevel(1)
        select_overview.setOnClickListener {
            select_overview.setImageLevel(1)
            select_tiers.setImageLevel(0)

            layout_overview.visibility = VISIBLE
            layout_tiers.visibility = GONE
            animation_fullscreen.setImageResource(evidenceRecord.getAnimationAt(0))
        }
        select_tiers.setOnClickListener {
            select_overview.setImageLevel(0)
            select_tiers.setImageLevel(1)

            layout_tiers.visibility = VISIBLE
            layout_overview.visibility = GONE

            select_tier_1.setImageLevel(1)
            select_tier_2.setImageLevel(0)
            select_tier_3.setImageLevel(0)
            generateEvidenceTierView(
                1, animation_fullscreen,
                evidenceRecord.getDescriptionAt(context, 1),
                evidenceRecord.getAnimationAt(1),
                evidenceRecord.getUnlockLevelAt(context, 0),
                fontEmphasisColor
            )
        }

        //TIER STATES
        select_tier_1.setImageLevel(1)
        select_tier_1.setOnClickListener {
            select_tier_1.setImageLevel(1)
            select_tier_2.setImageLevel(0)
            select_tier_3.setImageLevel(0)
            generateEvidenceTierView(
                1, animation_fullscreen,
                evidenceRecord.getDescriptionAt(context, 1),
                evidenceRecord.getAnimationAt(1),
                evidenceRecord.getUnlockLevelAt(context, 0),
                fontEmphasisColor
            )
        }
        select_tier_2.setOnClickListener {
            select_tier_2.setImageLevel(1)
            select_tier_1.setImageLevel(0)
            select_tier_3.setImageLevel(0)
            generateEvidenceTierView(
                2, animation_fullscreen,
                evidenceRecord.getDescriptionAt(context, 2),
                evidenceRecord.getAnimationAt(2),
                evidenceRecord.getUnlockLevelAt(context, 1),
                fontEmphasisColor
            )
        }
        select_tier_3.setOnClickListener {
            select_tier_3.setImageLevel(1)
            select_tier_1.setImageLevel(0)
            select_tier_2.setImageLevel(0)
            generateEvidenceTierView(
                3, animation_fullscreen,
                evidenceRecord.getDescriptionAt(context, 3),
                evidenceRecord.getAnimationAt(3),
                evidenceRecord.getUnlockLevelAt(context, 2),
                fontEmphasisColor
            )
        }

        animation.setOnClickListener {
            if (animation_fullscreen.visibility != VISIBLE) {
                animation_fullscreen.visibility = VISIBLE
            }
        }
        animation_fullscreen.setOnClickListener { v: View ->
            if (v.visibility == VISIBLE) {
                v.visibility = GONE
            }
        }

        fadeOutIndicatorAnimation(
            null,
            null,
            scroller,
            indicator
        )

        label.text = evidenceRecord.getName(context)
        info.text = Html.fromHtml(
            replaceHTMLFontColor(
                evidenceRecord.getDescriptionAt(context, 0),
                "#ff6161", fontEmphasisColor.toString()
            )
        )

        val typedArray: TypedArray
        try {
            typedArray = context.resources.obtainTypedArray(R.array.equipment_animation_array)
            animation.setImageResource(evidenceRecord.getAnimationAt(0))
            animation_fullscreen.setImageResource(typedArray.getResourceId(evidenceRecord.index, 0))
            typedArray.recycle()
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        //----
        closeButton.setOnClickListener { popupWindow?.dismiss() }

        popupWindow?.showAtLocation(rootView, Gravity.CENTER_VERTICAL, 0, 0)

        MobileAds.initialize(context) { }
        val mAdView = findViewById<AdView>(R.id.adView)
        adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun generateEvidenceTierView(
        tierIndex: Int, animation_fullscreen: GifImageView,
        description: String?, @DrawableRes animation: Int,
        level: String, @ColorInt fontEmphasisColor: Int
    ) {
        val scrollView = findViewById<ConstraintLayout>(R.id.scrollview_tiers)
        val title = findViewById<AppCompatTextView>(R.id.label_tier)
        val animationView = findViewById<GifImageView>(R.id.animation_tier)

        val details = scrollView.findViewById<AppCompatTextView>(R.id.info_tier)
        val levelView = findViewById<AppCompatTextView>(R.id.label_level)

        details.text = Html.fromHtml(
            replaceHTMLFontColor(
                description,
                "#ff6161", fontEmphasisColor.toString()
            )
        )
        levelView.text = Html.fromHtml(
            replaceHTMLFontColor(
                context.getString(R.string.evidence_requirement_level_title) + " " + level,
                "#ff6161", fontEmphasisColor.toString()
            )
        )

        animationView.setImageResource(animation)
        animation_fullscreen.setImageResource(animation)

        val typedArray =
            resources.obtainTypedArray(R.array.equipment_tiers)
        title.text = typedArray.getString(tierIndex - 1)
        typedArray.recycle()


        animationView.setOnClickListener {
            if (animation_fullscreen.visibility != VISIBLE) {
                animation_fullscreen.visibility = VISIBLE
            }
        }
        animation_fullscreen.setOnClickListener { v: View ->
            if (v.visibility == VISIBLE) {
                v.visibility = GONE
            }
        }
    }

}