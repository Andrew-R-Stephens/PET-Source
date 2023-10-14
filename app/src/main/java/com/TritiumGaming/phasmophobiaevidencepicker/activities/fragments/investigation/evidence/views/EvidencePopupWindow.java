package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.EvidencePopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.GhostPopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import pl.droidsonroids.gif.GifImageView;

public class EvidencePopupWindow extends InvestigationPopupWindow {

    private int detailIndex = 0;

    public EvidencePopupWindow(@NonNull Context context) {
        super(context);
        initView();
    }

    public EvidencePopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EvidencePopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public EvidencePopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        super.initView(R.layout.popup_info_evidence);
    }

    public void build(EvidenceViewModel evidenceViewModel,
                      EvidencePopupData.EvidencePopupRecord evidenceRecord,
                      int groupIndex, AdRequest adRequest) {

        if(getContext() == null) { return; }

        // THEME
        Resources.Theme theme = getContext().getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.bodyEmphasisFontColor, typedValue, true);
        @ColorInt int fontEmphasisColor = typedValue.data;

        AppCompatImageButton closeButton = findViewById(R.id.popup_close_button);
        AppCompatTextView label = findViewById(R.id.label_name);
        AppCompatTextView info = findViewById(R.id.label_info);

        AppCompatImageView select_overview = findViewById(R.id.textView_overview_image);
        AppCompatImageView select_tiers = findViewById(R.id.textView_tiers_image);

        AppCompatImageView select_tier_1 = findViewById(R.id.textView_tiers_1_image);
        AppCompatImageView select_tier_2 = findViewById(R.id.textView_tiers_2_image);
        AppCompatImageView select_tier_3 = findViewById(R.id.textView_tiers_3_image);

        ScrollView scroller = findViewById(R.id.scrollView);
        View indicator = findViewById(R.id.scrollview_indicator);
        GifImageView animation = findViewById(R.id.animation_evidence);
        GifImageView animation_fullscreen = findViewById(R.id.animation_evidence_fullscreen);

        ConstraintLayout layout_overview = findViewById(R.id.constraintLayout_evidence_overview);
        ConstraintLayout layout_tiers = findViewById(R.id.constraintLayout_evidence_tiers);
        AppCompatTextView label_cost = findViewById(R.id.label_cost);

        // Init
        label_cost.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getContext().getString(R.string.evidence_requirement_cost_title) + " $" + evidenceRecord.getCost(getContext()),
                "#ff6161", fontEmphasisColor + "")));
        //MAIN STATES
        select_overview.setImageState(new int[]{R.attr.state_done}, true);
        select_overview.setOnClickListener(selectView -> {
            select_overview.setImageState(new int[]{R.attr.state_done}, true);
            select_tiers.setImageState(new int[]{-R.attr.state_done}, true);

            layout_overview.setVisibility(View.VISIBLE);
            layout_tiers.setVisibility(View.GONE);

            animation_fullscreen.setImageResource(evidenceRecord.getAnimation(getContext(), 0));
        });
        select_tiers.setOnClickListener(selectView -> {
            select_tiers.setImageState(new int[]{R.attr.state_done}, true);
            select_overview.setImageState(new int[]{-R.attr.state_done}, true);

            layout_tiers.setVisibility(View.VISIBLE);
            layout_overview.setVisibility(View.GONE);

            select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);
            generateEvidenceTierView(1, animation_fullscreen,
                    evidenceRecord.getDescription(getContext(), 1),
                    evidenceRecord.getAnimation(getContext(), 1),
                    evidenceRecord.getUnlockLevel(getContext(), 0),
                    fontEmphasisColor);
        });

        //TIER STATES
        select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
        select_tier_1.setOnClickListener(selectView -> {
            select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);

            generateEvidenceTierView(1, animation_fullscreen,
                    evidenceRecord.getDescription(getContext(), 1),
                    evidenceRecord.getAnimation(getContext(), 1),
                    evidenceRecord.getUnlockLevel(getContext(), 0),
                    fontEmphasisColor);
        });
        select_tier_2.setOnClickListener(selectView -> {
            select_tier_2.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_1.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);

            generateEvidenceTierView(2, animation_fullscreen,
                    evidenceRecord.getDescription(getContext(), 2),
                    evidenceRecord.getAnimation(getContext(), 2),
                    evidenceRecord.getUnlockLevel(getContext(), 1),
                    fontEmphasisColor);
        });
        select_tier_3.setOnClickListener(selectView -> {
            select_tier_3.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_1.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);

            generateEvidenceTierView(3, animation_fullscreen,
                    evidenceRecord.getDescription(getContext(), 3),
                    evidenceRecord.getAnimation(getContext(), 3),
                    evidenceRecord.getUnlockLevel(getContext(), 2),
                    fontEmphasisColor);
        });


        animation.setOnClickListener(view12 -> {
            if(animation_fullscreen.getVisibility() != View.VISIBLE) {
                animation_fullscreen.setVisibility(View.VISIBLE);
            }
        });
        animation_fullscreen.setOnClickListener(view1 -> {
            if(view1.getVisibility() == View.VISIBLE) {
                view1.setVisibility(View.GONE);
            }
        });

        fadeOutIndicatorAnimation(
                null,
                null,
                scroller,
                indicator);

        label.setText(evidenceRecord.getName(getContext()));
        info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                evidenceRecord.getDescription(getContext(), 0),
                "#ff6161", fontEmphasisColor + "")));

        TypedArray typedArray;
        try {
            typedArray = getContext().getResources().
                    obtainTypedArray(R.array.equipment_animation_array);
            animation.setImageResource(evidenceRecord.getAnimation(getContext(), 0));
            animation_fullscreen.setImageResource(typedArray.getResourceId(evidenceRecord.index(), 0));
            typedArray.recycle();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        //----

        closeButton.setOnClickListener(v1 -> popupWindow.dismiss());

        popupWindow.showAtLocation(getRootView(), Gravity.CENTER_VERTICAL, 0, 0);

        MobileAds.initialize(getContext(), initializationStatus -> { });
        AdView mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void generateEvidenceTierView(int tierIndex, GifImageView animation_fullscreen,
                                         String description, @DrawableRes int animation,
                                         String level, @ColorInt int fontEmphasisColor) {

        ConstraintLayout scrollView = findViewById(R.id.scrollview_tiers);
        AppCompatTextView title = findViewById(R.id.label_tier);
        GifImageView animationView = findViewById(R.id.animation_tier);

        AppCompatTextView details = scrollView.findViewById(R.id.info_tier);
        AppCompatTextView levelView = findViewById(R.id.label_level);

        details.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                description,
                "#ff6161", fontEmphasisColor + "")));
        levelView.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getContext().getString(R.string.evidence_requirement_level_title) + " " + level,
                "#ff6161", fontEmphasisColor + "")));

        animationView.setImageResource(animation);
        animation_fullscreen.setImageResource(animation);

        if(getContext() != null) {
            TypedArray typedArray =
                    getContext().getResources().obtainTypedArray(R.array.equipment_tiers);
            title.setText(typedArray.getString(tierIndex-1));
            typedArray.recycle();
        }


        animationView.setOnClickListener(v -> {
            if(animation_fullscreen.getVisibility() != View.VISIBLE) {
                animation_fullscreen.setVisibility(View.VISIBLE);
            }
        });
        animation_fullscreen.setOnClickListener(v -> {
            if(v.getVisibility() == View.VISIBLE) {
                v.setVisibility(View.GONE);
            }
        });

    }

    public void fadeOutIndicatorAnimation(ConstraintLayout bodyCons, ConstraintLayout container, ScrollView scroller, View indicator) {
        scroller.post(() -> {
            if (!scroller.canScrollVertically(1)) {
                indicator.setVisibility(View.INVISIBLE);
                indicatorFadeAnimation(indicator, 0);
            } else {
                if(container != null) {
                    if(container.getLayoutParams() instanceof LayoutParams lParams) {

                        Log.d("Scroller", "Should constrain");
                        lParams.constrainedHeight = true;
                        container.setLayoutParams(lParams);
                        container.invalidate();

                        if (!scroller.canScrollVertically(1)) {
                            indicator.setVisibility(View.INVISIBLE);

                            indicatorFadeAnimation(indicator, 0);
                        } else {
                            indicator.setVisibility(View.VISIBLE);
                            indicator.setAlpha(1f);
                        }
                    }
                }
            }

            if(bodyCons != null) {
                //initialize info content scroller
                bodyCons.setVisibility(View.VISIBLE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroller.setOnScrollChangeListener((v13, scrollX, scrollY, oldScrollX,
                                                oldScrollY) -> {
                if (!scroller.canScrollVertically(1)) {
                    indicatorFadeAnimation(indicator, getResources().getInteger(
                            android.R.integer.config_longAnimTime));
                }
            });
        } else {
            scroller.setOnDragListener((v12, event) -> {
                if (!scroller.canScrollVertically(1)) {
                    indicatorFadeAnimation(indicator, getResources().getInteger(
                            android.R.integer.config_longAnimTime));
                }
                return true;
            });
        }
    }

    private void indicatorFadeAnimation(View indicator, int time) {

        indicator.animate()
                .alpha(0f)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        indicator.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
