package binding_of_isaac_tainted_cain_run_planner.view
import binding_of_isaac_tainted_cain_run_planner.manager.*
import binding_of_isaac_tainted_cain_run_planner.models.Item
import binding_of_isaac_tainted_cain_run_planner.models.PickUp
import binding_of_isaac_tainted_cain_run_planner.models.Run

class MainView{
    val pickUpManager = PickUpManager()
    val runManager = RunManager()
    val itemManager = ItemManager()

    fun loadData(){
        runManager.load()
        pickUpManager.load()
        itemManager.load()
    }

    fun addItem(item : Item){
        itemManager.add(item)
        itemManager.save()
    }

    fun addRun(run : Run){
        runManager.add(run)
        runManager.save()
    }

    fun createItem(): Item {
        var inputString: String?
        val itemToBeAdded = Item(null,null)
        val itemToBeAddedPickUps = Array<PickUp>(8){ PickUp(null,null,null) }
        println("Input item Name: ")
        inputString = readLine()!!
        itemToBeAdded.itemName = inputString;
        println("Input items requirements: ")
        println(listPickUps())
        for(i in 0 .. 7){
            var badInput = true
            while (badInput){
                inputString = readLine()
                if (inputString != null && inputString.toInt()>0 && inputString.toInt()<pickUpManager.getPickUps().size) {
                    badInput = false
                }
            }
            if (inputString != null) {
                itemToBeAddedPickUps[i] = pickUpManager.getPickUp(inputString.toInt()-1)
            }
            println("Added Item: ${itemToBeAddedPickUps[i].pickUpName}")
        }
        itemToBeAdded.pickUps = itemToBeAddedPickUps
        return itemToBeAdded
    }

    fun listItems(){
        for(item in itemManager.getItems()){
            println(item.toString())
        }
    }

    fun removeItem(){
        displayItemsWithIndex()
        var goodInput = false
        var itemToRemoveIndex = -1
        while(!goodInput){

            itemToRemoveIndex = takeInput("Input Run to remove index, -1 to exit:")
            if(itemToRemoveIndex ==-1){
                return
            }
            if(itemToRemoveIndex >= itemManager.getItems().size || itemToRemoveIndex < 0){
                continue
            }
            goodInput = true
        }


        runManager.removeItemsFromRuns(itemManager.getItem(itemToRemoveIndex))
        itemManager.removeItem(itemToRemoveIndex)
        itemManager.save()
        runManager.save()
    }

    fun displayItemsWithIndex(){
        for(i in itemManager.getItems().indices){
            println("$i) ${itemManager.getItem(i).itemName}")
        }
    }

    fun updateItem(){
        displayItemsWithIndex()
        var goodInput = false
        var itemIndex : Int = 0
        var optionInput : Int
        while(!goodInput) {
            itemIndex = takeInput("Input item to update index: ")
            if(itemIndex >= itemManager.getItems().size || itemIndex < 0){
                continue
            }
            println("Is the item you want to update ${itemManager.getItem(itemIndex).itemName}?\n Yes : 1 | No : 0 | Return : -1")
            optionInput = takeInput("")
            if (optionInput == 1)goodInput = true
            else if (optionInput == -1) return
        }
        val updatedItem = itemManager.getItem(itemIndex).copy()
        println("What do you want to change?\n Name: 0 | Items: 1")

        optionInput = takeInput("")
        goodInput = false
        while(!goodInput) {
            if (optionInput == 0) {
                print("Enter New Name: ")
                val newName = readLine()
                if( newName.isNullOrEmpty()) {
                    break
                }
                goodInput = true
                itemManager.getItem(itemIndex).itemName = newName;

            } else if (optionInput == 1) {
                var inputValue = 0
                val newPickUps = Array<PickUp>(8){ PickUp(null,null,null) }
                println("Input items requirements: ")
                println(listPickUps())
                for(i in 0 .. 7){
                    inputValue = takeInput("")
                    newPickUps[i] = pickUpManager.getPickUp(inputValue-1)
                    println("Added Item: ${newPickUps[i].pickUpName}")
                }
                updatedItem.pickUps = newPickUps
                goodInput = true
                itemManager.getItem(itemIndex).pickUps = newPickUps.clone()
            }
        }
        itemManager.save()
    }

    fun createRun(): Run {
        val runToBeAdded = Run(null, null)
        val runToBeAddedItems : MutableList<Item> =  emptyList<Item>().toMutableList()

        println("Input Run Name: ")
        val inputString : String = readLine()!!
        runToBeAdded.runName = inputString;


        var runInput: Int
        displayItemsWithIndex()
        println("-1 to finalize run")
        do {
            runInput = takeInput("")
            when(runInput) {
                -1 -> println("Creating Run")
                else -> {
                    //getItemIndex
                    if(runInput >= itemManager.getItems().size || runInput < 0){
                        continue
                    }else {
                        runToBeAddedItems.add(itemManager.getItem(runInput))
                    }
                }
            }
            println()
        } while (runInput != -1)

        runToBeAdded.runItems = runToBeAddedItems as ArrayList<Item>?
        return runToBeAdded
    }

