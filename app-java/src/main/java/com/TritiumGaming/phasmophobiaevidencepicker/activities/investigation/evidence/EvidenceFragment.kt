package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_evidence, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sanityToolsLayout = view.findViewById(R.id.layout_sanity_tool)
        sanityToolsLayout?.init(evidenceViewModel)

        evidenceViewModel.sanityModel?.flashTimeoutMax = 
            globalPreferencesViewModel.huntWarningFlashTimeout.toLong()

        // GHOST / EVIDENCE CONTAINERS
        val columnLeft = view.findViewById<FrameLayout>(R.id.column_left)
        val columnRight = view.findViewById<FrameLayout>(R.id.column_right)
        columnRight.findViewById<View>(R.id.scrollview).verticalScrollbarPosition =
            View.SCROLLBAR_POSITION_RIGHT

        if (!globalPreferencesViewModel.isLeftHandSupportEnabled) {
            ghostSection = columnLeft.getChildAt(0) as InvestigationSection
            evidenceSection = columnRight.getChildAt(0) as InvestigationSection
        } else {
            evidenceSection = columnLeft.getChildAt(0) as InvestigationSection
            ghostSection = columnRight.getChildAt(0) as InvestigationSection
        }

        ghostSection?.setLabel(getString(R.string.investigation_section_title_ghosts))
        evidenceSection?.setLabel(getString(R.string.investigation_section_title_evidence))

        val ghostScrollview = ghostSection?.findViewById<ScrollView>(R.id.scrollview)
        val evidenceScrollview = evidenceSection?.findViewById<ScrollView>(R.id.scrollview)

        ghostList = GhostListView(requireContext())
        evidenceList = EvidenceListView(requireContext())

        ghostList?.init(
            globalPreferencesViewModel, evidenceViewModel,
            popupWindow, ghostSection?.findViewById(R.id.progressbar),
            adRequest
        )
        evidenceList?.init(
            globalPreferencesViewModel, evidenceViewModel,
            popupWindow, evidenceSection?.findViewById(R.id.progressbar),
            adRequest, ghostList
        )

        val listGhosts = ghostSection?.findViewById<ViewStub>(R.id.list)
        val listEvidence = evidenceSection?.findViewById<ViewStub>(R.id.list)

        ghostScrollview?.removeView(listGhosts)
        ghostScrollview?.addView(ghostList)

        evidenceScrollview?.removeView(listEvidence)
        evidenceScrollview?.addView(evidenceList)

        toggleCollapseButton = view.findViewById(R.id.button_toggleSanity)

        // SANITY COLLAPSIBLE
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking)

        if (toggleCollapseButton != null) {
            toggleCollapseButton?.setOnClickListener {
                if (evidenceViewModel.isDrawerCollapsed) {
                    sanityTrackingConstraintLayout?.animate()
                        ?.setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator) {
                                super.onAnimationStart(animation)

                                sanityTrackingConstraintLayout?.visibility = View.GONE
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)

                                sanityTrackingConstraintLayout?.visibility = View.VISIBLE
                                toggleCollapseButton?.setImageLevel(2)
                                evidenceViewModel.isDrawerCollapsed = false
                            }
                        })
                        ?.start()
                } else {
                    sanityTrackingConstraintLayout?.animate()
                        ?.setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator) {
                                super.onAnimationStart(animation)

                                sanityTrackingConstraintLayout?.visibility = View.VISIBLE
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationStart(animation)

                                sanityTrackingConstraintLayout?.visibility = View.GONE
                                toggleCollapseButton?.setImageLevel(1)
                                evidenceViewModel.isDrawerCollapsed = true
                            }
                        })
                        ?.start()
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
        if (evidenceViewModel.isDrawerCollapsed) {
            sanityTrackingConstraintLayout?.visibility = View.GONE
            toggleCollapseButton?.setImageLevel(1)
        } else {
            sanityTrackingConstraintLayout?.visibility = View.VISIBLE
            toggleCollapseButton?.setImageLevel(2)
        }
    }

    override fun reset() {
        if (evidenceViewModel != null) {
            evidenceViewModel.reset()
        }

        // TODO Force progress bar update

        // TODO Reset and Pause PhaseTimer
    }

    fun requestInvalidateComponents() {
        if (evidenceViewModel != null) {
            evidenceViewModel.ghostOrderData?.updateOrder()
        }

        if (ghostList != null) {
            ghostList?.forceResetGhostContainer()
        }


        // TODO Force progress bar update (aka reset)


        // TODO Reset Play/Pause button (to 'Play' state)\
    }

    override fun onDestroyView() {
        if (popupWindow != null) {
            popupWindow?.dismiss()
            popupWindow = null
        }

        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun saveStates() {
    }
}
