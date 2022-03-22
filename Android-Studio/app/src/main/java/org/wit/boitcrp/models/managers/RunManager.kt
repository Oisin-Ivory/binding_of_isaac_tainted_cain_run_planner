/*package org.wit.boitcrp.models.managers

import android.content.Context
import com.google.gson.reflect.TypeToken
import org.wit.boitcrp.models.Run
import org.wit.placemark.helpers.*
import java.lang.reflect.Type

object RunManager{
    private var runs : MutableList<Run> = emptyList<Run>().toMutableList()
    val JSON_FILE = "runs.json"
    val listType: Type = object : TypeToken<ArrayList<Run>>() {}.type

//    init {
//        if (exists(context, JSON_FILE)) {
//            deserialize()
//        }
//    }
//
//    private fun serialize() {
//        val jsonString = gsonBuilder.toJson(runs, listType)
//        write(context, JSON_FILE, jsonString)
//    }
//
//    private fun deserialize() {
//        val jsonString = read(context, JSON_FILE)
//        runs = gsonBuilder.fromJson(jsonString, listType)
//    }

    fun findAll(): List<Run> {
        return runs
    }

    fun create(run: Run) {
        run.id = generateRandomId()
        runs.add(run)
        //serialize()
    }

    fun update(run: Run){
        val foundRun: Run? = runs.find { p -> p.id == run.id }
        if (foundRun != null) {
            foundRun.runName = run.runName
            foundRun.runItems = run.runItems
            foundRun.seed = run.seed
        }
        //serialize()
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

    fun delete(run: Run) {
        for(aruns in runs) {
            if(aruns.id == run.id){
                runs.remove(aruns)
                //serialize()
                return
            }
        }
    }


}*/