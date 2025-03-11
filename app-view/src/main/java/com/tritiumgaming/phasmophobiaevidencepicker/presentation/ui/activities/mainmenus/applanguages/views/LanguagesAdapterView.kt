package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.applanguages.views

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton

class LanguagesAdapterView(
    languages: ArrayList<GlobalPreferencesViewModel.LanguageObject>, selected: Int,
    onLanguageListener: OnLanguageListener
) : RecyclerView.Adapter<LanguagesAdapterView.ViewHolder>() {
    private val languages: ArrayList<GlobalPreferencesViewModel.LanguageObject>
    private val onLanguageListener: OnLanguageListener

    companion object {
        private var mPreviousIndex = 0
    }

    init {
        mPreviousIndex = selected
        this.languages = languages
        this.onLanguageListener = onLanguageListener
    }

    class ViewHolder(view: View, onLanguageListener: OnLanguageListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val nameTextView: AppCompatTextView? = itemView.findViewById(R.id.textView_languageName)
        val image: PETImageButton? = itemView.findViewById(R.id.imageView_languageChoiceIcon)
        val background: Drawable? = nameTextView?.background
        private val onLanguageListener: OnLanguageListener

        init {
            view.setOnClickListener(this)
            this.onLanguageListener = onLanguageListener
        }

        override fun onClick(v: View) {
            onLanguageListener.onNoteClick(adapterPosition)
            mPreviousIndex = adapterPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val messageView = inflater.inflate(R.layout.item_language, parent, false)
        return ViewHolder(messageView, this.onLanguageListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView?.let { textView ->
            textView.text = languages[position].name
            textView.isSelected = true
        }
        //color on item unselecting item
        holder.image?.visibility = if (mPreviousIndex == position) View.VISIBLE else View.INVISIBLE
        holder.background?.setLevel(if (mPreviousIndex == position) 1 else 0)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    interface OnLanguageListener {
        fun onNoteClick(position: Int)
    }

}