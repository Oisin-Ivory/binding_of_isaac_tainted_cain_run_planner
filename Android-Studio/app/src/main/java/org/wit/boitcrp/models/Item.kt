package org.wit.boitcrp.models

import android.content.res.Resources
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import org.wit.boitcrp.BuildConfig
import org.wit.boitcrp.main.MainApp

@IgnoreExtraProperties
@Parcelize
data class Item(var itemName: String = "",
                var id: String = "",
                var pickUps: List<PickUp>? = listOf(PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp())
) : Parcelable {

    @Exclude
    fun GetPickUpResString(slot : Int) : Int {
        println("String is: " + pickUps?.get(slot)?.getResString() + " Package name: "+BuildConfig.APPLICATION_ID)
        return MainApp.instance.resources.getIdentifier(pickUps?.get(slot)?.getResString(),"drawable",BuildConfig.APPLICATION_ID)
    }

    @Exclude
    fun setPickUp(position:Int,pickup:PickUp){
        val newPickUps = pickUps?.toMutableList()
        newPickUps?.set(position,pickup)
        pickUps = newPickUps
    }



}

