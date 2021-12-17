package org.wit.boitcrp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Run(var runName: String? = "",
               var id: Long? = 0,
               var runItems: ArrayList<Item>? = arrayListOf()) : Parcelable {


    fun addItem(item: Item){
        runItems?.add(item)
    }

    fun removeItem(id: Long){
        val foundItem: Item? = runItems?.find { p -> p.id == id }
        if (foundItem != null) {
            runItems?.remove(foundItem)
        }

    }

    fun FindAllItems():ArrayList<Item>{
        return runItems!!
    }
}