    fun listRuns() {

        for(run in runManager.getRuns()){
            println("\n_________________________________________________________")
            println(run.runName+"\n----------------------------------------\n" + run.formatRunItems())
            println("\n_________________________________________________________")
        }
    }

    fun removeRun(){
        displayRunsWithIndex()
        var goodInput = false
        var input = 0
        while(!goodInput){
            input = takeInput("Input Run to remove index -1 to exit: ")
            if(input ==-1){
                return
            }
            if(input >= runManager.getRuns().size || input < 0){
                continue
            }
            goodInput = true
        }
        runManager.removeRun(input)
        runManager.save()
    }

    fun displayRunsWithIndex() {
        for(i in runManager.getRuns().indices){
            println("$i) ${runManager.getRun(i).runName}")
        }
    }

    fun updateRun(){

        displayRunsWithIndex()
        var goodInput = false
        var runIndex : Int = 0
        var optionInput : Int
        while(!goodInput) {
            runIndex = takeInput("Input run index to update item: ")
            if(runIndex >= runManager.getRuns().size || runIndex < 0){
                continue
            }
            println("Is the run you want to update ${runManager.getRun(runIndex).runName}?\n Yes : 1 | No : 0 | Return : -1")
            optionInput = takeInput("")
            if (optionInput == 1)goodInput = true
            else if (optionInput == -1) return
        }
        println("What do you want to change?\n Name: 0 | Add Item: 1 | Remove Item: 2")

        optionInput = takeInput("")
        goodInput = false
        while(!goodInput) {
            if (optionInput == 0) {
                print("Enter New Name: ")
                val newName = readLine()
                if( newName.isNullOrEmpty()) {
                    break
                }
                goodInput = true
                runManager.getRun(runIndex).runName = newName;

            } else if (optionInput == 1) {
                var inputValue = 0
                displayItemsWithIndex()

                inputValue = takeInput("Input item index: ")
                if(inputValue >= itemManager.getItems().size || inputValue < 0){
                    continue
                }
                runManager.getRun(runIndex).addItem(itemManager.getItem(inputValue));
                goodInput = true
            }else if (optionInput == 2) {
                var inputValue = 0
                print(runManager.getRun(runIndex).listRunItemsWithIndex())
                inputValue = takeInput("Input item index: ")
                if(inputValue >= runManager.getRun(runIndex).runItems!!.size || inputValue < 0){
                    continue
                }
                runManager.getRun(runIndex).removeItem(inputValue)
                goodInput = true
            }
        }


        runManager.save()
    }

    fun searchItems(){
        println("Input Search Terms: ")
        println("Input -1 when ready: ")
        val searchTerms = emptyList<String>().toMutableList()
        var inputString : String
        inputString = readLine()!!
        while (inputString!="-1"){
            searchTerms.add(inputString)
            inputString = readLine()!!
        }

        val result = itemManager.searchItems(searchTerms)
        for(item in result){
            print(item.toString())
        }
    }

    fun searchRuns(){
        println("Input Search Terms: ")
        println("Input -1 when ready: ")
        val searchTerms = emptyList<String>().toMutableList()
        var inputString : String
        inputString = readLine()!!
        while (inputString!="-1"){
            searchTerms.add(inputString)
            inputString = readLine()!!
        }

        val result = runManager.searchRuns(searchTerms)
        for(run in result){
            print(run.formatRunString())
        }
    }



    fun listPickUps():String{

        var returnString = ""

        for(i in 1..pickUpManager.getPickUps().size){
            returnString = returnString+"\n${i}) ${pickUpManager.getPickUp(i-1).pickUpName}"


        }
        return  returnString
    }

    fun menu() : Int {

        val option : Int
        var input: String? = null

        println("Main Menu")
        println(" 1) Add Item")
        println(" 2) List Items")
        println(" 3) Remove Item")
        println(" 4) Update Item")
        println(" 5) Create Run")
        println(" 6) List Runs")
        println(" 7) Remove Run")
        println(" 8) Update Run")
        println(" 9) Search Items")
        println(" 10) Search Runs")
//    println(" 11) Search Runs by Name")
//    println(" 12) Search Runs by Name")
        println("-1) Exit")
        println()
        print("Enter an integer : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        //print(runManager.getRuns().toString())
        return option
    }

    fun takeInput(message : String) : Int{
        val inputToReturn : Int
        var inputToGetResult: String? = null
        print(message)
        inputToGetResult = readLine()!!
        inputToReturn = if (inputToGetResult.toIntOrNull() != null && !inputToGetResult.isEmpty())
            inputToGetResult.toInt()
        else
            -9
        return inputToReturn
    }

}