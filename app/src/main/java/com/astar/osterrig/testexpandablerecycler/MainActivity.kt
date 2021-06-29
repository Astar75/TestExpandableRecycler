package com.astar.osterrig.testexpandablerecycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.astar.osterrig.testexpandablerecycler.adapter2.ExpandableItem
import com.astar.osterrig.testexpandablerecycler.adapter2.TestAdapter
import com.astar.osterrig.testexpandablerecycler.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val testAdapter = TestAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupControlButtons()
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerTest.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerTest.adapter = testAdapter
        testAdapter.setData(getTestData())
    }

    private fun setupControlButtons() = with(binding) {
        buttonUpdate.setOnClickListener {
            val data = getTestData()
            testAdapter.setData(data)
        }
    }

    private fun getTestData(): List<ExpandableItem> {
        val items = mutableListOf<ExpandableItem>()
        val parentCount = Random.nextInt(30)
        for (parentId in 0..parentCount) {
            val parent = ExpandableItem.Group()
            val childItems = ArrayList<ExpandableItem.Item>()
            val childCount = Random.nextInt(20)
            for (childId in 0..childCount) {
                childItems.add(ExpandableItem.Item("Child #$parentId - $childId"))
            }
            parent.items = childItems
            items.add(parent)
        }
        return items
    }

    /*private fun getTestData(): List<Item> {
        val items = mutableListOf<Item>()
        val parentCount = Random.nextInt(30)
        for (parentId in 0..parentCount) {
            val parent = ParentItem(parentId)
            val childItems = ArrayList<ChildItem>()
            val childCount = Random.nextInt(20)
            for (childId in 0..childCount) {
                childItems.add(ChildItem(parent, childId, "Child #$parentId - $childId"))
            }
            parent.childItems.addAll(childItems)
            items.add(parent)
        }
        return items
    }*/


}