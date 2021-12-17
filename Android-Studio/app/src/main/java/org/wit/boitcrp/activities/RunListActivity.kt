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
import org.wit.boitcrp.adapters.RunAdapter
import org.wit.boitcrp.adapters.RunListener
import org.wit.boitcrp.databinding.ActivityRunListBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Run

class RunListActivity: AppCompatActivity(), RunListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityRunListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RunAdapter(app.runs.findAll(),this)
        binding.searchbtn.setOnClickListener(){
            searchRuns()
        }
        loadRuns()
        registerRefreshCallback()
    }

    fun searchRuns(){
        val terms = binding.searchField.text.split(",")
        val displayItems = app.runs.searchRuns(terms)
        showItems(displayItems)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(run: MenuItem): Boolean {
        when (run.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, RunActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_back -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(run)
    }

    override fun onRunClick(run: Run) {
        val launcherIntent = Intent(this, RunActivity::class.java)
        launcherIntent.putExtra("run_edit", run)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadRuns() }
    }

    private fun loadRuns() {
        showItems(app.runs.findAll())
    }

    fun showItems (runs: List<Run>) {
        binding.recyclerView.adapter = RunAdapter(runs, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}