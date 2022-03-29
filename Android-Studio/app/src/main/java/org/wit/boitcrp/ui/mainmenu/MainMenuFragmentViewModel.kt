package org.wit.boitcrp.ui.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.boitcrp.firebase.FirebaseDBManager
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.models.managers.ItemManager
import java.lang.Exception

class MainMenuFragmentViewModel : ViewModel() {

    private val runs = MutableLiveData<List<Run>>()

    var runCount : String = ""
    var itemCount : String = ""

    val observableRunList: LiveData<List<Run>>
        get() = runs

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            //DonationManager.findAll(liveFirebaseUser.value?.email!!, donationsList)
            println("---------------------------------------------------\n"+liveFirebaseUser.value?.uid!!)
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                runs)
            println("Report Load Success : ${runs.value.toString()}")
            runCount = runs.value?.size.toString()
            itemCount = ItemManager.findAll().size.toString()
        }
        catch (e: Exception) {
            println("Report Load Error : $e.message")
        }
    }


}


//FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
//runs)