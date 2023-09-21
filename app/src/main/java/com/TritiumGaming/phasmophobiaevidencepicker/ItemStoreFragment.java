package com.TritiumGaming.phasmophobiaevidencepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

public class ItemStoreFragment extends Fragment {

    private ItemStoreEquipmentStoreData storeData = new ItemStoreEquipmentStoreData();

    private ItemStoreScrollView scrollView;
    private ViewTreeObserver.OnScrollChangedListener viewTreeObserverlistener;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_safehouse_itemstore, container, false);
    }

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        // INITIALIZE VIEWS
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);

        ViewGroup itemStore = view.findViewById(R.id.item_safehouse_itemstore);
        LinearLayoutCompat parent = itemStore.findViewById(R.id.linearLayout_itemStore_list);
        GridLayout scrollViewPaginator = view.findViewById(R.id.item_safehouse_itemstore_paginator);
        scrollView = view.findViewById(R.id.scrollView);

        View dataView = view.findViewById(R.id.item_safehouse_itemstore_itemData);
        ImageView close_button = view.findViewById(R.id.close_button);

        TypedArray typed_shop_list = getResources().obtainTypedArray(R.array.shop_equipment_array);
        scrollViewPaginator.setRowCount(typed_shop_list.length());

        for (int i = 0; i < typed_shop_list.length(); i++) {
            @StringRes int equipmentName;
            @DrawableRes Integer equipmentIcon = 0;
            @DrawableRes ArrayList<Integer> tierImages = new ArrayList<>();

            TypedArray typed_shop =
                    getContext().getResources().obtainTypedArray(typed_shop_list.getResourceId(i, 0));

            equipmentName = typed_shop.getResourceId(0, 0);
            equipmentIcon = typed_shop.getResourceId(1, 0);

            TypedArray typed_equipment_image =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(2, 0));
            for (int j = 0; j < typed_equipment_image.length(); j++) {
                tierImages.add(typed_equipment_image.getResourceId(j, 0));
            }
            typed_equipment_image.recycle();
            typed_shop.recycle();

            LayoutInflater inflater = getLayoutInflater();
            AppCompatImageView equipmentView = (AppCompatImageView)inflater.inflate(R.layout.item_safehouse_scrollview_paginator_icon, null);
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            param.width = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
            param.height = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
            equipmentView.setLayoutParams(param);

            //ImageView imageView_icon = scrollViewPaginator.getChildAt(i).findViewById(R.id.image_equipmentIcon);
            equipmentView.setImageResource(equipmentIcon);
            //setIconFilter(equipmentView, Color.argb(255,255,255,255), .5f);
            setIconFilter(equipmentView, "#2D3635", 1f);
            scrollViewPaginator.addView(equipmentView);
            
            buildItemStoreGroup(parent, equipmentName, tierImages);
        }
        typed_shop_list.recycle();

        label_goto_left.setText(R.string.evidence_evidence_title);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeItemDataView(dataView);
            }
        });


        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        // LISTENERS
        initNavListeners(
                view.findViewById(R.id.listener_goto_left),
                null, //view.findViewById(R.id.icon_goto_medLeft),
                view.findViewById(R.id.listener_resetAll),
                null,
                view.findViewById(R.id.listener_goto_right),
                view.findViewById(R.id.icon_goto_left),
                view.findViewById(R.id.icon_goto_medLeft),
                view.findViewById(R.id.icon_resetAll),
                null,
                view.findViewById(R.id.icon_goto_right));

        LinearLayoutCompat list = (LinearLayoutCompat) (scrollView.getChildAt(0));

        for(int i = 0; i < list.getChildCount(); i++) {
            ItemStoreEquipmentGroup group = (ItemStoreEquipmentGroup) list.getChildAt(i);
            for(ItemStoreEquipmentItem item : group.getItems()) {
                item.setOnClickListener((itemView) -> {

                    boolean newState = !itemView.isSelected();
                    for(int k = 0; k < list.getChildCount(); k++) {
                        ItemStoreEquipmentGroup g = (ItemStoreEquipmentGroup) list.getChildAt(k);

                        for (ItemStoreEquipmentItem gItem: g.getItems()) {
                            gItem.setSelected(false);
                        }
                    }

                    itemView.setSelected(newState);

                    if (newState) {
                        openItemDataView(dataView);
                    } else {
                        closeItemDataView(dataView);
                    }
                });
            }
        }

        int paginatorChildCount = scrollViewPaginator.getChildCount();

        scrollViewPaginator.setOnTouchListener((view12, e) -> {
            float maxDimTouchPos = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? e.getY() : e.getX());
            float maxDimPercentage = (maxDimTouchPos) / (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? scrollViewPaginator.getHeight() : scrollViewPaginator.getWidth());
            int markIndex = Math.max(0, Math.min(paginatorChildCount, (int)((paginatorChildCount) * maxDimPercentage)));
            int verticalScrollableHeight = scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight();
            float percent = verticalScrollableHeight * ((markIndex)/(float)(paginatorChildCount-1));

            scrollView.smoothScrollTo(0, (int)(percent));

            return true;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((view1, i, i1, i2, i3) -> {
                doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount);
            });
        } else {
            viewTreeObserverlistener = () -> doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount);
            scrollView.getViewTreeObserver().addOnScrollChangedListener(viewTreeObserverlistener);
        }

        scrollView.post(() -> doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount));
    }

    private static void closeItemDataView(View dataView) {
        dataView.setVisibility(View.VISIBLE);


        if(dataView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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

    private static void openItemDataView(View dataView) {
        if(dataView.getVisibility() == View.VISIBLE) { return; }

        dataView.setVisibility(View.VISIBLE);

        if(dataView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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


    private void doScrollItemStoreScrollView(GridLayout scrollViewPaginator, int paginatorChildCount) {
        int verticalScrollableHeight = scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight();
        float scrollPos = scrollView.getScrollY();
        float maxDimPercentage = (scrollPos) / verticalScrollableHeight;
        int markIndex = Math.max(0, Math.min(paginatorChildCount -1, (int)(paginatorChildCount * maxDimPercentage)));

        Log.d("Scroll",  paginatorChildCount + " " + markIndex);

        for(int j = 0; j < paginatorChildCount; j++) {
            ImageView icon = scrollViewPaginator.getChildAt(j)
                    .findViewById(R.id.image_equipmentIcon);
            //setIconFilter(icon, Color.argb(255, 255, 255, 255), .5f);
            setIconFilter(icon, "#2D3635", 1f);
        }
        ImageView icon = scrollViewPaginator.getChildAt(markIndex)
                .findViewById(R.id.image_equipmentIcon);
        //setIconFilter(icon, Color.argb(255, 255, 0, 0), .75f);
        setIconFilter(icon, "#FFB43D", 1f);
    }

    private static void setIconFilter(ImageView icon, int colorInt, float alpha) {
        icon.setColorFilter(colorInt);
        icon.setAlpha(alpha);
    }

    private static void setIconFilter(ImageView icon, String colorString, float alpha) {
        setIconFilter(icon, Color.parseColor(colorString), alpha);
    }

    private void buildItemStoreGroup(
            LinearLayoutCompat parent,
            @StringRes int equipmentName,
            @IntegerRes ArrayList<Integer> tierImages
    ) {

        if(getContext() == null) { return; }

        ItemStoreEquipmentGroup itemStoreEquipmentGroup = new ItemStoreEquipmentGroup(getContext());

        itemStoreEquipmentGroup.build(equipmentName, tierImages);
        parent.addView(itemStoreEquipmentGroup);

    }

    private void initNavListeners(View lstnr_navLeft,
                                  View lstnr_navMedLeft,
                                  View lstnr_navCenter,
                                  View lstnr_navMedRight,
                                  View lstnr_navRight,
                                  AppCompatImageView icon_navLeft,
                                  AppCompatImageView icon_navMedLeft,
                                  AppCompatImageView icon_navCenter,
                                  AppCompatImageView icon_navMedRight,
                                  AppCompatImageView icon_navRight) {

        if(lstnr_navLeft != null) {
            ((View)lstnr_navLeft.getParent()).setVisibility(View.VISIBLE);
            icon_navLeft.setImageResource(R.drawable.icon_evidence);
            lstnr_navLeft.setOnClickListener(v -> {
                        Navigation.findNavController(v)
                                .popBackStack();
                    }
            );
        }
        /*
        if(lstnr_navMedLeft != null) {

        }

        if(lstnr_navCenter != null) {

        }

        if(lstnr_navMedRight != null) {

        }

        if(lstnr_navRight != null) {

        }
        */

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(scrollView != null && scrollView.getViewTreeObserver() != null && viewTreeObserverlistener != null){
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(viewTreeObserverlistener);
        }
    }
}
