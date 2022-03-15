package org.wit.boitcrp.ui.runlist

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.models.managers.RunManager

class RunListFragmentViewModel : ViewModel() {

    private val runs = MutableLiveData<List<Run>>()

    val observableRunList: LiveData<List<Run>>
        get() = runs

    init {
        load()
    }

    fun load() {
        runs.value = RunManager.findAll()
    }


}