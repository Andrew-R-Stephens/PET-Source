package com.TritiumGaming.phasmophobiaevidencepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.ArrayList;

public class ItemStoreEquipmentGroup extends LinearLayoutCompat {

    public ItemStoreEquipmentGroup(@NonNull Context context) {
        super(context);
        initView();
    }

    public ItemStoreEquipmentGroup(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ItemStoreEquipmentGroup(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.item_shop_tool_group, this);
    }

    public void build(@StringRes int name, @IntegerRes ArrayList<Integer> equipmentIcons) {

        ViewGroup grid = findViewById(R.id.testgridlist);
        for(int i = 0; i < grid.getChildCount(); i++) {
            ItemStoreEquipmentItem item = (ItemStoreEquipmentItem) grid.getChildAt(i);
            item.setTier(i+1);
            item.setEquipment(equipmentIcons.get(i));
        }

        AppCompatTextView textView_name = findViewById(R.id.safehouse_shop_tool_label);
        String title = getResources().getString(name);
        textView_name.setText(title);
        textView_name.setSelected(true);
    }
}
