package binding_of_isaac_tainted_cain_run_planner.models

data class Run(var runName: String?, var runItems: ArrayList<Item>?) {

    fun formatRunItems():String{
        var returnString : String = ""
        for(item in runItems!!){
            returnString += "${item.itemName}:\n" +
                    item.compactPickUpString()
        }

        return returnString
    }

    fun addItem(item: Item){
        runItems?.add(item)
    }

    fun removeItem(index: Int){
        runItems?.removeAt(index)
    }

    fun listRunItemsWithIndex(): String{
        var index = 0
        var returnString = ""
        for(i in runItems!!){
            returnString +=("$index) ${runItems!!.get(index).itemName}\n")
            index++
        }

        return returnString
    }
    override fun toString(): String =
        "Name: "+this.runName+"\nItems: "+this.runItems.toString()
}