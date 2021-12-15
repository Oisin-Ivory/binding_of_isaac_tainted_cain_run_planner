package org.wit.boitcrp.main
import android.app.Application
import org.wit.boitcrp.managers.*

class MainApp : Application() {

    lateinit var items: ItemManager
    lateinit var runs: RunManager
    lateinit var pickups: PickUpManager
    lateinit var PACKAGE_NAME: String
    override fun onCreate() {
        super.onCreate()
        items = ItemManager(applicationContext)
        runs = RunManager(applicationContext)
        pickups = PickUpManager(applicationContext)
        PACKAGE_NAME = getApplicationContext().getPackageName();
    }
}