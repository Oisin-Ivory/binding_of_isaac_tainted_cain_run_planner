package org.wit.boitcrp.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize


@IgnoreExtraProperties
@Parcelize
data class Run(var uid: String? = "",
               var runName: String? = "",
               var seed: String? = "",
               var email: String? = "",
               var runItems: List<Item>? = arrayListOf())
    : Parcelable {


    @Exclude
    fun addItem(item: Item){
        val newRunItems = runItems?.toMutableList()
        newRunItems?.add(item)
        runItems = newRunItems
    }
    @Exclude
    fun removeItem(id: String){
        val foundItem: Item? = runItems?.find { p -> p.id == id }
        if (foundItem != null) {
            val newRunItems = runItems?.toMutableList()
            newRunItems?.remove(foundItem)
            runItems = newRunItems
        }

    }
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "runName" to runName,
            "seed" to seed,
            "email" to email,
            "runItems" to runItems
        )
    }


}