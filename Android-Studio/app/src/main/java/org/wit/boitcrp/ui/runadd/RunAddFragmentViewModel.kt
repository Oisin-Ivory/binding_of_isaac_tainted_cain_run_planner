package org.wit.boitcrp.ui.runadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.models.managers.RunManager

class RunAddFragmentViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRun(run: Run) {
        status.value = try {
            RunManager.create(run)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }


}