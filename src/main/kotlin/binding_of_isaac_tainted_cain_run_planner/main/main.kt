import binding_of_isaac_tainted_cain_run_planner.manager.*
import binding_of_isaac_tainted_cain_run_planner.models.*
import java.awt.Color.red

//Load Pick Ups
val pickUpManager = PickUpManager()
val runManager = RunManager()
val itemManager = ItemManager()


fun main(args:Array<String>){
    runManager.load()
    pickUpManager.load()
    itemManager.load()

    var input: Int

    do {
        input = menu()
        when(input) {
            1 -> {
                println("Create Item")
                itemManager.add(createItem())
                itemManager.save()
            }
            2->{
                println("List Items")
                listItems()

            }
            3 ->{
                println("Remove Item")
                removeItem()
                itemManager.save()
            }
            4 ->{
                println("Update Item")
                updateItem()
                itemManager.save()
            }
            5 -> {
                println("Create Run")
                runManager.add(createRun())
                runManager.save()
            }
            6 -> {
                println("List Runs")
                listRuns()

            }
            7 -> {
                println("Remove Run")
                removeRun()
                runManager.save()
            }
            8 -> {
                println("Update Run")
                updateRun()
                runManager.save()
            }
            9 -> {
                println("Searching items")
                searchItems()
            }
            10 -> {
                println("Searching Runs")
                searchRuns()
            }
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)

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
        inputString = readLine()
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
    itemManager.removeItem(takeInput("Input item to remove index: "))
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
                runToBeAddedItems.add(itemManager.getItem(runInput))
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
        println(run.runName+"\n----------------------------------------\n" + run.formatRunItems()+"\n")
        println("\n_________________________________________________________")
    }
}

fun removeRun(){
    displayRunsWithIndex()
    runManager.removeRun(takeInput("Input Run to remove index: "))
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
            runManager.getRun(runIndex).addItem(itemManager.getItem(inputValue));
            goodInput = true
        }else if (optionInput == 2) {
            var inputValue = 0
            print(runManager.getRun(runIndex).listRunItemsWithIndex())
            inputValue = takeInput("Input item index: ")
            runManager.getRun(runIndex).removeItem(inputValue)
            goodInput = true
        }
    }



}

// TODO: 1/10/2021 search functionality
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
    for(item in result){
        print(item.toString())
    }
}



fun listPickUps():String{
    return  "\n1) Read Heart" +
            "\n2) Soul Heart" +
            "\n3) Black Heart" +
            "\n4) Eternal Heart" +
            "\n5) Gold Heart" +
            "\n6) Bone Heart" +
            "\n7) Rotten Heart" +
            "\n8) Penny" +
            "\n9) Nickle" +
            "\n10) Dime" +
            "\n11) Lucky Penny" +
            "\n12) Key" +
            "\n13) Gold Key" +
            "\n14) Charged Key" +
            "\n15) Bomb" +
            "\n16) Golden Bomb" +
            "\n17) Giga Bomb" +
            "\n18) Micro Battery" +
            "\n19) Lil' Battery" +
            "\n20) Mega Battery" +
            "\n21) Card" +
            "\n22) Pill" +
            "\n23) Rune/Soul" +
            "\n24) Dice Shard" +
            "\n25) Cracked Key"


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
    println(" 9) Search Items by Name")
    println(" 10) Search Runs by Pickups")
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

