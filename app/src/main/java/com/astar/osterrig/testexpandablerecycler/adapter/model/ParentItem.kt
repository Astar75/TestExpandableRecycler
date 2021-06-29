package com.astar.osterrig.testexpandablerecycler.adapter.model

data class ParentItem(val id: Int) : Item {
    val childItems = ArrayList<ChildItem>()
    var isExpanded = false
    var selectedChild: ChildItem? = null
    override fun getItemType(): Int = Item.PARENT
}