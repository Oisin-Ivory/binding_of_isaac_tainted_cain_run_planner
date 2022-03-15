package org.wit.boitcrp.ui.itemadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.managers.ItemManager

class ItemAddViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addItem(item: Item,edit: Boolean) {
        if(edit){
            status.value = try {
                println("______________________________________________________________________updaing item "+item.itemName)
                ItemManager.update(item)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }else{
            status.value = try {
                ItemManager.create(item)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }

}