package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.CodexFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreHScrollView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreVScrollView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigationUtils.codex.itemstore.ItemStoreGroupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigationUtils.codex.itemstore.ItemStoreListModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import kotlin.math.max
import kotlin.math.min

abstract class ItemStoreFragment : CodexFragment() {
    protected val storeData: ItemStoreListModel = ItemStoreListModel()

    protected var scrollView: FrameLayout? = null
    protected var viewTreeObserverListener: OnScrollChangedListener? = null

    protected var itemSelected: ItemStoreItemView? = null

    protected var dataView: View? = null

    private var selColor = Color.parseColor("#2D3635")
    private var unselColor = Color.parseColor("#FFB43D")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_itemstore, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unselColor = getColorFromAttribute(requireContext(), R.attr.codex4_unsel)
        selColor = getColorFromAttribute(requireContext(), R.attr.codex5_sel)

        val titleView = view.findViewById<AppCompatTextView>(R.id.label_pagetitle)
        val itemStore = view.findViewById<ViewGroup>(R.id.item_safehouse_itemstore)
        val parent = itemStore.findViewById<LinearLayoutCompat>(R.id.linearLayout_itemStore_list)
        val scrollViewPaginator = view.findViewById<GridLayout>(R.id.item_safehouse_itemstore_paginator)
        scrollView = view.findViewById(R.id.scrollView)

        setPageTitle(titleView)
        setDataViewLayout(view)

        val backButton = view.findViewById<AppCompatImageView>(R.id.button_back)
        val closeButton = view.findViewById<ImageView>(R.id.close_button)
        val progressBar = view.findViewById<ProgressBar>(R.id.pBar)

