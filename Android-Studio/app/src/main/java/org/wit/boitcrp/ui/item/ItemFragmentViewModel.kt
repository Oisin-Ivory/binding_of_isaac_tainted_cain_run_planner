package org.wit.boitcrp.ui.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.managers.ItemManager

class ItemFragmentViewModel : ViewModel() {
    private val item = MutableLiveData<Item>()

    var observableItem: LiveData<Item>
        get() = item
        set(value) {item.value = value.value}

    fun setItem(item:Item){
        this.item.value = item
    }
}

