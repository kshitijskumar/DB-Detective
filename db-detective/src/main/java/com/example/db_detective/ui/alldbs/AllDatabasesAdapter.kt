package com.example.db_detective.ui.alldbs

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.db_detective.databinding.LayoutSingleDatabaseItemBinding

class AllDatabasesAdapter(
    private val onTableNameClicked: (SingleDatabaseModel) -> Unit
) : ListAdapter<SingleDatabaseModel, AllDatabasesAdapter.SingleDatabaseViewHolder>(diffUtil) {


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SingleDatabaseModel>() {
            override fun areItemsTheSame(
                oldItem: SingleDatabaseModel,
                newItem: SingleDatabaseModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SingleDatabaseModel,
                newItem: SingleDatabaseModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleDatabaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutSingleDatabaseItemBinding.inflate(inflater, parent, false)
        return SingleDatabaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleDatabaseViewHolder, position: Int) {
        val data = getItem(position)
        holder.render(data)
    }

    inner class SingleDatabaseViewHolder(private val binding: LayoutSingleDatabaseItemBinding): RecyclerView.ViewHolder(binding.root) {
        private var data: SingleDatabaseModel? = null

        init {
            binding.root.setOnClickListener {
                data?.let(onTableNameClicked)
            }
        }

        fun render(data: SingleDatabaseModel) {
            this.data = data
            val stringToDisplay = "${data.databaseName} -> ${data.tableName}"
            val delimiterStartIndex = data.databaseName.length + 1
            val spanString = SpannableString(stringToDisplay)
            spanString.setSpan(
                StyleSpan(Typeface.BOLD_ITALIC),
                0,
                delimiterStartIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanString.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                delimiterStartIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            binding.tvName.text = spanString
        }
    }
}

data class SingleDatabaseModel(
    val databaseName: String,
    val tableName: String
)