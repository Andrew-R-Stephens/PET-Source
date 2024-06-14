package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.CodexFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.ItemStoreListModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreHScrollView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreVScrollView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.ColorUtils;

public abstract class ItemStoreFragment extends CodexFragment {

    protected final ItemStoreListModel storeData = new ItemStoreListModel();

    protected FrameLayout scrollView;
    protected ViewTreeObserver.OnScrollChangedListener viewTreeObserverListener;

    @Nullable
    protected ItemStoreItemView itemSelected = null;

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

        unselColor = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex4_unsel);
        selColor = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex5_sel);

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

            try {
                requireActivity().runOnUiThread(() -> {
                    Log.d("Err", "Building Store Views");
                    buildGroupViews(parent, scrollViewPaginator);
                    Log.d("Err", "Finished Building Store Views");
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            try {
                requireActivity().runOnUiThread(() -> {
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
                            ItemStoreGroupListView group = (ItemStoreGroupListView) list.getChildAt(i);
                            group.setVisibility(View.INVISIBLE);
                            group.setAlpha(0);
                            for(int j = 0; j < group.getItems().length; j++) {

                                ItemStoreItemView item = group.getItems()[j];
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
            } catch (IllegalStateException e) {
                e.printStackTrace();
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

        try {
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        handleBackAction(dataView);
                    }
                });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        stylizeLogo(view.findViewById(R.id.label_codex_ghostos));
    }

    protected abstract void setPageTitle(AppCompatTextView titleView);

    protected abstract void setDataViewLayout(View view);

    public void handleBackAction(@NonNull View dataView) {
        if(dataView.getVisibility() == View.VISIBLE) {
            closeItemDataView(dataView);
            return;
        }
        backPressedHandler();
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initScrollViewListeners(@NonNull GridLayout scrollViewPaginator, int paginatorChildCount) {
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
    public abstract void reset();

    @SuppressLint("ResourceType")
    protected abstract void buildStoreData();

    protected abstract void createGroup(
            LinearLayoutCompat parent,
            ItemStoreGroupModel group
    );

    @SuppressLint("ResourceType")
    protected abstract void buildGroupViews(LinearLayoutCompat parent, GridLayout scrollViewPaginator);

    protected abstract void buildDataPopupView(View dataView, int groupIndex, int itemIndex);

    protected void addPaginatorIcon(@NonNull GridLayout scrollViewPaginator, Integer icon) {
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

    protected static void closeItemDataView(@NonNull View dataView) {
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

    protected static void openItemDataView(@NonNull View dataView) {

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

    protected void doScrollItemStoreScrollView(@NonNull GridLayout paginatorGrid, int paginatorChildCount) {

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        int scrollableLength = isPortrait ?
                scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight()
                : scrollView.getChildAt(0).getMeasuredWidth() - scrollView.getMeasuredWidth();
        float scrollPos = isPortrait ? scrollView.getScrollY() : scrollView.getScrollX();

        float maxDimPercentage = scrollPos / scrollableLength;
        int markIndex = Math.max(0, Math.min(paginatorChildCount -1, (int)(paginatorChildCount * maxDimPercentage)));

        Log.d("Scroll",  paginatorChildCount + " " + markIndex);

        for(int j = 0; j < paginatorChildCount; j++) {
            ImageView icon = paginatorGrid.getChildAt(j)
                    .findViewById(R.id.image_equipmentIcon);
            setIconFilter(icon, selColor, 1f);
        }
        ImageView icon = paginatorGrid.getChildAt(markIndex)
                .findViewById(R.id.image_equipmentIcon);
        setIconFilter(icon, unselColor, 1f);
    }

    private static void setIconFilter(@NonNull ImageView icon, int colorInt, float alpha) {
        icon.setColorFilter(colorInt);
        icon.setAlpha(alpha);
    }

    /** @noinspection SameParameterValue*/
    private static void setIconFilter(@NonNull ImageView icon, String colorString, float alpha) {
        setIconFilter(icon, Color.parseColor(colorString), alpha);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(scrollView != null && scrollView.getViewTreeObserver() != null && viewTreeObserverListener != null){
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(viewTreeObserverListener);
        }
    }
}
