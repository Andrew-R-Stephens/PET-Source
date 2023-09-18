package com.TritiumGaming.phasmophobiaevidencepicker;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.data.MapData;

import java.util.ArrayList;

public class ItemStoreFragment extends Fragment {

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

        ConstraintLayout itemStore = view.findViewById(R.id.item_safehouse_itemstore);
        LinearLayoutCompat parent = itemStore.findViewById(R.id.linearLayout_itemStore_list);
        GridLayout scrollViewPaginator = view.findViewById(R.id.item_safehouse_itemstore_paginator);
        ScrollView scrollView = view.findViewById(R.id.scrollView);

        TypedArray typed_shop_list = getResources().obtainTypedArray(R.array.shop_equipment_array);
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

            ImageView imageView_icon = scrollViewPaginator.getChildAt(i).findViewById(R.id.image_equipmentIcon);
            imageView_icon.setImageResource(equipmentIcon);
            imageView_icon.setColorFilter(Color.argb(255, 255, 255, 255));
            imageView_icon.setAlpha(.5f);
            
            buildItemStoreGroup(parent, equipmentName, tierImages);
        }
        typed_shop_list.recycle();

        ImageView imageView_icon = scrollViewPaginator.getChildAt(0).findViewById(R.id.image_equipmentIcon);
        imageView_icon.setColorFilter(Color.argb(255, 255, 0, 0));
        imageView_icon.setAlpha(.75f);


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


        label_goto_left.setText(R.string.evidence_evidence_title);

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
                int verticalScrollableHeight = scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight();
                float scrollPos = scrollView.getScrollY();
                float maxDimPercentage = (scrollPos) / verticalScrollableHeight;
                int markIndex = Math.max(0, Math.min(paginatorChildCount-1, (int)((paginatorChildCount) * maxDimPercentage)));

                Log.d("Scroll",  paginatorChildCount + " " + markIndex);

                for(int j = 0; j < paginatorChildCount; j++) {
                    ImageView icon = scrollViewPaginator.getChildAt(j)
                    .findViewById(R.id.image_equipmentIcon);
                    icon.setColorFilter(Color.argb(255, 255, 255, 255));
                    icon.setAlpha(.5f);
                }
                ImageView icon = scrollViewPaginator.getChildAt(markIndex)
                        .findViewById(R.id.image_equipmentIcon);
                icon.setColorFilter(Color.argb(255, 255, 0, 0));
                icon.setAlpha(.75f);
            });
        }


        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        /*else {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(
                    () -> {
                        int y = scrollView.getScrollY();
                    });
        }*/

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

        if(lstnr_navMedLeft != null) {

        }

        if(lstnr_navCenter != null) {

        }

        if(lstnr_navMedRight != null) {

        }

        if(lstnr_navRight != null) {

        }

    }

}
