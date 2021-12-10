package org.wit.boitcrp.managers

import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.nio.file.Paths

class RunManager : Manager {
    private var runs : MutableList<Run> = emptyList<Run>().toMutableList()
    val mapper = jacksonObjectMapper()

    fun getRuns(): MutableList<Run> {
        return runs;
    }
    fun getRun(index : Int): Run {
        return runs[index]
    }

    fun setRun(index : Int): Run {
        return runs[index]
    }
    fun removeRun(index : Int){
        runs.removeAt(index)
    }
    fun add(run : Run){
        runs.add(run)
    }

    fun searchRuns(searchTerms : List<String>):List<Run>{
        val returnList = emptyList<Run>().toMutableList()

        for (run in runs){
            var addToList = true;
            for(term in searchTerms){
                if(!run.toString().lowercase().contains(term.lowercase())){
                    addToList = false
                }
            }
            if(addToList) returnList.add(run)
        }
        return returnList
    }

    override fun save() {
        mapper.writeValue(Paths.get("data/runs.json").toFile(), runs);
    }

    override fun load() {
        val runFile = File("data/runs.json")
        if(!runFile.exists()) {
            runFile.createNewFile()
            save()
            return
        }
        val loadRuns = mapper.readValue<MutableList<Run>>(runFile)

        runs = loadRuns
    }

    fun removeItemsFromRuns(itemToRemove: Item) {
        val runsToRemove : MutableList<Run> = emptyList<Run>().toMutableList()
        for(run in runs){
            for(item in run.runItems!!){
                if (item == itemToRemove){
                    run.runItems!!.remove(item)
                    break
                }
            }
            if(run.runItems!!.isEmpty()){
                runsToRemove.add(run)
            }
        }

        for(run in runsToRemove){
            runs.remove(run)
        }
    }

}