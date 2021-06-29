package com.astar.osterrig.testexpandablerecycler.adapter2

const val GROUP = 0
const val ITEM = 1

sealed class ExpandableItem(
    val itemType: Int,
    var isEnabled: Boolean = true
) {
    data class Group(
        var items: List<Item> = listOf(),
        var isExpanded: Boolean = false
    ): ExpandableItem(GROUP)

    data class Item(
        var title: String
    ): ExpandableItem(ITEM)
}