package com.astar.osterrig.testexpandablerecycler.adapter.model

interface Item {

    fun getItemType(): Int

    companion object {
        const val PARENT = 0
        const val CHILD = 1
    }

}