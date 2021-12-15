package org.wit.boitcrp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(var itemName: String = "",
                var id: Long = 0,
                var pickUps: Array<PickUp>? = arrayOf(PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp(),PickUp())
) : Parcelable {


}