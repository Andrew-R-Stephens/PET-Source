package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.compose.ui.platform.ComposeView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreComposablesKt;

import org.jetbrains.annotations.NotNull;

public class ItemStorePaginator extends GridLayout {

    protected ViewTreeObserver.OnScrollChangedListener viewTreeObserverListener;
    
    public ItemStorePaginator(Context context) {
        super(context);
        init(context);
    }

    public ItemStorePaginator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemStorePaginator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ItemStorePaginator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_itemstore_scrollview_paginator, this);
    }

    public void addIcon(Integer icon) {
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        param.width = isPortrait ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        param.height = (!isPortrait) ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;

        ComposeView image = new ComposeView(getContext());
        ItemStoreComposablesKt.setPaginationIcon(image, icon, false, () -> null);

        addView(image, param);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void initScrollListener(@NotNull FrameLayout scrollView) {

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int paginatorChildCount = isPortrait ?
                this.getRowCount() :
                this.getColumnCount();

        this.setOnTouchListener((v, e) -> {
            float maxDimTouchPos = isPortrait ? e.getY() : e.getX();
            float maxDimPercentage = (maxDimTouchPos) / (isPortrait ?
                    this.getHeight() : this.getWidth());
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
                doScrollItemStoreScrollView(scrollView);
            });
        } else {
            viewTreeObserverListener = () ->
                    doScrollItemStoreScrollView(scrollView);
            scrollView.getViewTreeObserver().addOnScrollChangedListener(viewTreeObserverListener);
        }
    }
    
    public void doScrollItemStoreScrollView(@NotNull FrameLayout scrollView) {

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int paginatorChildCount = isPortrait ?
                this.getRowCount() :
                this.getColumnCount();

        int scrollableLength = isPortrait ?
                scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight()
                : scrollView.getChildAt(0).getMeasuredWidth() - scrollView.getMeasuredWidth();
        float scrollPos = isPortrait ? scrollView.getScrollY() : scrollView.getScrollX();

        float maxDimPercentage = scrollPos / scrollableLength;
        int markIndex = Math.max(0, Math.min(paginatorChildCount -1, (int)(paginatorChildCount * maxDimPercentage)));

        Log.d("Scroll",  paginatorChildCount + " " + markIndex);

    }
    
    public void removeListener(View tetheredView) {
        if(tetheredView != null && 
                tetheredView.getViewTreeObserver() != null && viewTreeObserverListener != null){
            tetheredView.getViewTreeObserver().removeOnScrollChangedListener(viewTreeObserverListener);
        }
    }

}
