package org.wit.boitcrp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.R
import org.wit.boitcrp.databinding.ActivityItemListBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.activities.ItemActivity
import org.wit.boitcrp.adapters.ItemAdapter
import org.wit.boitcrp.adapters.ItemListener

class ItemsListActivity: AppCompatActivity(), ItemListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityItemListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ItemAdapter(app.items.findAll(),this)
        loadItems()
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ItemActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_back -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: Item) {
        val launcherIntent = Intent(this, ItemActivity::class.java)
        launcherIntent.putExtra("item_edit", item)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadItems() }
    }

    private fun loadItems() {
        showItems(app.items.findAll())
    }

    fun showItems (items: List<Item>) {
        binding.recyclerView.adapter = ItemAdapter(items, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}