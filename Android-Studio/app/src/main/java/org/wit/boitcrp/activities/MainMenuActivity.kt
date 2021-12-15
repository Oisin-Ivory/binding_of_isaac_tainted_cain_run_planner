package org.wit.boitcrp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import org.wit.boitcrp.databinding.MainMenuBinding
import org.wit.boitcrp.main.MainApp

class MainMenuActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: MainMenuBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        print("Created Main activity")
        super.onCreate(savedInstanceState)
        binding = MainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        binding.btnItems.setOnClickListener(){
            launchItemList()
        }
        binding.btnRuns.setOnClickListener(){
            launchRunList()
        }
    }



    private fun launchItemList(){
        val intent = Intent(this, ItemsListActivity::class.java)
        startActivity(intent)
    }

    private fun launchRunList(){
        val intent = Intent(this, RunListActivity::class.java)
        startActivity(intent)
    }
}