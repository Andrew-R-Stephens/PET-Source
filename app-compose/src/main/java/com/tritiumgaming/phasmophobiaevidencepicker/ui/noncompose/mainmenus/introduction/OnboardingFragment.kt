package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus.introduction

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.OnboardingSupportFragment
import com.tritiumgaming.phasmophobiaevidencepicker.R

class OnboardingFragment : OnboardingSupportFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoResourceId = R.drawable.icon_logo_app

        arrowColor = resources.getColor(R.color.white_M200)
        arrowBackgroundColor = resources.getColor(R.color.red_M200)

        dotBackgroundColor = resources.getColor(R.color.white_M200)

        view.setBackgroundColor(resources.getColor(R.color.black))
    }

    override fun getPageCount(): Int {
        return 2
    }

    override fun getPageTitle(pageIndex: Int): CharSequence? {
        return when (pageIndex) {
            0, 1 -> {
                "Welcome P.E.T.!"
            }

            else -> {
                "?"
            }
        }
    }

    override fun getPageDescription(pageIndex: Int): CharSequence? {
        return when (pageIndex) {
            0 -> {
                "This is the ultimate investigation tool for Phasmophobia!"
            }

            1 -> {
                "With this companion tool by your side, your Phasmophobia investigations will be a breeze from now on!"
            }

            else -> {
                "?"
            }
        }
    }

    override fun onPageChanged(newPage: Int, previousPage: Int) {
        super.onPageChanged(newPage, previousPage)
    }

    override fun onCreateBackgroundView(inflater: LayoutInflater, container: ViewGroup): View? {
        return null
    }

    override fun onCreateTitleAnimator(): Animator {
        return AnimatorInflater.loadAnimator(
            context,
            R.animator.onboarding_reset_animation
        )
    }

    override fun onCreateDescriptionAnimator(): Animator {
        return AnimatorInflater.loadAnimator(
            context,
            R.animator.onboarding_reset_animation
        )
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup): View? {
        /*
        ImageView contentView = new ImageView(getContext());
        contentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        contentView.setImageResource(R.drawable.icon_cp_tarot_joker);
        contentView.setPadding(0, 0, 0, 0);
        return contentView;
        */
        return null
    }

    override fun onCreateForegroundView(inflater: LayoutInflater, container: ViewGroup): View? {
        /*
        ImageView contentView = new ImageView(getContext());
        contentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        contentView.setImageResource(R.drawable.icon_cursedpossessions_musicbox);
        contentView.setPadding(0, 32, 0, 32);
        return contentView;
        */
        return null
    }

    override fun onFinishFragment() {
        try {
            Log.d("Onboarding", "Finishing Fragment!")
            requireActivity().finish()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }
}
