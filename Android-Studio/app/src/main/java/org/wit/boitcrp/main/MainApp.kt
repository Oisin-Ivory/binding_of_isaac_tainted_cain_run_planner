package org.wit.boitcrp.main
import android.app.Application
import org.wit.boitcrp.managers.ItemManager
import org.wit.boitcrp.managers.PickUpManager
import org.wit.boitcrp.managers.RunManager

class MainApp : Application() {

    lateinit var items: ItemManager
    lateinit var pickups: PickUpManager
    lateinit var runs: RunManager

    override fun onCreate() {
        super.onCreate()

        items.load()
        pickups.load()
        runs.load()
    }
}