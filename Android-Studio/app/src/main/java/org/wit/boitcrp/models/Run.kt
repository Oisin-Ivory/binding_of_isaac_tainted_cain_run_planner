package org.wit.boitcrp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Run(var runName: String?,
               var id: Long?,
               var runItems: ArrayList<Item>?) : Parcelable {


    fun addItem(item: Item){
        runItems?.add(item)
    }

    fun removeItem(id: Long){
        val foundItem: Item? = runItems?.find { p -> p.id == id }
        if (foundItem != null) {
            runItems?.remove(foundItem)
        }

    }

}