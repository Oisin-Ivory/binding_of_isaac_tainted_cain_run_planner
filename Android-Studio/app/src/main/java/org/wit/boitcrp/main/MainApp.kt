package org.wit.boitcrp.main
import android.app.Application
import android.content.Context
import org.wit.boitcrp.models.managers.*

class MainApp : Application() {

    //lateinit var items: ItemManager
//    lateinit var runs: RunManager
    lateinit var pickups: PickUpManager
    lateinit var PACKAGE_NAME: String
    lateinit var appContext: Context
    override fun onCreate() {
        super.onCreate()
        //items = ItemManager(applicationContext)
        //runs = RunManager(applicationContext)
        pickups = PickUpManager(applicationContext)
        PACKAGE_NAME = getApplicationContext().getPackageName();
        appContext = this
    }

    companion object {
        lateinit var instance: MainApp
    }

    init {
        instance = this
    }
}
