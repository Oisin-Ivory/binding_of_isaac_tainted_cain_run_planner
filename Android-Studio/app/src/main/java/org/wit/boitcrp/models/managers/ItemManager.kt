package org.wit.boitcrp.models.managers

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.helpers.*
import org.wit.boitcrp.main.MainApp
import java.lang.reflect.Type
import kotlin.collections.ArrayList

object ItemManager{
    private var items : MutableList<Item> = emptyList<Item>().toMutableList()
    val JSON_FILE = "items.json"
    val listType: Type = object : TypeToken<ArrayList<Item>>() {}.type
    val instance = MainApp.instance

    init {
        if (exists(instance.appContext, JSON_FILE)) {
            deserialize()
        }
    }

    fun searchItems(searchTerms : List<String>):List<Item>{
        val returnList = emptyList<Item>().toMutableList()

        for (item in items){
            var addToList = true;
            for(term in searchTerms){
                if(!item.toString().lowercase().contains(term.lowercase())){
                    addToList = false
                }
            }
            if(addToList) returnList.add(item)
        }
        return returnList
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(items, listType)
        write(instance.appContext, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(instance.appContext, JSON_FILE)
        items = gsonBuilder.fromJson(jsonString, listType)
    }

    fun findAll(): List<Item> {
        return items
    }

    fun create(item: Item) {
        item.id = generateRandomId()
        items.add(item)
        serialize()
    }

    fun update(item: Item) {

        val foundItem: Item? = items.find { p -> p.id == item.id }
        if (foundItem != null) {
            println("______________________________________________\nFound item: "+foundItem.itemName)
            foundItem.itemName = item.itemName
            foundItem.pickUps = item.pickUps
        }
        serialize()
    }

    fun delete(id: String) {
        for(aitems in items) {
            if(aitems.id == id){
                items.remove(aitems)
                serialize()
                return
            }
        }
    }

}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}