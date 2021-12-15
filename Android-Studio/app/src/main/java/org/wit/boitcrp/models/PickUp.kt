package org.wit.boitcrp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PickUp(var pickUpName: String? = "Empty",
                  var pickUpIcon: String? = "empty.png",
                  var locations: List<String>? = listOf<String>()) : Parcelable {

}