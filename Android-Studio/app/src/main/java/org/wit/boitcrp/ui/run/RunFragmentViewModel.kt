package org.wit.boitcrp.ui.run

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.boitcrp.firebase.FirebaseDBManager
import org.wit.boitcrp.models.Run
import java.lang.Exception

class RunFragmentViewModel : ViewModel() {
    private val run = MutableLiveData<Run>()

    var observableRun: LiveData<Run>
        get() = run
        set(value) {run.value = value.value}

    fun setRun(run: Run){
        this.run.value = run
    }
    fun deleteRun(userid: String,
                  id: String) {
        try {
            //DonationManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
        }
        catch (e: Exception) {
        }
    }
}