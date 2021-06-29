package com.astar.osterrig.testexpandablerecycler.adapter

import androidx.recyclerview.widget.DiffUtil
import com.astar.osterrig.testexpandablerecycler.adapter.model.Item

class TestDiffUtil : DiffUtil.Callback() {

    private val oldItems: MutableList<Item> = ArrayList()
    private val newItems: MutableList<Item> = ArrayList()

    fun setItems(oldItems: List<Item>, newItems: List<Item>) {
        this.newItems.clear()
        this.newItems.addAll(newItems)
        this.oldItems.clear()
        this.oldItems.addAll(oldItems)
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldpos: Int, newpos: Int): Boolean {
        val oldItem = oldItems[oldpos]
        val newItem = newItems[newpos]

        return oldItem == newItem && oldItem.getItemType() == newItem.getItemType()
    }

    override fun areContentsTheSame(oldpos: Int, newpos: Int): Boolean {
        val oldItem = oldItems[oldpos]
        val newItem = newItems[newpos]

        return oldItem == newItem
    }
}