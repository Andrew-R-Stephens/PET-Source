package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.popups

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.Gravity
import android.view.View
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups.GhostPopupModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.utils.FontUtils.replaceHTMLFontColor
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
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
        evidenceViewModel: EvidenceViewModel,
        ghostPopupData: GhostPopupModel,
        groupIndex: Int, adRequest: AdRequest?
    ) {
        var adRequest = adRequest
        val linearLayout_iconRow = findViewById<LinearLayoutCompat>(R.id.icon_container)

        val scrollCons_swapping =
            findViewById<ConstraintLayout>(R.id.scrollView_swapping)
        val scrollCons_huntdata =
            findViewById<ConstraintLayout>(R.id.scrollView_huntdata)
        val label_name =
            findViewById<AppCompatTextView>(R.id.textView_title)

        val closeButton = findViewById<PETImageButton>(R.id.button_right)
        val bodyCons = findViewById<ConstraintLayout>(R.id.layout_contentbody)
        val left = findViewById<PETImageButton>(R.id.title_left)
        val right = findViewById<PETImageButton>(R.id.title_right)
        val title = findViewById<AppCompatTextView>(R.id.label_infoTitle)

        val scroller_swapping = scrollCons_swapping.findViewById<ScrollView>(R.id.scrollView)
        val indicator_swapping = scrollCons_swapping.findViewById<View>(R.id.scrollview_indicator)
        val data_swapping = scroller_swapping.findViewById<AppCompatTextView>(R.id.label_info)

        val scroller_huntdata = scrollCons_huntdata.findViewById<ScrollView>(R.id.scrollView)
        val indicator_huntdata = scrollCons_huntdata.findViewById<View>(R.id.scrollview_indicator)
        val data_huntdata = scroller_huntdata.findViewById<AppCompatTextView>(R.id.label_info)


        val titles = arrayOf(
            resources.getString(R.string.popup_ghost_info),
            resources.getString(R.string.popup_ghost_strength),
            resources.getString(R.string.popup_ghost_weakness)
        )

        // THEME
        @ColorInt val fontEmphasisColor = getColorFromAttribute(
            context, R.attr.textColorBodyEmphasis
        )


        for (i in evidenceViewModel.investigationData!!.ghostList
            .getAt(groupIndex)
            .evidenceArray.indices) {
            val evidenceIcon =
                linearLayout_iconRow.getChildAt(i) as AppCompatImageView

            evidenceIcon.setImageResource(
                evidenceViewModel.investigationData!!.ghostList
                    .getAt(groupIndex).evidence[i].icon
            )
        }

        label_name.text = evidenceViewModel.investigationData!!.ghostList
            .getAt(groupIndex).name

        //initialize info content scroller
        bodyCons.visibility = INVISIBLE

        val tempList: List<String> = (ghostPopupData.getCycleDetails(
            context, groupIndex
        ))
        val cycleDetails = tempList.toTypedArray<String>()

        title.text = titles[detailIndex]
        data_swapping.text = Html.fromHtml(
            replaceHTMLFontColor(
                cycleDetails[detailIndex],
                "#ff6161", fontEmphasisColor.toString()
            )
        )
        data_huntdata.text = Html.fromHtml(
            replaceHTMLFontColor(
                context.getString(ghostPopupData.getHuntData(groupIndex)),
                "#ff6161", fontEmphasisColor.toString()
            )
        )

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            scrollCons_huntdata.post {
                scrollCons_huntdata.maxHeight = (bodyCons.height * .4f).toInt()
                fadeOutIndicatorAnimation(
                    scrollCons_huntdata,
                    scrollCons_huntdata,
                    scroller_huntdata,
                    indicator_huntdata
                )
            }
        }

        left.setOnClickListener {
            detailIndex = min(
                (((detailIndex - 1) % cycleDetails.size) and (cycleDetails.size)).toDouble(),
                (cycleDetails.size - 1).toDouble()
            )
                .toInt()
            title.text = titles[detailIndex]
            data_swapping.text = Html.fromHtml(
                replaceHTMLFontColor(
                    cycleDetails[detailIndex],
                    "#ff6161", fontEmphasisColor.toString()
                )
            )
            fadeOutIndicatorAnimation(
                bodyCons,
                scrollCons_swapping,
                scroller_swapping,
                indicator_swapping
            )
        }

        right.setOnClickListener {
            detailIndex = (detailIndex + 1) % cycleDetails.size
            title.text = titles[detailIndex]
            data_swapping.text = Html.fromHtml(
                replaceHTMLFontColor(
                    cycleDetails[detailIndex],
                    "#ff6161", fontEmphasisColor.toString()
                )
            )
            fadeOutIndicatorAnimation(
                bodyCons,
                scrollCons_swapping,
                scroller_swapping,
                indicator_swapping
            )
        }

        fadeOutIndicatorAnimation(
            bodyCons,
            scrollCons_swapping,
            scroller_swapping,
            indicator_swapping
        )

        closeButton.setOnClickListener { popupWindow!!.dismiss() }

        popupWindow!!.showAtLocation(rootView, Gravity.CENTER_VERTICAL, 0, 0)

        MobileAds.initialize(context) { }
        val mAdView = findViewById<AdView>(R.id.adView)
        adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    /*
    fun fadeOutIndicatorAnimation(
        bodyCons: ConstraintLayout?,
        container: ConstraintLayout?,
        scroller: ScrollView,
        indicator: View
    ) {
        scroller.post {
            if (!scroller.canScrollVertically(1)) {
                indicator.visibility = INVISIBLE
                indicatorFadeAnimation(indicator, 0)
            } else {
                if (container != null) {
                    if (container.layoutParams is LayoutParams) {
                        val lParams = container.layoutParams as LayoutParams
                        Log.d("Scroller", "Should constrain")
                        lParams.constrainedHeight = true
                        container.layoutParams = lParams
                        container.invalidate()

                        if (!scroller.canScrollVertically(1)) {
                            indicator.visibility = INVISIBLE

                            indicatorFadeAnimation(indicator, 0)
                        } else {
                            indicator.visibility = VISIBLE
                            indicator.alpha = 1f
                        }
                    }
                }
            }
            if (bodyCons != null) {
                //initialize info content scroller
                bodyCons.visibility = VISIBLE
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroller.setOnScrollChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int ->
                if (!scroller.canScrollVertically(1)) {
                    indicatorFadeAnimation(
                        indicator, resources.getInteger(
                            android.R.integer.config_longAnimTime
                        )
                    )
                }
            }
        } else {
            scroller.setOnDragListener { _: View?, _: DragEvent? ->
                if (!scroller.canScrollVertically(1)) {
                    indicatorFadeAnimation(
                        indicator, resources.getInteger(
                            android.R.integer.config_longAnimTime
                        )
                    )
                }
                true
            }
        }
    }

    fun indicatorFadeAnimation(indicator: View, time: Int) {
        indicator.animate()
            .alpha(0f)
            .setDuration(time.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    indicator.visibility = INVISIBLE
                }
            })
    }*/
}
