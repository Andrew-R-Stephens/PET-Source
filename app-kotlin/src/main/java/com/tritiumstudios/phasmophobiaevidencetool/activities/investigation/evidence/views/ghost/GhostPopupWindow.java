package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.views.ghost;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.views.investigation.InvestigationPopupWindow;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.GhostPopupData;
import com.tritiumstudios.phasmophobiaevidencetool.data.utilities.FontUtils;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.EvidenceViewModel;
import com.tritiumstudios.phasmophobiaevidencetool.views.PETImageButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class GhostPopupWindow extends InvestigationPopupWindow {

    private int detailIndex = 0;

    public GhostPopupWindow(@NonNull Context context) {
        super(context);
        initView();
    }

    public GhostPopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GhostPopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GhostPopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        super.initView(R.layout.popup_info_ghost);
    }

    public void build(@NonNull EvidenceViewModel evidenceViewModel,
                      @NonNull GhostPopupData ghostPopupData,
                      int groupIndex, AdRequest adRequest) {

        if(getContext() == null) { return; }

        LinearLayoutCompat linearLayout_iconRow = findViewById(R.id.icon_container);

        ConstraintLayout scrollCons_swapping =
                findViewById(R.id.scrollView_swapping);
        ConstraintLayout scrollCons_huntdata =
                findViewById(R.id.scrollView_huntdata);
        AppCompatTextView label_name =
                findViewById(R.id.textView_title);

        PETImageButton closeButton = findViewById(R.id.button_right);
        ConstraintLayout bodyCons = findViewById(R.id.layout_contentbody);
        AppCompatImageButton left = findViewById(R.id.title_left);
        AppCompatImageButton right = findViewById(R.id.title_right);
        AppCompatTextView title = findViewById(R.id.label_infoTitle);

        ScrollView scroller_swapping = scrollCons_swapping.findViewById(R.id.scrollView);
        View indicator_swapping = scrollCons_swapping.findViewById(R.id.scrollview_indicator);
        AppCompatTextView data_swapping = scroller_swapping.findViewById(R.id.label_info);

        ScrollView scroller_huntdata = scrollCons_huntdata.findViewById(R.id.scrollView);
        View indicator_huntdata = scrollCons_huntdata.findViewById(R.id.scrollview_indicator);
        AppCompatTextView data_huntdata = scroller_huntdata.findViewById(R.id.label_info);


        String[] titles = new String[] {
                getResources().getString(R.string.popup_ghost_info),
                getResources().getString(R.string.popup_ghost_strength),
                getResources().getString(R.string.popup_ghost_weakness)
        };

        // THEME
        Resources.Theme theme = getContext().getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.textColorBodyEmphasis, typedValue, true);
        @ColorInt int fontEmphasisColor = typedValue.data;


        for (int i = 0; i < evidenceViewModel.getInvestigationData().getGhostList()
                .getAt(groupIndex)
                .getEvidenceArray().length; i++) {

            AppCompatImageView evidenceIcon =
                    (AppCompatImageView) linearLayout_iconRow.getChildAt(i);

            evidenceIcon.setImageResource(evidenceViewModel.getInvestigationData().getGhostList()
                    .getAt(groupIndex).getEvidence()[i].getIcon());

        }

        label_name.setText(evidenceViewModel.getInvestigationData().getGhostList()
                .getAt(groupIndex).getName());

        //initialize info content scroller
        bodyCons.setVisibility(View.INVISIBLE);

        String[] cycleDetails = ghostPopupData.getCycleDetails(getContext(), groupIndex);

        title.setText(titles[detailIndex]);
        data_swapping.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                cycleDetails[detailIndex],
                "#ff6161", String.valueOf(fontEmphasisColor))));
        data_huntdata.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getContext().getString(ghostPopupData.getHuntData(groupIndex)),
                "#ff6161", String.valueOf(fontEmphasisColor))));

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            scrollCons_huntdata.post(() ->
            {
                scrollCons_huntdata.setMaxHeight((int) (bodyCons.getHeight() * .4f));

                fadeOutIndicatorAnimation(
                        scrollCons_huntdata,
                        scrollCons_huntdata,
                        scroller_huntdata,
                        indicator_huntdata);
            });
        }

        left.setOnClickListener(view -> {
            detailIndex = Math.min(((detailIndex -1) % cycleDetails.length) & (cycleDetails.length), cycleDetails.length-1);

            title.setText(titles[detailIndex]);
            data_swapping.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                    cycleDetails[detailIndex],
                    "#ff6161", String.valueOf(fontEmphasisColor))));

            fadeOutIndicatorAnimation(
                    bodyCons,
                    scrollCons_swapping,
                    scroller_swapping,
                    indicator_swapping);

        });

        right.setOnClickListener(view -> {
            detailIndex = (detailIndex +1)% cycleDetails.length;

            title.setText(titles[detailIndex]);
            data_swapping.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                    cycleDetails[detailIndex],
                    "#ff6161", String.valueOf(fontEmphasisColor))));

            fadeOutIndicatorAnimation(
                    bodyCons,
                    scrollCons_swapping,
                    scroller_swapping,
                    indicator_swapping);

        });

        fadeOutIndicatorAnimation(
                bodyCons,
                scrollCons_swapping,
                scroller_swapping,
                indicator_swapping);

        closeButton.setOnClickListener(v1 -> popupWindow.dismiss());

        popupWindow.showAtLocation(getRootView(), Gravity.CENTER_VERTICAL, 0, 0);

        MobileAds.initialize(getContext(), initializationStatus -> { });
        AdView mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void fadeOutIndicatorAnimation(@Nullable ConstraintLayout bodyCons, @Nullable ConstraintLayout container, @NonNull ScrollView scroller, @NonNull View indicator) {
        scroller.post(() -> {
            if (!scroller.canScrollVertically(1)) {
                indicator.setVisibility(View.INVISIBLE);
                indicatorFadeAnimation(indicator, 0);
            } else {
                if(container != null) {
                    if(container.getLayoutParams() instanceof ConstraintLayout.LayoutParams lParams) {

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

    private void indicatorFadeAnimation(@NonNull View indicator, int time) {

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
