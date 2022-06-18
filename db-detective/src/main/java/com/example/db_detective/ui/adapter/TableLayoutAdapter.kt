package com.example.db_detective.ui.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.db_detective.databinding.LayoutTableRowItemBinding

class TableLayoutAdapter : ListAdapter<List<String>, TableLayoutViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<List<String>>() {
            override fun areItemsTheSame(oldItem: List<String>, newItem: List<String>): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: List<String>, newItem: List<String>): Boolean {
                return oldItem == newItem
            }
        }

        private const val VH_TYPE_HEADING = 0
        private const val VH_TYPE_ROW_ENTRY = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableLayoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutTableRowItemBinding.inflate(inflater, parent, false)
        return when(viewType) {
            VH_TYPE_HEADING -> TableLayoutColumnNameViewHolder(binding)
            VH_TYPE_ROW_ENTRY -> TableLayoutRowViewHolder(binding)
            else -> TableLayoutRowViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: TableLayoutViewHolder, position: Int) {
        val rowEntry = getItem(position)
        holder.renderEntries(rowEntry)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VH_TYPE_HEADING
        } else {
            VH_TYPE_ROW_ENTRY
        }
    }

    inner class TableLayoutRowViewHolder(private val binding: LayoutTableRowItemBinding): TableLayoutViewHolder(binding.root) {
        override fun setupSingleEntryViewInLayout(): TextView {
            val tv =  TextView(binding.root.context).apply {
                width = 200
                gravity = Gravity.CENTER
                textSize = 14f
                setPadding(0, 8, 0, 8)
            }
            binding.root.addView(tv)
            setStartAndEndMargin(tv, 20)
            return tv
        }

        override fun removeExtraViewsFromLayout(view: TextView) {
            binding.root.removeView(view)
        }
    }

    inner class TableLayoutColumnNameViewHolder(private val binding: LayoutTableRowItemBinding): TableLayoutViewHolder(binding.root) {
        override fun setupSingleEntryViewInLayout(): TextView {
            val tv =  TextView(binding.root.context).apply {
                setTextColor(Color.BLACK)
                width = 200
                gravity = Gravity.CENTER
                textSize = 18f
                setPadding(0, 12, 0, 12)
            }
            binding.root.addView(tv)
            setStartAndEndMargin(tv, 20)
            return tv
        }

        override fun removeExtraViewsFromLayout(view: TextView) {
            binding.root.removeView(view)
        }
    }
}