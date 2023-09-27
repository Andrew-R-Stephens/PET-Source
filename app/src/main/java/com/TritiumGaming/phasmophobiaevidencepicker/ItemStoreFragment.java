package com.TritiumGaming.phasmophobiaevidencepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.InvestigationFragment;

import java.util.ArrayList;

public class ItemStoreFragment extends InvestigationFragment {

    private ItemStoreEquipmentStoreData storeData = new ItemStoreEquipmentStoreData();

    private ItemStoreScrollView scrollView;
    private ViewTreeObserver.OnScrollChangedListener viewTreeObserverlistener;

    private ItemStoreEquipmentItem shopItemSelected = null;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_safehouse_itemstore, container, false);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(getContext() == null || getContext().getResources() == null) { return; }

        // INITIALIZE VIEWS
        //AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        //AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        //View listener_goto_left = view.findViewById(R.id.listener_goto_left);

        ViewGroup itemStore = view.findViewById(R.id.item_safehouse_itemstore);
        LinearLayoutCompat parent = itemStore.findViewById(R.id.linearLayout_itemStore_list);
        GridLayout scrollViewPaginator = view.findViewById(R.id.item_safehouse_itemstore_paginator);
        scrollView = view.findViewById(R.id.scrollView);

        View dataView = view.findViewById(R.id.item_safehouse_itemstore_itemData);
        ImageView close_button = view.findViewById(R.id.close_button);

        //label_goto_left.setText(R.string.evidence_evidence_title);

        close_button.setOnClickListener(v -> {
            closeItemDataView(dataView);

            if(shopItemSelected != null) {
                shopItemSelected.setSelected(false);
            }

        });

        if(getActivity() != null) {
            getActivity().runOnUiThread(() -> buildStore(parent, scrollViewPaginator));
        }

