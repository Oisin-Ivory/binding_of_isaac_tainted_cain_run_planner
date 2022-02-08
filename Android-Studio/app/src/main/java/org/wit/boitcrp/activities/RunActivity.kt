package org.wit.boitcrp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.boitcrp.R
import org.wit.boitcrp.main.MainApp
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.adapters.ItemAdapter
import org.wit.boitcrp.adapters.ItemListener
import org.wit.boitcrp.databinding.ActivityRunBinding
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run


class RunActivity : AppCompatActivity(), ItemListener {

    private lateinit var binding: ActivityRunBinding

    var run = Run()

    lateinit var app: MainApp

    private val addItemIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val itemToAdd: Item  = result.data?.getParcelableExtra<Item>("item")!!
                run.addItem(itemToAdd)
                loadItems()
            }
        }

    private val removeItemIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val itemToRemove: Item  = result.data?.getParcelableExtra<Item>("item")!!
                run.removeItem(itemToRemove.id)
                loadItems()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityRunBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = ItemAdapter(app.items.findAll(),this)
        loadItems()


        if (intent.hasExtra("run_edit")) {
            edit = true
            run = intent.extras?.getParcelable("run_edit")!!

            binding.toolbarAdd.title = run.runName
            binding.runName.setText(run.runName)

            binding.btnAdd.setText(R.string.button_updatePrompt)
        }

        binding.btnAddItem.setOnClickListener() {

            val intent = Intent(this, ItemSelectActivity::class.java)
            val items = ArrayList<Item>(app.items.findAll())

            intent.putExtra("itemlist",items)
            addItemIntentLauncher.launch(intent)
        }

        binding.btnRemoveItem.setOnClickListener() {

            val intent = Intent(this, ItemSelectActivity::class.java)
            intent.putExtra("itemlist", run.runItems)
            removeItemIntentLauncher.launch(intent)
        }

        binding.btnAdd.setOnClickListener() {
            run.runName = binding.runName.text.toString()

            if (run.runName!!.isEmpty()) {
                Snackbar.make(it,R.string.input_namePrompt, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.runs.update(run.copy())
                } else {
                    app.runs.create(run.copy())

                }
            }
            setResult(RESULT_OK)
            finish()
        }

        loadItems()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(m_item: MenuItem): Boolean {
        when (m_item.itemId) {
            R.id.item_cancel -> {
                finish()
            }

            R.id.item_delete -> {
                app.runs.delete(run)
                finish()
            }
        }
        return super.onOptionsItemSelected(m_item)
    }

    private fun loadItems() {
        showItems(run.FindAllItems())
    }

    fun showItems (items: List<Item>) {
        //binding.recyclerView.adapter = ItemAdapter(items, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onItemClick(item: Item) {
        val launcherIntent = Intent(this, ItemActivity::class.java)
        launcherIntent.putExtra("item_edit", item)
        startActivity(launcherIntent)
    }


}