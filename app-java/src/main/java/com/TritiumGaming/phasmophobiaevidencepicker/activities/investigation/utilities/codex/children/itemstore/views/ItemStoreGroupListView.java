package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore.ItemStoreGroupModel;

public class ItemStoreGroupListView extends LinearLayoutCompat {

    public static final int UNSPECIFIED_LAYOUT = -1;

    public ItemStoreGroupListView(@NonNull Context context) {
        super(context);
        //initView(UNSPECIFIED_LAYOUT);
    }

    public ItemStoreGroupListView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        //initView(UNSPECIFIED_LAYOUT);
    }

    public ItemStoreGroupListView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initView(UNSPECIFIED_LAYOUT);
    }

    public void build(@DrawableRes int containerSrc, ItemStoreGroupModel group) {
        build(containerSrc, group, false);
    }

    public void build(@DrawableRes int containerSrc, ItemStoreGroupModel group, boolean hasTier) {
        initView(group.getSize() > 3 ?
                R.layout.item_itemstore_itemgroup_long :
                R.layout.item_itemstore_itemgroup);

        ViewGroup groupList = findViewById(R.id.groupList);

        if(group.getSize() > groupList.getChildCount()) {
            for(int i = 0; i < group.getSize() - groupList.getChildCount(); i++) {
                groupList.addView(
                        inflate(getContext(), R.layout.item_itemstore_scrollview_image, null));
            }
        }
        groupList = findViewById(R.id.groupList);

        int i = 0;
        for(; (i < groupList.getChildCount()) && (i < group.getSize()); i++) {
            ItemStoreItemView item = (ItemStoreItemView) groupList.getChildAt(i);
            item.setImageResource(containerSrc);
            item.setSelected(false);
            item.setTier(hasTier ? i+1 : 0);
            item.setEquipment(group.getItemImages().get(i));
        }
        for(; i < groupList.getChildCount(); i++) {
            View v = groupList.getChildAt(i);
            v.setVisibility(GONE);
        }

        AppCompatTextView textView_name = this.findViewById(R.id.safehouse_shop_tool_label);
        String title = getResources().getString(group.nameData);
        textView_name.setText(title);
        textView_name.setSelected(true);

        this.setVisibility(View.INVISIBLE);
        this.setAlpha(0);
    }

    public void initView(@LayoutRes int layoutRes) {
        if(layoutRes == UNSPECIFIED_LAYOUT)
            inflate(getContext(), R.layout.item_itemstore_itemgroup, this);
        else {
            inflate(getContext(), layoutRes, this);
        }
    }

    @NonNull
    public ItemStoreItemView[] getItems() {
        LinearLayoutCompat layout = (LinearLayoutCompat) getChildAt(0);
        ViewGroup container = layout.findViewById(R.id.groupList);

        ItemStoreItemView[] items = new ItemStoreItemView[container.getChildCount()];

        for(int i = 0; i < container.getChildCount(); i++){
            items[i] = (ItemStoreItemView) (container.getChildAt(i));
        }

        return items;
    }
}
