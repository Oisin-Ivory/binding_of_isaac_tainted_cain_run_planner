package org.wit.boitcrp.models.managers

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.boitcrp.models.Item
import org.wit.placemark.helpers.*
import java.lang.reflect.Type
import kotlin.collections.ArrayList


object ItemManager {
    private var items : MutableList<Item> = emptyList<Item>().toMutableList()
    val JSON_FILE = "items.json"
    val listType: Type = object : TypeToken<ArrayList<Item>>() {}.type


    init {
//        if (exists(context, JSON_FILE)) {
//            deserialize()
//        }
        var i = 0;
        while(i < 10){
            items.add(Item())
            i++;
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


//    private fun serialize() {
//        val jsonString = gsonBuilder.toJson(items, listType)
//        write(context, JSON_FILE, jsonString)
//    }
//
//    private fun deserialize() {
//        val jsonString = read(context, JSON_FILE)
//        items = gsonBuilder.fromJson(jsonString, listType)
//    }

    fun findAll(): List<Item> {
        return items
    }

    fun create(item: Item) {
        item.id = generateRandomId()
        items.add(item)
        //serialize()
    }

    fun update(item: Item) {
        val foundItem: Item? = items.find { p -> p.id == item.id }
        if (foundItem != null) {
            foundItem.itemName = item.itemName
            foundItem.pickUps = item.pickUps
        }
        //serialize()
    }

    fun delete(item: Item) {
        for(aitems in items) {
            if(aitems.id == item.id){
                items.remove(aitems)
                //serialize()
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