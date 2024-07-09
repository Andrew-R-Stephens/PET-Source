package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.SanityToolsLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.InvestigationSection
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists.EvidenceListView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists.GhostListView

open class EvidenceFragment(layout: Int) : InvestigationFragment(layout) {
    protected var ghostSection: InvestigationSection? = null
    protected var evidenceSection: InvestigationSection? = null

    protected var ghostList: GhostListView? = null
    protected var evidenceList: EvidenceListView? = null

    protected var toggleCollapseButton: AppCompatImageView? = null
    protected var sanityTrackingConstraintLayout: ConstraintLayout? = null

    protected var sanityToolsLayout: SanityToolsLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        return inflater.inflate(R.layout.fragment_evidence, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sanityToolsLayout = view.findViewById(R.id.layout_sanity_tool)
        investigationViewModel?.let { investigationViewModel ->
            sanityToolsLayout?.init(investigationViewModel)
        }

        // GHOST / EVIDENCE CONTAINERS
        val columnLeft = view.findViewById<FrameLayout>(R.id.column_left)
        val columnRight = view.findViewById<FrameLayout>(R.id.column_right)
        columnRight.findViewById<View>(R.id.scrollview).verticalScrollbarPosition =
            View.SCROLLBAR_POSITION_RIGHT

        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            /*investigationViewModel?.phaseWarnModel?.flashTimeMax =
                globalPreferencesViewModel.huntWarningFlashTimeMax.toLong()
            */
            if (!globalPreferencesViewModel.isLeftHandSupportEnabled) {
                ghostSection = columnLeft.getChildAt(0) as InvestigationSection
                evidenceSection = columnRight.getChildAt(0) as InvestigationSection
            } else {
                evidenceSection = columnLeft.getChildAt(0) as InvestigationSection
                ghostSection = columnRight.getChildAt(0) as InvestigationSection
            }
        }

        ghostSection?.setLabel(getString(R.string.investigation_section_title_ghosts))
        evidenceSection?.setLabel(getString(R.string.investigation_section_title_evidence))

        val ghostScrollview = ghostSection?.findViewById<ScrollView>(R.id.scrollview)
        val evidenceScrollview = evidenceSection?.findViewById<ScrollView>(R.id.scrollview)

        ghostList = GhostListView(requireContext())
        evidenceList = EvidenceListView(requireContext())

        ghostList?.init(globalPreferencesViewModel, investigationViewModel,
            popupWindow, ghostSection?.findViewById(R.id.progressbar), adRequest)
        evidenceList?.init(globalPreferencesViewModel, investigationViewModel,
            popupWindow, evidenceSection?.findViewById(R.id.progressbar),
            adRequest, ghostList)

        val listGhosts = ghostSection?.getList()
        val listEvidence = evidenceSection?.getList()

        ghostScrollview?.removeView(listGhosts)
        ghostScrollview?.addView(ghostList)

        evidenceScrollview?.removeView(listEvidence)
        evidenceScrollview?.addView(evidenceList)

        toggleCollapseButton = view.findViewById(R.id.button_toggleSanity)

        // SANITY COLLAPSIBLE
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking)

        toggleCollapseButton?.let { toggleCollapseButton ->
            toggleCollapseButton.setOnClickListener {
                when (investigationViewModel?.isDrawerCollapsed == true) {
                    true ->
                        sanityTrackingConstraintLayout?.animate()
                        ?.setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator) {
                                super.onAnimationStart(animation)
                                sanityTrackingConstraintLayout?.visibility = View.GONE
                            }
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                sanityTrackingConstraintLayout?.visibility = View.VISIBLE
                                toggleCollapseButton.setImageLevel(2)
                                investigationViewModel?.isDrawerCollapsed = false
                            }
                        })?.start()
                    false -> {
                        sanityTrackingConstraintLayout?.animate()
                        ?.setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator) {
                                super.onAnimationStart(animation)
                                sanityTrackingConstraintLayout?.visibility = View.VISIBLE
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationStart(animation)
                                sanityTrackingConstraintLayout?.visibility = View.GONE
                                toggleCollapseButton.setImageLevel(1)
                                investigationViewModel?.isDrawerCollapsed = true
                            }
                        })?.start()
                    }
                }
            }

            initCollapsible()
        }

        popupWindow = PopupWindow(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        ghostList?.createPopupWindow(popupWindow)
        evidenceList?.createPopupWindow(popupWindow)
        Thread { ghostList?.createViews() }.start()
        Thread { evidenceList?.createViews() }.start()
    }

    private fun initCollapsible() {
        if (investigationViewModel?.isDrawerCollapsed == true) {
            sanityTrackingConstraintLayout?.visibility = View.GONE
            toggleCollapseButton?.setImageLevel(1)
        } else {
            sanityTrackingConstraintLayout?.visibility = View.VISIBLE
            toggleCollapseButton?.setImageLevel(2)
        }
    }

    override fun reset() {
        investigationViewModel?.reset()
        objectivesViewModel?.reset()

        // TODO Force progress bar update
    }

    fun requestInvalidateComponents() {

        investigationViewModel?.investigationModel?.ghostOrderModel?.updateOrder()

        ghostList?.reset()


        // TODO Force progress bar reset


        // TODO Reset Play/Pause button (to 'Play' state)
    }

    override fun onDestroyView() {
        popupWindow?.dismiss()
        popupWindow = null

        super.onDestroyView()
    }

    override fun saveStates() { }
}
