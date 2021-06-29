package com.astar.osterrig.testexpandablerecycler.adapter.model

data class ChildItem(
    val parent: ParentItem,
    val id: Int,
    val text: String
) : Item {
    override fun getItemType(): Int = Item.CHILD
}