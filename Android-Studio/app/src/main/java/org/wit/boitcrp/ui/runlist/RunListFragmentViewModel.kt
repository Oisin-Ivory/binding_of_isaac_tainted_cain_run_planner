package org.wit.boitcrp.ui.runlist

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import org.wit.boitcrp.firebase.FirebaseDBManager
import org.wit.boitcrp.models.Run
import java.lang.Exception

//import org.wit.boitcrp.models.managers.RunManager

class RunListFragmentViewModel : ViewModel() {

    private val runs = MutableLiveData<List<Run>>()

    val observableRunList: LiveData<List<Run>>
        get() = runs

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    init {
        load()
    }

    fun load() {
        try {
            //DonationManager.findAll(liveFirebaseUser.value?.email!!, donationsList)
            readOnly.value = false
            println("---------------------------------------------------\n"+liveFirebaseUser.value?.uid!!)
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                runs)
            println("Report Load Success : ${runs.value.toString()}")
        }
        catch (e: Exception) {
            println("Report Load Error : $e.message")
        }
    }
    fun loadAll() {
        try {
            //DonationManager.findAll(liveFirebaseUser.value?.email!!, donationsList)
            readOnly.value = true
            FirebaseDBManager.findAll(
                runs)
            println("Report Load Success : ${runs.value.toString()}")
        }
        catch (e: Exception) {
            println("Report Load Error : $e.message")
        }
    }

    fun setRuns(runsToDisplay:List<Run>){
        runs.value = runsToDisplay
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
        }
        catch (e: Exception) {
            println("Report Delete Error : $e.message")
        }
    }

}