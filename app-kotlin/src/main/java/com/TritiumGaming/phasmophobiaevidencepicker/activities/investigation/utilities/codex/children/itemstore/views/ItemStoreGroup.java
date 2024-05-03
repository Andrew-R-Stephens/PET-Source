package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views;

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
import androidx.compose.ui.platform.ComposeView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreComposablesKt;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreType;

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

    public void build(
            @StringRes int name,
            @NonNull @IntegerRes ArrayList<Integer> icons,
            ItemStoreType type) {

        ComposeView grid = findViewById(R.id.groupList);
        ItemStoreComposablesKt.createGroup(grid, icons, type);

        AppCompatTextView textView_name = findViewById(R.id.safehouse_shop_tool_label);
        String title = getResources().getString(name);
        textView_name.setText(title);
        textView_name.setSelected(true);
    }

    @NonNull
    public View[] getItems() {
        LinearLayoutCompat layout = (LinearLayoutCompat) getChildAt(0);
        ViewGroup container = layout.findViewById(R.id.groupList);

        View[] items = new View[container.getChildCount()];

        for(int i = 0; i < container.getChildCount(); i++){
            items[i] = container.getChildAt(i);
        }

        return items;
    }
}
