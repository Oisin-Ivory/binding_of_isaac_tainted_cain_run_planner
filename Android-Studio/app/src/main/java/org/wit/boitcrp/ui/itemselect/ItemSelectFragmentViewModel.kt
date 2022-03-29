package org.wit.boitcrp.ui.itemselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.managers.ItemManager

class ItemSelectFragmentViewModel: ViewModel() {

    private val itemList = MutableLiveData<List<Item>>()

    val observableItemList: LiveData<List<Item>>
        get() = itemList


    fun set(displayItemList:List<Item>) {
        itemList.value = displayItemList
    }
}