package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.utilities.codex.children.itemstore.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.tritiumstudios.phasmophobiaevidencetool.R;

import java.util.ArrayList;

public class ItemStoreGroup extends LinearLayoutCompat {

    public static final int UNSPECIFIED_LAYOUT = -1;

    public ItemStoreGroup(@NonNull Context context, @LayoutRes int layoutRes) {
        super(context);

        initView(layoutRes);
    }

    public ItemStoreGroup(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, @LayoutRes int layoutRes) {
        super(context, attrs);

        initView(layoutRes);
    }

    public ItemStoreGroup(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, @LayoutRes int layoutRes) {
        super(context, attrs, defStyleAttr);

        initView(layoutRes);
    }

    public void initView(@LayoutRes int layoutRes) {
        if(layoutRes == UNSPECIFIED_LAYOUT) {
            inflate(getContext(), R.layout.item_itemstore_itemgroup, this);
        } else {
            inflate(getContext(), layoutRes, this);
        }
    }

    public void build(@DrawableRes int containerSrc, @StringRes int name, @NonNull @IntegerRes ArrayList<Integer> equipmentIcons, boolean hasTier) {

        ViewGroup grid = findViewById(R.id.groupList);

        if(equipmentIcons.size() > grid.getChildCount()) {
            for(int i = 0; i < equipmentIcons.size() - grid.getChildCount(); i++) {
                grid.addView(inflate(getContext(), R.layout.item_itemstore_scrollview_image, null));
            }
        }
        grid = findViewById(R.id.groupList);

        int i = 0;
        for(; (i < grid.getChildCount()) && (i < equipmentIcons.size()); i++) {
            ItemStoreItem item = (ItemStoreItem) grid.getChildAt(i);
            item.setImageResource(containerSrc);
            item.setSelected(false);
            item.setTier(hasTier ? i+1 : 0);
            item.setEquipment(equipmentIcons.get(i));
        }
        for(; i < grid.getChildCount(); i++) {
            View v = grid.getChildAt(i);
            v.setVisibility(GONE);
        }

        AppCompatTextView textView_name = findViewById(R.id.safehouse_shop_tool_label);
        String title = getResources().getString(name);
        textView_name.setText(title);
        textView_name.setSelected(true);
    }

    @NonNull
    public ItemStoreItem[] getItems() {
        LinearLayoutCompat layout = (LinearLayoutCompat) getChildAt(0);
        ViewGroup container = layout.findViewById(R.id.groupList);

        ItemStoreItem[] items = new ItemStoreItem[container.getChildCount()];

        for(int i = 0; i < container.getChildCount(); i++){
            items[i] = (ItemStoreItem) (container.getChildAt(i));
        }

        return items;
    }
}
