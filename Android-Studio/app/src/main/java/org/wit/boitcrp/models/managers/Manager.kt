package org.wit.boitcrp.models.managers

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import org.wit.boitcrp.models.Run

interface Manager {
    fun findAll(runsList:
                MutableLiveData<List<Run>>)

    fun findAll(userid:String,
                runsList:
                MutableLiveData<List<Run>>)

    fun findById(userid: String,
                 runid: String,
                 run: MutableLiveData<Run>)

    fun create(firebaseUser: MutableLiveData<FirebaseUser>, run: Run)

    fun update(userid:String, runid: String,run: Run)

    fun delete(userid:String, runid: String)
}