package org.wit.boitcrp.models

import android.content.res.Resources
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.wit.boitcrp.BuildConfig
import org.wit.boitcrp.main.MainApp

@Parcelize
data class Item(var itemName: String = "",
                var id: Long = 0,
                var pickUps: Array<PickUp>? = arrayOf(PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp())
) : Parcelable {
    public fun GetPickUpResString(slot : Int) : Int {
        println("String is: " + pickUps?.get(slot)?.getResString() + " Package name: "+BuildConfig.APPLICATION_ID)
        return MainApp.instance.resources.getIdentifier(pickUps?.get(slot)?.getResString(),"drawable",BuildConfig.APPLICATION_ID)
    }

}

