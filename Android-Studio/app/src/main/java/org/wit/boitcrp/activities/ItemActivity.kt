package org.wit.boitcrp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.boitcrp.R
import org.wit.boitcrp.databinding.ActivityItemBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import android.widget.ArrayAdapter
import org.wit.boitcrp.models.PickUp


class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding

    var item = Item()

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp


        if (intent.hasExtra("item_edit")) {
            edit = true
            item = intent.extras?.getParcelable("item_edit")!!

            binding.toolbarAdd.title = item.itemName
            binding.itemName.setText(item.itemName)
            binding.btnAdd.setText(R.string.button_updatePrompt)
        }

        binding.btnAdd.setOnClickListener() {
            item.itemName = binding.itemName.text.toString()
            item.pickUps?.set(0,
                app.pickups.findPickupName(binding.spinnerpickup1.getSelectedItem().toString())!!
            )
            item.pickUps?.set(1,
                app.pickups.findPickupName(binding.spinnerpickup2.getSelectedItem().toString())!!
            )
            item.pickUps?.set(2,
                app.pickups.findPickupName(binding.spinnerpickup3.getSelectedItem().toString())!!
            )
            item.pickUps?.set(3,
                app.pickups.findPickupName(binding.spinnerpickup4.getSelectedItem().toString())!!
            )
            item.pickUps?.set(4,
                app.pickups.findPickupName(binding.spinnerpickup5.getSelectedItem().toString())!!
            )
            item.pickUps?.set(5,
                app.pickups.findPickupName(binding.spinnerpickup6.getSelectedItem().toString())!!
            )
            item.pickUps?.set(6,
                app.pickups.findPickupName(binding.spinnerpickup7.getSelectedItem().toString())!!
            )
            item.pickUps?.set(7,
                app.pickups.findPickupName(binding.spinnerpickup8.getSelectedItem().toString())!!
            )

            if (item.itemName.isEmpty()) {
                Snackbar.make(it,R.string.input_namePrompt, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.items.update(item.copy())
                } else {
                    app.items.create(item.copy())

                }
            }
            setResult(RESULT_OK)
            finish()
        }

        initSpinners(edit)

    }

    fun initSpinners(edit: Boolean) {
        val spinners: List<Spinner>
        spinners = mutableListOf(
            binding.spinnerpickup1,
            binding.spinnerpickup2,
            binding.spinnerpickup3,
            binding.spinnerpickup4,
            binding.spinnerpickup5,
            binding.spinnerpickup6,
            binding.spinnerpickup7,
            binding.spinnerpickup8
        )

        for (spinner in spinners){

            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, app.pickups.findAllNames()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(adapter)
        }

        if(edit){
            for (i in 0..7){
                item.pickUps?.get(i)
                    ?.let { app.pickups.getPickUpIndex(it) }?.let { spinners[i].setSelection(it) }
            }
        }


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
                app.items.delete(item)
                println("---------------------------------------\nDeleting item " + item.id)
                finish()
            }
        }
        return super.onOptionsItemSelected(m_item)
    }


}