package org.wit.boitcrp.models

data class PickUp(var pickUpName: String?, var pickUpIcon: String?, var locations: List<String>?) {


    override fun toString(): String =
        "Name: "+this.pickUpName+"\nIcon: "+this.pickUpIcon+"\nLocations: "+this.locations.toString()


}