package org.wit.boitcrp.ui.itemlist
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.managers.ItemManager

class ItemListViewModel : ViewModel() {

    private val itemList = MutableLiveData<List<Item>>()

    val observableItemList: LiveData<List<Item>>
        get() = itemList

    init {
        load()
    }

    fun delete(id:String){
        ItemManager.delete(id)
    }
    fun load() {
        itemList.value = ItemManager.findAll()
    }
    fun setItems(items:List<Item>) {
        itemList.value = items
    }
}