package com.astar.osterrig.testexpandablerecycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.testexpandablerecycler.R
import com.astar.osterrig.testexpandablerecycler.adapter.model.ChildItem
import com.astar.osterrig.testexpandablerecycler.adapter.model.Item
import com.astar.osterrig.testexpandablerecycler.adapter.model.ParentItem

class TestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList: MutableList<Item> = ArrayList()

    fun setItems(items: List<Item>) {
        val diff = TestDiffUtil()
        diff.setItems(itemList, items)
        val resultDiff  = DiffUtil.calculateDiff(diff)
        itemList.clear()
        itemList.addAll(items)
        resultDiff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Item.CHILD -> ChildViewHolder(parent.inflate(R.layout.item_child))
            else -> ParentViewHolder(parent.inflate(R.layout.item_parent))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            Item.CHILD -> {
                val childViewHolder = holder as ChildViewHolder
                childViewHolder.childItem = itemList[position] as ChildItem
                childViewHolder.bind()
            }
            else -> {
                val parentViewHolder = holder as ParentViewHolder
                val parentItem = itemList[position] as ParentItem
                parentViewHolder.parentItem = parentItem
                parentViewHolder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].getItemType()
    }

    override fun getItemCount(): Int = itemList.size


    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var parentItem: ParentItem
        private val titleParent = itemView.findViewById<TextView>(R.id.parentTitle)

        init {
            itemView.setOnClickListener {
                val startPosition = adapterPosition + 1
                val count = parentItem.childItems.count()

                if (parentItem.isExpanded) {
                    itemList.removeAll(parentItem.childItems)
                    notifyItemRangeRemoved(startPosition, count)
                    parentItem.isExpanded = false
                } else {
                    itemList.addAll(startPosition, parentItem.childItems)
                    notifyItemRangeInserted(startPosition, count)
                    parentItem.isExpanded = true
                }
            }
            updateExpandedViewState()
        }

        private fun updateExpandedViewState() {

        }

        fun bind() {
            titleParent.text = "Parent ${parentItem.id}"
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var childItem: ChildItem
        private val titleChild = itemView.findViewById<TextView>(R.id.childTitle)

        fun bind() {
            titleChild.text = childItem.text
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layout: Int): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false)
}