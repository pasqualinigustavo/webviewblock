package com.webviewblock.presentation.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.webviewblock.databinding.LayoutEmptyHistoryBinding
import com.webviewblock.databinding.RowHistoryBinding
import com.webviewblock.domain.History

class HistoryAdapter(
    private val clickListener: (item: History) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<History> = emptyList<History>().toMutableList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setup(newItems: List<History>) {
        _showError = false
        items.clear()
        if (newItems.isNotEmpty()) {
            items.addAll(newItems)
        }
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        _firstLoading = true
        notifyDataSetChanged()
    }

    internal class EmptyViewHolder(val binding: LayoutEmptyHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal class MessageViewHolder(val binding: RowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MessageViewHolder) {
            val item = items[position]

            if (holder is MessageViewHolder) {
                //url
                val viewHolder = holder as MessageViewHolder
                viewHolder.binding.rowHistoryTextviewUrl.text = item.url
                //date
                viewHolder.binding.rowHistoryTextviewDate.text = item.time
                //click
                viewHolder.binding.root.setOnClickListener {
                    clickListener.invoke(item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items.isEmpty())
            return VIEW_TYPE_EMPTY_STATE
        return VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = RowHistoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return MessageViewHolder(binding)
            }
            else -> {
                val binding = LayoutEmptyHistoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private var _firstLoading = false
    private var _showError = false

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_EMPTY_STATE = 1
    }
}
