package binding_of_isaac_tainted_cain_run_planner.models

data class PickUp(var pickUpName: String?, var pickUpIcon: String?, var locations: List<String>?) {


    override fun toString(): String =
        "Name: "+this.pickUpName+"\nIcon: "+this.pickUpIcon+"\nLocations: "+this.locations.toString()

//    fun toJSONString():String{
//
//        return "    {\n        \"pickUpName\":\""+pickUpName+"\",\n        \"pickUpIcon\":\""+pickUpIcon+"\",\n        \"locations\":[\n"+formatLocationJson()+"        ]\n    },"
//    }
//    fun formatLocationJson():String{
//        var locationString = ""
//        for(loc in locations!!){
//            if(loc == locations!!.last()){
//                locationString += "            \"$loc\"\n"
//            }else {
//                locationString += "            \"$loc\",\n"
//            }
//        }
//        return locationString
//    }
}