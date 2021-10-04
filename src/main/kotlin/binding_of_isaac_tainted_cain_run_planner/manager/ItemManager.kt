package binding_of_isaac_tainted_cain_run_planner.manager

import binding_of_isaac_tainted_cain_run_planner.models.Item
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.nio.file.Paths

class ItemManager : Manager {
    private var items : MutableList<Item> = emptyList<Item>().toMutableList()
    val mapper = jacksonObjectMapper()

    fun getItems(): MutableList<Item> {
        return items;
    }
    fun getItem(index : Int): Item {
        return items[index]
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

    fun setItem(index : Int,item : Item){
        items[index] = item
    }
    fun removeItem(index : Int){
        items.removeAt(index)
    }
    fun add(item : Item){
        items.add(item)
    }

    override fun save() {
        mapper.writeValue(Paths.get("data/items.json").toFile(), items);
    }

    override fun load() {
        val loadItems = mapper.readValue<MutableList<Item>>(File("data/items.json"))
        items = loadItems
    }

}