        Thread {
            buildStoreData()
            try {
                requireActivity().runOnUiThread {
                    Log.d("Err", "Building Store Views")
                    buildGroupViews(parent, scrollViewPaginator)
                    Log.d("Err", "Finished Building Store Views")
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }

            try {
                requireActivity().runOnUiThread {
                    scrollView?.let { scrollView ->
                        scrollView.post {
                            val isPortrait =
                                resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                            val isScrollable: Boolean =
                                if (isPortrait) {
                                    scrollView.height < scrollView.getChildAt(0).height +
                                            scrollView.paddingTop + scrollView.paddingBottom }
                                else {
                                    scrollView.width < scrollView.getChildAt(0).width +
                                            scrollView.paddingStart + scrollView.paddingEnd }

                            val paginatorChildCount =
                                if (isPortrait) scrollViewPaginator.rowCount
                                else scrollViewPaginator.columnCount

                            initScrollViewListeners(scrollViewPaginator, paginatorChildCount)
                            doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount)

                            if (isScrollable) { scrollViewPaginator.visibility = View.VISIBLE }
                            else { scrollViewPaginator.visibility = View.GONE }

                            val list = (scrollView.getChildAt(0)) as LinearLayoutCompat
                            for (i in 0 until list.childCount) {
                                val group = list.getChildAt(i) as ItemStoreGroupListView
                                group.visibility = View.INVISIBLE
                                group.alpha = 0f
                                for (j in group.items.indices) {
                                    val item = (group.items[j])!!

                                    item.setOnClickListener {
                                        val newState = !item.isSelected
                                        itemSelected?.isSelected = false

                                        itemSelected = item
                                        itemSelected?.isSelected = newState

                                        dataView?.let { dataView ->
                                            buildDataPopupView(dataView, i, j)
                                            if (newState) { openItemDataView(dataView) }
                                            else { closeItemDataView(dataView) }
                                        }
                                    }
                                }

                                group.post {
                                    progressBar.animate().alpha(0f).setDuration(250)
                                        .setListener(object : AnimatorListenerAdapter() {
                                            override fun onAnimationEnd(animation: Animator) {
                                                progressBar.visibility = View.GONE
                                                super.onAnimationEnd(animation)
                                            }
                                        }).start()
                                    group.animate()
                                        .setListener(object : AnimatorListenerAdapter() {
                                            override fun onAnimationStart(animation: Animator) {
                                                super.onAnimationStart(animation)
                                                group.visibility = View.VISIBLE
                                            }
                                        }
                                        ).alpha(1f).setStartDelay((50f * i).toLong())
                                        .setDuration(200)
                                }
                            }
                        }
                    }
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }.start()

        closeButton.setOnClickListener {
            dataView?.let { dataView -> closeItemDataView(dataView) }
            itemSelected?.isSelected = false
        }

        backButton.setOnClickListener {
            dataView?.let { dataView -> handleBackAction( dataView ) }
        }

        try {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        dataView?.let { dataView -> handleBackAction(dataView) }
                    }
                })
        } catch (e: IllegalStateException) { e.printStackTrace() }

        stylizeLogo(view.findViewById(R.id.label_codex_ghostos))
    }

    @SuppressLint("ClickableViewAccessibility")
    protected fun initScrollViewListeners(
        scrollViewPaginator: GridLayout,
        paginatorChildCount: Int
    ) {
        scrollViewPaginator.setOnTouchListener { v: View?, e: MotionEvent ->
            val isPortrait =
                resources.configuration.orientation ==
                        Configuration.ORIENTATION_PORTRAIT
            val maxDimTouchPos = if (isPortrait) e.y else e.x
            val maxDimPercentage =
                (maxDimTouchPos) / (if (isPortrait) scrollViewPaginator.height else scrollViewPaginator.width)
            val markIndex = max(
                0.0, min(
                    paginatorChildCount.toDouble(),
                    (paginatorChildCount * maxDimPercentage).toInt().toDouble()
                )
            ).toInt()

            val scrollDistance =
                if (isPortrait) scrollView!!.getChildAt(0).measuredHeight - scrollView!!.measuredHeight
                else scrollView!!.getChildAt(0).measuredWidth - scrollView!!.measuredWidth
            val percent = scrollDistance * ((markIndex) / (paginatorChildCount - 1).toFloat())

            when(scrollView) {
                 is ItemStoreVScrollView ->
                     (scrollView as ItemStoreVScrollView).smoothScrollTo(0, percent.toInt())
                is ItemStoreHScrollView ->
                    (scrollView as ItemStoreHScrollView).smoothScrollTo(percent.toInt(), 0)
            }

            true
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView?.setOnScrollChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int ->
                doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount) } }
        else {
            viewTreeObserverListener = OnScrollChangedListener {
                doScrollItemStoreScrollView(scrollViewPaginator, paginatorChildCount) }
            viewTreeObserverListener?.let { observer ->
                scrollView?.viewTreeObserver?.addOnScrollChangedListener(observer) }
        }
    }

    protected fun addPaginatorIcon(scrollViewPaginator: GridLayout, icon: Int?) {
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val inflater = layoutInflater
        val equipmentView =
            inflater.inflate(R.layout.item_itemstore_scrollview_paginator_icon, null) as AppCompatImageView
        val param = GridLayout.LayoutParams()
        param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        param.width = if (isPortrait) ViewGroup.LayoutParams.WRAP_CONTENT else 0
        param.height = if ((!isPortrait)) ViewGroup.LayoutParams.WRAP_CONTENT else 0
        equipmentView.layoutParams = param
        equipmentView.setImageResource(icon!!)
        setIconFilter(equipmentView,  /*"#2D3635"*/selColor, 1f)
        scrollViewPaginator.addView(equipmentView)
    }

    protected fun doScrollItemStoreScrollView(paginatorGrid: GridLayout, paginatorChildCount: Int) {
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val scrollableLength =
            if (isPortrait) scrollView!!.getChildAt(0).measuredHeight - scrollView!!.measuredHeight
            else scrollView!!.getChildAt(0).measuredWidth - scrollView!!.measuredWidth
        val scrollPos = (if (isPortrait) scrollView!!.scrollY else scrollView!!.scrollX).toFloat()

        val maxDimPercentage = scrollPos / scrollableLength
        val markIndex = max(0.0, min((paginatorChildCount - 1).toDouble(),
            (paginatorChildCount * maxDimPercentage).toInt().toDouble())).toInt()

        Log.d("Scroll", "$paginatorChildCount $markIndex")

        for (j in 0 until paginatorChildCount) {
            val icon = paginatorGrid.getChildAt(j)
                .findViewById<ImageView>(R.id.image_equipmentIcon)
            setIconFilter(icon, selColor, 1f)
        }
        val icon = paginatorGrid.getChildAt(markIndex)
            .findViewById<ImageView>(R.id.image_equipmentIcon)
        setIconFilter(icon, unselColor, 1f)
    }

    companion object {
        protected fun closeItemDataView(dataView: View) {
            val isPortrait = dataView.context.resources.configuration.orientation ==
                    Configuration.ORIENTATION_PORTRAIT

            dataView.visibility = View.VISIBLE
            if (isPortrait) {
                dataView.animate()
                    .translationY(dataView.height.toFloat())
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)
                            dataView.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            dataView.visibility = View.GONE
                        }
                    })
            } else {
                dataView.animate()
                    .translationX(dataView.width.toFloat())
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)

                            dataView.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)

                            dataView.visibility = View.GONE
                        }
                    })
            }
        }

        protected fun openItemDataView(dataView: View) {
            if (dataView.visibility == View.VISIBLE) { return }

            val isPortrait =
                dataView.context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

            dataView.visibility = View.VISIBLE

            if (isPortrait) {
                dataView.translationY = dataView.height.toFloat()
                dataView.animate()
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)

                            dataView.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)

                            dataView.visibility = View.VISIBLE
                        }
                    })
                    .translationY(0f)
                    .setDuration(150)
            } else {
                dataView.translationX = dataView.width.toFloat()
                dataView.animate()
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)

                            dataView.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)

                            dataView.visibility = View.VISIBLE
                        }
                    })
                    .translationX(0f)
                    .setDuration(150)
            }
        }

        private fun setIconFilter(icon: ImageView, colorInt: Int, alpha: Float) {
            icon.setColorFilter(colorInt)
            icon.alpha = alpha
        }

        private fun setIconFilter(icon: ImageView, colorString: String, alpha: Float) {
            setIconFilter(icon, Color.parseColor(colorString), alpha)
        }
    }

    @SuppressLint("ResourceType")
    protected abstract fun buildStoreData()

    protected abstract fun createGroup(
        parent: LinearLayoutCompat, group: ItemStoreGroupModel)

    @SuppressLint("ResourceType")
    protected abstract fun buildGroupViews(
        parent: LinearLayoutCompat, scrollViewPaginator: GridLayout)

    protected abstract fun buildDataPopupView(dataView: View, groupIndex: Int, itemIndex: Int)

    protected abstract fun setPageTitle(titleView: AppCompatTextView)

    protected abstract fun setDataViewLayout(view: View)

    abstract override fun reset()

    fun handleBackAction(dataView: View) {
        if (dataView.visibility == View.VISIBLE) {
            closeItemDataView(dataView)
            return
        }
        backPressedHandler()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewTreeObserverListener?.let { observer ->
            scrollView?.viewTreeObserver?.removeOnScrollChangedListener(observer)
        }
    }

}
