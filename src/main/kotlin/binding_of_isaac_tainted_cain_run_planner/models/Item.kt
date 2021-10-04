package binding_of_isaac_tainted_cain_run_planner.models

data class Item(var itemName: String?, var pickUps: Array<PickUp>?) {

    fun compactPickUpString():String{
        var pickUpsString = ""
        for(pickUp in this.pickUps!!){
            pickUpsString+="| ${pickUp.pickUpName} "
        }
        return pickUpsString + "|\n"
    }

    override fun toString(): String {
        return "Name: " + this.itemName + "\nItems: " + compactPickUpString()
    }

}