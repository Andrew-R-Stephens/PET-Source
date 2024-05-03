package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ItemStoreViewModel;

public abstract class ItemStoreDataView extends ConstraintLayout {

    protected int layoutResId = R.layout.layout_itemstore_itemdata_equipment;

    public ItemStoreDataView(@NonNull Context context) {
        super(context);
    }

    public ItemStoreDataView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemStoreDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemStoreDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init() {
        setDefaults();
    }

    private void setDefaults() {
        setVisibility(INVISIBLE);
    }

    public abstract void buildData(ItemStoreViewModel itemStoreViewModel, int groupIndex, int itemIndex);

    public int getLayoutResId() {
        return layoutResId;
    }

    public void closeItemDataView() {
        boolean isPortrait = this.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        this.setVisibility(View.VISIBLE);
        if(isPortrait) {
            this.animate()
                    .translationY(this.getHeight())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            ItemStoreDataView.this.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            ItemStoreDataView.this.setVisibility(View.GONE);
                        }
                    });
        } else {
            this.animate()
                    .translationX(this.getWidth())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            ItemStoreDataView.this.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            ItemStoreDataView.this.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public void openItemDataView() {

        if(this.getVisibility() == View.VISIBLE) { return; }

        boolean isPortrait = this.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        this.setVisibility(View.VISIBLE);
        if(isPortrait) {
            this.setTranslationY(this.getHeight());
            this.animate()
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            ItemStoreDataView.this.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            ItemStoreDataView.this.setVisibility(View.VISIBLE);
                        }
                    })
                    .translationY(0)
                    .setDuration(150);
        } else {
            this.setTranslationX(this.getWidth());
            this.animate()
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);

                            ItemStoreDataView.this.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            ItemStoreDataView.this.setVisibility(View.VISIBLE);
                        }
                    })
                    .translationX(0)
                    .setDuration(150);
        }
    }

}
