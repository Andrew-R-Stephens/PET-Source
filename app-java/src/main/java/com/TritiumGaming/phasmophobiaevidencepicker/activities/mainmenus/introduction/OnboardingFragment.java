package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.introduction;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.app.OnboardingSupportFragment;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class OnboardingFragment extends OnboardingSupportFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLogoResourceId(R.drawable.app_icon_sm);

        setArrowColor(getResources().getColor(R.color.white_M200));
        setArrowBackgroundColor(getResources().getColor(R.color.red_M200));

        setDotBackgroundColor(getResources().getColor(R.color.white_M200));

        view.setBackgroundColor(getResources().getColor(R.color.black));
    }

    @Override
    protected int getPageCount() {
        return 2;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected CharSequence getPageTitle(int pageIndex) {
        switch (pageIndex) {
            case 0, 1 -> {
                return "Welcome P.E.T.!";
            }
            default -> {
                return "?";
            }
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected CharSequence getPageDescription(int pageIndex) {
        switch (pageIndex) {
            case 0 -> {
                return "This is the ultimate investigation tool for Phasmophobia!";
            }
            case 1 -> {
                return "With this companion tool by your side, your Phasmophobia investigations will be a breeze from now on!";
            }
            default -> {
                return "?";
            }
        }
    }

    @Override
    protected void onPageChanged(int newPage, int previousPage) {
        super.onPageChanged(newPage, previousPage);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected View onCreateBackgroundView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return null;
    }

    @NonNull
    @Override
    protected Animator onCreateTitleAnimator() {
        return AnimatorInflater.loadAnimator(
                getContext(),
                R.animator.onboarding_reset_animation);
    }

    @NonNull
    @Override
    protected Animator onCreateDescriptionAnimator() {
        return AnimatorInflater.loadAnimator(
                getContext(),
                R.animator.onboarding_reset_animation);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        /*
        ImageView contentView = new ImageView(getContext());
        contentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        contentView.setImageResource(R.drawable.icon_cp_tarot_joker);
        contentView.setPadding(0, 0, 0, 0);
        return contentView;
        */
        return null;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected View onCreateForegroundView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        /*
        ImageView contentView = new ImageView(getContext());
        contentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        contentView.setImageResource(R.drawable.icon_cursedpossessions_musicbox);
        contentView.setPadding(0, 32, 0, 32);
        return contentView;
        */
        return null;
    }

    @Override
    protected void onFinishFragment() {
        try {
            Log.d("Onboarding", "Finishing Fragment!");
            requireActivity().finish();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
