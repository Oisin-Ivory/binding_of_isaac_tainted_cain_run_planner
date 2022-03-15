package org.wit.boitcrp.ui.run

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.boitcrp.models.Run

class RunFragmentViewModel : ViewModel() {
    private val run = MutableLiveData<Run>()

    var observableRun: LiveData<Run>
        get() = run
        set(value) {run.value = value.value}

    fun setRun(run: Run){
        this.run.value = run
    }
}