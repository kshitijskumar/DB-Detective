package com.example.db_detective.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class TableLayoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var singleEntryViewsList = mutableListOf<TextView>()

     abstract fun setupSingleEntryViewInLayout(): TextView

     abstract fun removeExtraViewsFromLayout(view: TextView)

     open fun renderEntries(rowEntry: List<String>) {
         val totalEntriesInARow = rowEntry.size
         val totalViewsWeHave = singleEntryViewsList.size
         if (totalEntriesInARow > totalViewsWeHave) {
             (0 until totalEntriesInARow-totalViewsWeHave).forEach {
                 singleEntryViewsList.add(setupSingleEntryViewInLayout())
             }
         } else if (totalViewsWeHave > totalEntriesInARow) {
             (0 until totalViewsWeHave-totalEntriesInARow).forEach {
                 val removedView = singleEntryViewsList.removeLastOrNull()
                 removedView?.let { removeExtraViewsFromLayout(it) }
             }
         }

         rowEntry.forEachIndexed { index, value ->
             singleEntryViewsList[index].text = value
         }
     }

    fun setStartAndEndMargin(tv: TextView, marginValue: Int) {
        val layoutParams = tv.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = marginValue
        layoutParams.marginEnd = marginValue
    }

}