/*

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }
*/

        LinearLayoutCompat list = (LinearLayoutCompat) (scrollView.getChildAt(0));

        for(int i = 0; i < list.getChildCount(); i++) {
            ItemStoreEquipmentGroup group = (ItemStoreEquipmentGroup) list.getChildAt(i);
            for(int j = 0; j < group.getItems().length; j++) {
                ItemStoreEquipmentItem item = group.getItems()[j];
                int groupIndex = i;
                int itemIndex = j;
                item.setOnClickListener((itemView) -> {
                    boolean newState = !item.isSelected();

                    if(shopItemSelected != null) {
                        shopItemSelected.setSelected(false);
                    }

                    shopItemSelected = item;
                    shopItemSelected.setSelected(newState);

                    buildItemDataView(dataView, groupIndex, itemIndex);

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
    }

    @Override
    public void softReset() {

    }

    @SuppressLint("ResourceType")
    private void buildStore(LinearLayoutCompat parent, GridLayout scrollViewPaginator) {
        if(getContext() == null) { return; }

        TypedArray typed_shop_list = getResources().obtainTypedArray(R.array.shop_equipment_array);
        scrollViewPaginator.setRowCount(typed_shop_list.length());

        for (int i = 0; i < typed_shop_list.length(); i++) {
            @StringRes int equipmentName;
            @IntegerRes int buyCostData;
            @DrawableRes int equipmentIcon;
            @DrawableRes ArrayList<Integer> tierImages = new ArrayList<>();

            ItemStoreEquipmentGroupData groupData = new ItemStoreEquipmentGroupData();

            TypedArray typed_shop =
                    getContext().getResources().obtainTypedArray(typed_shop_list.getResourceId(i, 0));

            equipmentName = typed_shop.getResourceId(0, 0);
            equipmentIcon = typed_shop.getResourceId(1, 0);
            buyCostData = typed_shop.getResourceId(6, 0);

            groupData.setNameData(equipmentName);
            groupData.setBuyCostData(buyCostData);

            TypedArray typed_equipment_image =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(2, 0));
            for (int j = 0; j < typed_equipment_image.length(); j++) {
                groupData.addItem(new ItemStoreEquipmentItemData());
                @DrawableRes int value = typed_equipment_image.getResourceId(j, 0);
                groupData.getItemDataAt(j).setImageData(value);

                tierImages.add(value);
            }
            typed_equipment_image.recycle();

            TypedArray typed_equipment_flavortext =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(3, 0));
            for (int j = 0; j < typed_equipment_flavortext.length(); j++) {
                @StringRes int value = typed_equipment_flavortext.getResourceId(j, 0);
                groupData.getItemDataAt(j).setFlavorData(value);
            }
            typed_equipment_flavortext.recycle();

            TypedArray typed_equipment_infotext =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(4, 0));
            for (int j = 0; j < typed_equipment_infotext.length(); j++) {
                @StringRes int value = typed_equipment_infotext.getResourceId(j, 0);
                groupData.getItemDataAt(j).setInfoData(value);
            }
            typed_equipment_infotext.recycle();

            TypedArray typed_equipment_attributes =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(5, 0));
            for (int j = 0; j < typed_equipment_attributes.length(); j++) {

                TypedArray typed_equipment_attributes_positive =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes.getResourceId(j, 0));
                TypedArray typed_equipment_attributes_positive_list =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes_positive.getResourceId(0, 0));
                for (int l = 0; l < typed_equipment_attributes_positive.length(); l++) {
                    @StringRes int value = typed_equipment_attributes_positive_list.getResourceId(l, 0);
                    groupData.getItemDataAt(j).addPositiveAttribute(value);
                }
                typed_equipment_attributes_positive_list.recycle();
                typed_equipment_attributes_positive.recycle();

                TypedArray typed_equipment_attributes_negative =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes.getResourceId(j, 0));
                TypedArray typed_equipment_attributes_negative_list =
                        getContext().getResources().obtainTypedArray(typed_equipment_attributes_negative.getResourceId(1, 0));
                for (int l = 0; l < typed_equipment_attributes_negative_list.length(); l++) {
                    @StringRes int value = typed_equipment_attributes_negative_list.getResourceId(l, 0);
                    groupData.getItemDataAt(j).addNegativeAttribute(value);
                }
                typed_equipment_attributes_negative_list.recycle();
                typed_equipment_attributes_negative.recycle();
            }
            typed_equipment_attributes.recycle();

            TypedArray typed_equipment_upgradelevel =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(7, 0));
            for (int j = 0; j < typed_equipment_upgradelevel.length(); j++) {
                @IntegerRes int value = typed_equipment_upgradelevel.getResourceId(j, 0);
                groupData.getItemDataAt(j).setUpgradeLevel(value);
            }
            typed_equipment_upgradelevel.recycle();

            TypedArray typed_equipment_upgradecost =
                    getContext().getResources().obtainTypedArray(typed_shop.getResourceId(8, 0));
            for (int j = 0; j < typed_equipment_upgradecost.length(); j++) {
                @IntegerRes int value = typed_equipment_upgradecost.getResourceId(j, 0);
                groupData.getItemDataAt(j).setUpgradeCostData(value);
            }
            typed_equipment_upgradecost.recycle();

            typed_shop.recycle();

            addPaginatorIcon(scrollViewPaginator, equipmentIcon);
            buildItemStoreGroup(parent, equipmentName, tierImages);

            storeData.addGroup(groupData);
        }
        typed_shop_list.recycle();


        int paginatorChildCount = scrollViewPaginator.getChildCount();
        scrollView.post(() -> doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount));
    }

    private void buildItemDataView(View dataView, int groupIndex, int itemIndex) {
        ItemStoreEquipmentGroupData groupData = storeData.getGroupAt(groupIndex);
        ItemStoreEquipmentItemData itemData = groupData.getItemDataAt(itemIndex);

        AppCompatTextView itemNameView = dataView.findViewById(R.id.safehouse_shop_tool_label);
        AppCompatTextView flavortextView = dataView.findViewById(R.id.textview_itemshop_flavor);
        AppCompatTextView infotextView = dataView.findViewById(R.id.textview_itemshop_info);
        AppCompatTextView attrtextView = dataView.findViewById(R.id.textview_itemshop_attributes);
        AppCompatTextView buycostView = dataView.findViewById(R.id.textview_itemcost);
        AppCompatTextView upgradeCostView = dataView.findViewById(R.id.textview_upgradecost);
        AppCompatTextView upgradeLevelView = dataView.findViewById(R.id.textview_unlocklevel);
        ItemStoreEquipmentItem itemImageView = dataView.findViewById(R.id.itemStoreEquipmentItemData);//.findViewById(R.id.tier_item);

        StringBuilder buyCost = new StringBuilder("$");
        buyCost.append(getResources().getInteger(groupData.getBuyCostData()));

        int upcst = getResources().getInteger(itemData.getUpgradeCostData());
        StringBuilder upgradeCost = new StringBuilder();
        if(upcst > 0) {
            upgradeCost.append("$").append(upcst);
        } else {
            upgradeCost.append("-");
        }

        int uplvl = getResources().getInteger(itemData.getUpgradeLevelData());
        StringBuilder upgradeLevel = new StringBuilder();
        upgradeLevel.append((uplvl > 0) ? uplvl : "-");

        itemNameView.setText(getString(groupData.getNameData()));
        flavortextView.setText(Html.fromHtml(getString(itemData.getFlavorData())));
        infotextView.setText(Html.fromHtml(getString(itemData.getInfoData())));
        buycostView.setText(buyCost);
        upgradeCostView.setText(upgradeCost);
        upgradeLevelView.setText(upgradeLevel);
        if(getContext() != null) {
            attrtextView.setText(Html.fromHtml(itemData.getAllAttributesAsFormattedHTML(getContext())));
        }
        LayerDrawable layerDrawable = (LayerDrawable) (itemImageView.getDrawable());
        layerDrawable.setDrawableByLayerId(R.id.ic_type, ResourcesCompat.getDrawable(getResources(), itemData.getImageData(), getContext().getTheme()));
        layerDrawable.setLevel(itemIndex+1);
        itemImageView.invalidate();
    }

    private void addPaginatorIcon(GridLayout scrollViewPaginator, Integer equipmentIcon) {
        LayoutInflater inflater = getLayoutInflater();
        AppCompatImageView equipmentView = (AppCompatImageView)inflater.inflate(R.layout.item_safehouse_scrollview_paginator_icon, null);
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        param.width = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        param.height = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        equipmentView.setLayoutParams(param);
        equipmentView.setImageResource(equipmentIcon);
        setIconFilter(equipmentView, "#2D3635", 1f);
        scrollViewPaginator.addView(equipmentView);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(scrollView != null && scrollView.getViewTreeObserver() != null && viewTreeObserverlistener != null){
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(viewTreeObserverlistener);
        }
    }
}
