package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreDataView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroup;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStorePaginator;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ItemStoreViewModel;

public abstract class ItemStoreFragment extends InvestigationFragment {

    protected ItemStoreViewModel itemStoreViewModel;

    protected FrameLayout scrollView;
    protected LinearLayoutCompat scrollViewParent;
    protected ItemStorePaginator paginator;

    protected ItemStoreDataView dataView;

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

        super.init();

        AppCompatTextView titleView = view.findViewById(R.id.label_pagetitle);
        paginator = view.findViewById(R.id.item_safehouse_itemstore_paginator);
        ViewGroup itemStore = view.findViewById(R.id.item_safehouse_itemstore);
        scrollView = itemStore.findViewById(R.id.scrollView);
        scrollViewParent = scrollView.findViewById(R.id.linearLayout_itemStore_list);

        setPageTitle(titleView);
        setDataViewLayout(view);

        AppCompatImageView back_button = view.findViewById(R.id.button_back);
        ImageView close_button = dataView.findViewById(R.id.close_button);
        ProgressBar progressBar = view.findViewById(R.id.pBar);

        new Thread(() -> {
            buildStoreData();

            try {
                requireActivity().runOnUiThread(this::buildGroupViews);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            try {
                requireActivity().runOnUiThread(() -> scrollView.post(() -> {
                    boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

                    boolean isScrollable;
                    if(isPortrait) {
                        isScrollable = scrollView.getHeight() < scrollView.getChildAt(0).getHeight() + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
                    } else {
                        isScrollable = scrollView.getWidth() < scrollView.getChildAt(0).getWidth() + scrollView.getPaddingStart() + scrollView.getPaddingEnd();
                    }

                    paginator.initScrollListener(scrollView);
                    paginator.doScrollItemStoreScrollView(scrollView);

                    if(isScrollable) {
                        paginator.setVisibility(View.VISIBLE);
                    } else {
                        paginator.setVisibility(View.GONE);
                    }

                    LinearLayoutCompat list = (LinearLayoutCompat) (scrollView.getChildAt(0));
                    for(int i = 0; i < list.getChildCount(); i++) {
                        int groupIndex = i;
                        ItemStoreGroup group = (ItemStoreGroup) list.getChildAt(i);
                        group.setVisibility(View.INVISIBLE);
                        group.setAlpha(0);


                        for(int j = 0; j < group.getItems().length; j++) {

                            View item = group.getItems()[j];
                            int itemIndex = j;

                            item.setOnClickListener((itemView) -> {
                                boolean newState = !itemStoreViewModel.getCurrentStore()
                                        .isSelectedItemAndGroupIndex(groupIndex, itemIndex);

                                itemStoreViewModel.getCurrentStore()
                                        .setSelectedItemAndGroupIndex(groupIndex, itemIndex);
                                Log.d("Selected",
                                        itemStoreViewModel.getCurrentStore().getGroupIndexSelected()
                                                + " " + itemStoreViewModel.getCurrentStore().getGroupIndexSelected());

                                dataView.buildData(itemStoreViewModel, groupIndex, itemIndex);

                                if (newState) {
                                    dataView.openItemDataView();
                                } else {
                                    dataView.closeItemDataView();
                                }
                            });
                        }

                        group.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.animate()
                                        .alpha(0)
                                        .setDuration(250)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                progressBar.setVisibility(View.GONE);
                                                super.onAnimationEnd(animation);
                                            }
                                        })
                                        .start();
                                group.animate()
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                                super.onAnimationStart(animation);
                                                group.setVisibility(View.VISIBLE);
                                            }}
                                        ).alpha(1)
                                        .setStartDelay((long)(50f * groupIndex))
                                        .setDuration(200);
                            }
                        });
                    }
                }));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }).start();

        close_button.setOnClickListener(v -> {
            Log.d("Close button", "Woo!");
            dataView.closeItemDataView();

            itemStoreViewModel.getCurrentStore().resetSelection();
        });

        back_button.setOnClickListener(v -> {
            handleBackAction();
        });

        try {
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        handleBackAction();
                    }
                });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        stylizeLogo(view.findViewById(R.id.label_codex_ghostos));
    }

    protected abstract void setPageTitle(AppCompatTextView titleView);

    protected abstract void setDataViewLayout(View view);

    @Override
    public abstract void softReset();

    @SuppressLint("ResourceType")
    protected abstract void buildStoreData();

    protected abstract void createGroup( LinearLayoutCompat parent, ItemStoreGroupData group );

    /*protected abstract void buildDataPopupView(int groupIndex, int itemIndex);*/

    public void handleBackAction() {
        if(dataView.getVisibility() == View.VISIBLE) {
            dataView.closeItemDataView();
            return;
        }
        backPressedHandler();
    }

    @SuppressLint("ResourceType")
    protected void buildGroupViews() {
        paginator.setRowCount(itemStoreViewModel.getCurrentStore().getGroups().size());

        for (ItemStoreGroupData group: itemStoreViewModel.getCurrentStore().getGroups()) {

            try {
                requireActivity().runOnUiThread(() -> {
                    paginator.addIcon(group.getPaginationIcon());

                    createGroup(scrollViewParent, group);
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void stylizeLogo(@NonNull AppCompatTextView label_ghostOS) {

        int color1 = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex_2);
        int color2 = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex_4);
        String color1Hex = String.format("#%06X", (0xFFFFFF & color1));
        String color2Hex = String.format("#%06X", (0xFFFFFF & color2));

        label_ghostOS.setText(Html.fromHtml(getString(R.string.codex_label_gh_ost)
                .replaceAll("#99AEB3", color1Hex)
                .replaceAll("#FFB43D", color2Hex)));
    }

    protected void initViewModels() {
        initItemStoreViewModel();
    }

    private void initItemStoreViewModel() {
        if (itemStoreViewModel == null) {
            itemStoreViewModel = new ViewModelProvider(requireActivity())
                    .get(ItemStoreViewModel.class);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(paginator != null) {
            paginator.removeListener(scrollView);
        }
    }
}
