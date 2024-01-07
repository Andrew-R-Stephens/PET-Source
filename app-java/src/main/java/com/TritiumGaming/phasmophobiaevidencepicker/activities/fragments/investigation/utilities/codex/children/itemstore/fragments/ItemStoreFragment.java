package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.ItemStoreData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.views.ItemStoreGroup;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.views.ItemStoreHScrollView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.views.ItemStoreItem;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.views.ItemStoreVScrollView;

public abstract class ItemStoreFragment extends InvestigationFragment {

    protected final ItemStoreData storeData = new ItemStoreData();

    protected FrameLayout scrollView;
    protected ViewTreeObserver.OnScrollChangedListener viewTreeObserverListener;

    protected ItemStoreItem itemSelected = null;

    protected View dataView;

    private int
            selColor = Color.parseColor("#2D3635"),
            unselColor = Color.parseColor("#FFB43D");

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_itemstore, container, false);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(getContext() == null || getContext().getResources() == null) { return; }

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.codex_4, typedValue, true);
        unselColor = typedValue.data;
        theme.resolveAttribute(R.attr.codex_5, typedValue, true);
        selColor = typedValue.data;

        AppCompatTextView titleView = view.findViewById(R.id.label_pagetitle);
        ViewGroup itemStore = view.findViewById(R.id.item_safehouse_itemstore);
        LinearLayoutCompat parent = itemStore.findViewById(R.id.linearLayout_itemStore_list);
        GridLayout scrollViewPaginator = view.findViewById(R.id.item_safehouse_itemstore_paginator);
        scrollView = view.findViewById(R.id.scrollView);

        setPageTitle(titleView);
        setDataViewLayout(view);

        AppCompatImageView back_button = view.findViewById(R.id.button_back);
        ImageView close_button = view.findViewById(R.id.close_button);
        ProgressBar progressBar = view.findViewById(R.id.pBar);

        new Thread(() -> {
            buildStoreData();

            if(getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Log.d("Err", "Building Store Views");
                    buildGroupViews(parent, scrollViewPaginator);
                    Log.d("Err", "Finished Building Store Views");
                });
            }

            if(getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    scrollView.post(() -> {
                        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

                        boolean isScrollable;
                        if(isPortrait) {
                            isScrollable = scrollView.getHeight() < scrollView.getChildAt(0).getHeight() + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
                        } else {
                            isScrollable = scrollView.getWidth() < scrollView.getChildAt(0).getWidth() + scrollView.getPaddingStart() + scrollView.getPaddingEnd();
                        }

                        int paginatorChildCount = isPortrait ?
                                scrollViewPaginator.getRowCount() :
                                scrollViewPaginator.getColumnCount();

                        initScrollViewListeners(scrollViewPaginator, paginatorChildCount);
                        doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount);

                        if(isScrollable) {
                            scrollViewPaginator.setVisibility(View.VISIBLE);
                        } else {
                            scrollViewPaginator.setVisibility(View.GONE);
                        }

                        LinearLayoutCompat list = (LinearLayoutCompat) (scrollView.getChildAt(0));
                        for(int i = 0; i < list.getChildCount(); i++) {
                            int groupIndex = i;
                            ItemStoreGroup group = (ItemStoreGroup) list.getChildAt(i);
                            group.setVisibility(View.INVISIBLE);
                            group.setAlpha(0);
                            for(int j = 0; j < group.getItems().length; j++) {

                                ItemStoreItem item = group.getItems()[j];
                                int itemIndex = j;

                                item.setOnClickListener((itemView) -> {
                                    boolean newState = !item.isSelected();

                                    if(itemSelected != null) {
                                        itemSelected.setSelected(false);
                                    }

                                    itemSelected = item;
                                    itemSelected.setSelected(newState);

                                    buildDataPopupView(dataView, groupIndex, itemIndex);

                                    if (newState) {
                                        openItemDataView(dataView);
                                    } else {
                                        closeItemDataView(dataView);
                                    }
                                });
                            }

                            group.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.animate().alpha(0).setDuration(250).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            progressBar.setVisibility(View.GONE);
                                            super.onAnimationEnd(animation);
                                        }
                                    }).start();
                                    group.animate()
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {
                                                    super.onAnimationStart(animation);
                                                    group.setVisibility(View.VISIBLE);
                                                }}
                                            ).alpha(1).setStartDelay((long)(50f * groupIndex)).setDuration(200);
                                }
                            });
                        }
                    });
                });
            }
        }).start();

        close_button.setOnClickListener(v -> {
            closeItemDataView(dataView);

            if(itemSelected != null) {
                itemSelected.setSelected(false);
            }
        });

        back_button.setOnClickListener(v -> {
            handleBackAction(dataView);
        });

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        handleBackAction(dataView);
                    }
                });
        }

        stylizeLogo(view.findViewById(R.id.label_codex_ghostos));
    }

    protected abstract void setPageTitle(AppCompatTextView titleView);

    protected abstract void setDataViewLayout(View view);

    public void handleBackAction(View dataView) {
        if(dataView.getVisibility() == View.VISIBLE) {
            closeItemDataView(dataView);
            return;
        }
        backPressedHandler();
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initScrollViewListeners(GridLayout scrollViewPaginator, int paginatorChildCount) {
        scrollViewPaginator.setOnTouchListener((v, e) -> {
            boolean isPortrait =
                    getResources().getConfiguration().orientation ==
                            Configuration.ORIENTATION_PORTRAIT;

            float maxDimTouchPos = isPortrait ? e.getY() : e.getX();
            float maxDimPercentage = (maxDimTouchPos) / (isPortrait ?
                    scrollViewPaginator.getHeight() : scrollViewPaginator.getWidth());
            int markIndex =
                    Math.max(0, Math.min(paginatorChildCount,
                            (int)(paginatorChildCount * maxDimPercentage)));
            int scrollDistance =
                    isPortrait ?
                    scrollView.getChildAt(0)
                            .getMeasuredHeight() - scrollView.getMeasuredHeight() :
                    scrollView.getChildAt(0)
                            .getMeasuredWidth() - scrollView.getMeasuredWidth();
            float percent = scrollDistance * ((markIndex)/(float)(paginatorChildCount -1));

            if(scrollView instanceof ItemStoreVScrollView sv) {
                sv.smoothScrollTo(0, (int) (percent));
            } else if(scrollView instanceof ItemStoreHScrollView sv) {
                sv.smoothScrollTo((int) (percent), 0);
            }

            return true;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((view1, i, i1, i2, i3) -> {
                doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount);
            });
        } else {
            viewTreeObserverListener = () ->
                    doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount);
            scrollView.getViewTreeObserver().addOnScrollChangedListener(viewTreeObserverListener);
        }
    }

    @Override
    public abstract void softReset();

    @SuppressLint("ResourceType")
    protected abstract void buildStoreData();

    protected abstract void createGroup(
            LinearLayoutCompat parent,
            ItemStoreGroupData group
    );

    @SuppressLint("ResourceType")
    protected abstract void buildGroupViews(LinearLayoutCompat parent, GridLayout scrollViewPaginator);

    protected abstract void buildDataPopupView(View dataView, int groupIndex, int itemIndex);

    protected void addPaginatorIcon(GridLayout scrollViewPaginator, Integer icon) {
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        LayoutInflater inflater = getLayoutInflater();
        AppCompatImageView equipmentView = (AppCompatImageView)inflater.inflate(R.layout.item_itemstore_scrollview_paginator_icon, null);
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        param.width = isPortrait ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        param.height = (!isPortrait) ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        equipmentView.setLayoutParams(param);
        equipmentView.setImageResource(icon);
        setIconFilter(equipmentView, /*"#2D3635"*/ selColor, 1f);
        scrollViewPaginator.addView(equipmentView);
    }

    protected static void closeItemDataView(View dataView) {
        boolean isPortrait = dataView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        dataView.setVisibility(View.VISIBLE);
        if(isPortrait) {
            dataView.animate()
                    .translationY(dataView.getHeight())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            dataView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            dataView.setVisibility(View.GONE);
                        }
                    });
        } else {
            dataView.animate()
                    .translationX(dataView.getWidth())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            dataView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            dataView.setVisibility(View.GONE);
                        }
                    });
        }
    }

    protected static void openItemDataView(View dataView) {

        if(dataView.getVisibility() == View.VISIBLE) { return; }

        boolean isPortrait = dataView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        dataView.setVisibility(View.VISIBLE);

        if(isPortrait) {
            dataView.setTranslationY(dataView.getHeight());
            dataView.animate()
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            dataView.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            dataView.setVisibility(View.VISIBLE);
                        }
                    })
                    .translationY(0)
                    .setDuration(150);
        } else {
            dataView.setTranslationX(dataView.getWidth());
            dataView.animate()
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            dataView.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            dataView.setVisibility(View.VISIBLE);
                        }
                    })
                    .translationX(0)
                    .setDuration(150);
        }
    }

    protected void doScrollItemStoreScrollView(GridLayout scrollViewPaginator, int paginatorChildCount) {

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        int scrollableLength = isPortrait ?
                scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight()
                : scrollView.getChildAt(0).getMeasuredWidth() - scrollView.getMeasuredWidth();
        float scrollPos = isPortrait ? scrollView.getScrollY() : scrollView.getScrollX();

        float maxDimPercentage = scrollPos / scrollableLength;
        int markIndex = Math.max(0, Math.min(paginatorChildCount -1, (int)(paginatorChildCount * maxDimPercentage)));

        Log.d("Scroll",  paginatorChildCount + " " + markIndex);

        for(int j = 0; j < paginatorChildCount; j++) {
            ImageView icon = scrollViewPaginator.getChildAt(j)
                    .findViewById(R.id.image_equipmentIcon);
            setIconFilter(icon, selColor, 1f);
        }
        ImageView icon = scrollViewPaginator.getChildAt(markIndex)
                .findViewById(R.id.image_equipmentIcon);
        setIconFilter(icon, unselColor, 1f);
    }

    private static void setIconFilter(ImageView icon, int colorInt, float alpha) {
        icon.setColorFilter(colorInt);
        icon.setAlpha(alpha);
    }

    /** @noinspection SameParameterValue*/
    private static void setIconFilter(ImageView icon, String colorString, float alpha) {
        setIconFilter(icon, Color.parseColor(colorString), alpha);
    }


    public void stylizeLogo(AppCompatTextView label_ghostOS) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(R.attr.codex_2, typedValue, true);
        int color1 = typedValue.data;
        theme.resolveAttribute(R.attr.codex_4, typedValue, true);
        int color2 = typedValue.data;
        String color1Hex = String.format("#%06X", (0xFFFFFF & color1));
        String color2Hex = String.format("#%06X", (0xFFFFFF & color2));

        label_ghostOS.setText(Html.fromHtml(getString(R.string.codex_label_gh_ost)
                .replaceAll("#99AEB3", color1Hex)
                .replaceAll("#FFB43D", color2Hex)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(scrollView != null && scrollView.getViewTreeObserver() != null && viewTreeObserverListener != null){
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(viewTreeObserverListener);
        }
    }
}
