package org.wit.boitcrp.models
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize


@IgnoreExtraProperties
@Parcelize
data class PickUp(var pickUpName: String? = "Empty",
                  var pickUpIcon: String? = "empty.png",
                  var locations: List<String>? = listOf<String>()) : Parcelable {


    @Exclude
    fun getResString():String{
        return pickUpIcon?.split(".")?.get(0) ?: ""
    }

}