package org.wit.boitcrp.managers

import android.content.Context
import com.google.gson.reflect.TypeToken
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run
import org.wit.placemark.helpers.*
import java.io.File
import java.lang.reflect.Type
import java.nio.file.Paths

class RunManager(private val context: Context){
    private var runs : MutableList<Run> = emptyList<Run>().toMutableList()
    val JSON_FILE = "runs.json"
    val listType: Type = object : TypeToken<ArrayList<Run>>() {}.type

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(runs, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        runs = gsonBuilder.fromJson(jsonString, listType)
    }

    fun findAll(): List<Run> {
        return runs
    }

    fun create(run: Run) {
        run.id = generateRandomId()
        runs.add(run)
        serialize()
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


}