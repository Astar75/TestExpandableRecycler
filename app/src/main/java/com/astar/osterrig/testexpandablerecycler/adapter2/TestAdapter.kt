package com.astar.osterrig.testexpandablerecycler.adapter2

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.testexpandablerecycler.R
import java.util.*


/***
 * Source https://github.com/mozilla-mobile/guardian-vpn-android/blob/13acfd327be57c5fa2c536ea7f9b1ff8fd5301f5/app/src/main/java/org/mozilla/firefox/vpn/apptunneling/ui/AppGroupViewHolder.kt
 */

class TestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<ExpandableItem> = Collections.emptyList()

    fun setData(data: List<ExpandableItem>) {
        getDiffResult(items, data).dispatchUpdatesTo(this)
        items = data.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM ->
                ItemViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_child, parent, false)
                )
            else ->
                GroupViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_parent, parent, false),
                    ::onGroupClicked
                )
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].itemType

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM -> {
                (holder as ItemViewHolder).bind(items[position] as ExpandableItem.Item)
            }
            else -> {
                (holder as GroupViewHolder).bind(items[position] as ExpandableItem.Group)
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder.itemViewType) {
            ITEM -> {
                (holder as ItemViewHolder).unbind()
            }
        }
        super.onViewRecycled(holder)
    }

    private fun onGroupClicked(holder: GroupViewHolder) {
        val groupItem = items[holder.adapterPosition] as ExpandableItem.Group
        val startPosition = holder.adapterPosition + 1
        val count = groupItem.items.size

        if (groupItem.isExpanded) {
            items.removeAll(groupItem.items)
            notifyItemRangeRemoved(startPosition, count)
            (items[holder.adapterPosition] as ExpandableItem.Group).isExpanded = false
        } else {
            items.addAll(startPosition, groupItem.items)
            notifyItemRangeInserted(startPosition, count)
            (items[holder.adapterPosition] as ExpandableItem.Group).isExpanded = true
        }
        notifyItemChanged(holder.adapterPosition)

        Log.d("GroupAdapter", "onGroupClicked: CLICKED")
    }

    class GroupViewHolder(
        itemView: View,
        groupClick: (GroupViewHolder) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.parentTitle)

        init {
            textView.setOnClickListener { groupClick.invoke(this) }
        }

        @SuppressLint("SetTextI18n")
        fun bind(groupItem: ExpandableItem.Group) {
            textView.text = "Size - " + groupItem.items.size
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.childTitle)

        @SuppressLint("SetTextI18n")
        fun bind(item: ExpandableItem.Item) {
            textView.text = item.title
        }

        fun unbind() {
            // do something
        }
    }

    private fun getDiffResult(
        oldList: List<ExpandableItem>,
        newList: List<ExpandableItem>
    ): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val newItem = newList[newItemPosition]
                val oldItem = oldList[oldItemPosition]

                if (newItem is ExpandableItem.Group && oldItem is ExpandableItem.Group) {
                    return newItem == oldItem
                }
                if (newItem is ExpandableItem.Item && oldItem is ExpandableItem.Item) {
                    return newItem.title == oldItem.title
                }
                return false
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val newItem = newList[newItemPosition]
                val oldItem = oldList[oldItemPosition]

                if (newItem is ExpandableItem.Group && oldItem is ExpandableItem.Group) {
                    return newItem.items == oldItem.items &&
                            newItem.isExpanded == oldItem.isExpanded &&
                            newItem.isEnabled == oldItem.isEnabled
                }
                if (newItem is ExpandableItem.Item && oldItem is ExpandableItem.Item) {
                    return newItem.isEnabled == oldItem.isEnabled
                }
                return false
            }
        })
    }
}