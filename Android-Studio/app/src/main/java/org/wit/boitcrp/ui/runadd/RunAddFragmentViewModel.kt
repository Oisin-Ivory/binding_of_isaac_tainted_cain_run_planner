package org.wit.boitcrp.ui.runadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.boitcrp.firebase.FirebaseDBManager
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run

class RunAddFragmentViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRun(firebaseUser: MutableLiveData<FirebaseUser>,
               run: Run) {
        status.value = try {
            FirebaseDBManager.create(firebaseUser,run)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun updateRun(userid:String,
                  runid:String,
                  run: Run) {
        status.value = try {
            FirebaseDBManager.update(userid,runid,run)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }



}