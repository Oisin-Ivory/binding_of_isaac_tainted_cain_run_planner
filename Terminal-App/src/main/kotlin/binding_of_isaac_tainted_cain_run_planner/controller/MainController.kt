package binding_of_isaac_tainted_cain_run_planner.controller
import binding_of_isaac_tainted_cain_run_planner.manager.*
import binding_of_isaac_tainted_cain_run_planner.models.*
import binding_of_isaac_tainted_cain_run_planner.view.*
import java.awt.SystemColor.menu

class MainController {

    //Load Pick Ups
    val mainView = MainView();
    //Todo migrate functions from controller to view
    fun start(){

        mainView.loadData()

        var input: Int

        do {
            input = mainView.menu()
            when(input) {
                1 -> {
                    println("Create Item")
                    createItem()
                }
                2->{
                    println("List Items")
                    listItems()

                }
                3 ->{
                    println("Remove Item")
                    removeItem()

                }
                4 ->{
                    println("Update Item")
                    updateItem()

                }
                5 -> {
                    println("Create Run")
                    createRun()
                }
                6 -> {
                    println("List Runs")
                    listRuns()

                }
                7 -> {
                    println("Remove Run")
                    removeRun()
                }
                8 -> {
                    println("Update Run")
                    updateRun()
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


    fun createItem(){
        mainView.addItem(mainView.createItem())
    }

    fun listItems(){
        mainView.listItems()
    }

    fun removeItem(){
        mainView.removeItem()
    }

    fun updateItem(){
        mainView.updateItem()
    }

    fun createRun(){
        mainView.addRun(mainView.createRun())

    }

    fun listRuns(){
        mainView.listRuns()
    }

    fun removeRun(){
        mainView.removeRun()
    }

    fun updateRun(){
        mainView.updateRun()
    }

    fun searchItems(){
        mainView.searchItems()
    }

    fun searchRuns(){
        mainView.searchRuns()
    }

}