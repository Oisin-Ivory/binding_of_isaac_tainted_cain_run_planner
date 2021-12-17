package org.wit.boitcrp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.ItemAdapter
import org.wit.boitcrp.adapters.ItemListener
import org.wit.boitcrp.databinding.ActivityItemListBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run

class ItemSelectActivity : AppCompatActivity(), ItemListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityItemListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var itemsToDisplay: List<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        binding.searchbtn.setOnClickListener(){
            searchTerms()
        }
        if (intent.hasExtra("itemlist")) {
            setContentView(binding.root)
            val items = intent.getParcelableArrayListExtra<Item>("itemlist")
            if(items == null) {
                return
            }
            itemsToDisplay = items.toList()
            setContentView(binding.root)
        }



        loadItems()
        registerRefreshCallback()
    }

    fun searchTerms(){
        val searchterms = binding.searchField.text.split(",")
        val searchitems = searchItems(searchterms)
        showItems(searchitems)
    }

    fun searchItems(searchTerms : List<String>):List<Item>{
        val returnList = emptyList<Item>().toMutableList()

        for (item in itemsToDisplay){
            var addToList = true;
            for(term in searchTerms){
                if(!item.toString().lowercase().contains(term.lowercase())){
                    addToList = false
                }
            }
            if(addToList) returnList.add(item)
        }
        return returnList
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
        val data = Intent()
        data.putExtra("item", item)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { showItems(app.items.findAll()) }
    }

    private fun loadItems() {
        showItems(itemsToDisplay)
    }

    fun showItems (items: List<Item>) {
        binding.recyclerView.adapter = ItemAdapter(items, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}