package org.wit.boitcrp.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.models.managers.Manager
import java.util.concurrent.CountDownLatch


object FirebaseDBManager : Manager {

        var database: DatabaseReference = FirebaseDatabase.getInstance("https://boitcrp-default-rtdb.europe-west1.firebasedatabase.app/").reference

        override fun findAll(runsList: MutableLiveData<List<Run>>) {
                database.child("runs")
                        .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                        val localList = ArrayList<Run>()
                                        val children = snapshot.children
                                        children.forEach {
                                                val run = it.getValue(Run::class.java)
                                                localList.add(run!!)
                                        }
                                        database.child("runs")
                                                .removeEventListener(this)

                                        runsList.value = localList
                                }
                        })
        }

        override fun findAll(userid: String, runsList: MutableLiveData<List<Run>>) {

                database.child("user-runs").child(userid)
                        .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                        val localList = ArrayList<Run>()
                                        val children = snapshot.children
                                        children.forEach {
                                                val run = it.getValue(Run::class.java)
                                                localList.add(run!!)
                                        }
                                        database.child("user-runs").child(userid)
                                                .removeEventListener(this)

                                        runsList.value = localList
                                }
                        })
        }

        override fun findFavourites(userid: String, runsList: MutableLiveData<List<Run>>) {
                database.child("user-fav").child(userid)
                        .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                        val localList = ArrayList<Run>()
                                        val children = snapshot.children
                                        children.forEach {
                                                val run = it.getValue(Run::class.java)
                                                localList.add(run!!)
                                        }
                                        database.child("user-fav").child(userid)
                                                .removeEventListener(this)

                                        runsList.value = localList
                                }
                        })
        }

        override fun findById(userid: String, runid: String, run: MutableLiveData<Run>) {
                database.child("user-runs").child(userid)
                .child(runid).get().addOnSuccessListener {
                        run.value = it.getValue(Run::class.java)
                        println("firebase Got value ${it.value}")
                }.addOnFailureListener{
                        println("firebase Error getting data $it")
                }
        }

        override fun create(firebaseUser: MutableLiveData<FirebaseUser>, run: Run) {
                println("Firebase DB Reference : $database")
                val uid = firebaseUser.value!!.uid
                val key = database.child("runs").push().key
                println(key)
                if (key == null) {
                        println("Firebase Error : Key Empty")
                        return
                }
                run.uid = key
                val runValues = run.toMap()

                val childAdd = HashMap<String, Any>()
                childAdd["/runs/$key"] = runValues
                childAdd["/user-runs/$uid/$key"] = runValues
                println(childAdd)
//                database.child("runs").child(key).setValue(run).addOnCompleteListener{
//                        println("Added run to firebase")
//                }
                database.updateChildren(childAdd)
        }

        override fun delete(userid: String, runid: String) {

                val childDelete : MutableMap<String, Any?> = HashMap()
                childDelete["/runs/$runid"] = null
                childDelete["/user-runs/$userid/$runid"] = null

                database.updateChildren(childDelete)
        }

        override fun update(userid: String, runid: String, run: Run) {

                val runValues = run.toMap()

                val childUpdate : MutableMap<String, Any?> = HashMap()
                childUpdate["runs/$runid"] = runValues
                childUpdate["user-runs/$userid/$runid"] = runValues

                database.updateChildren(childUpdate)
        }

        fun addFavourite(userid: String, run: Run) {
                println("Adding favourite to DB Reference : $database")
                val uid = userid
                val key = database.child("user-fav").push().key
                println(key)
                if (key == null) {
                        println("Firebase Error : Key Empty")
                        return
                }
                val runValues = run.toMap()

                val childFavAdd = HashMap<String, Any>()
                childFavAdd["/user-fav/$uid/${run.uid}"] = runValues
                println(childFavAdd)
//                database.child("runs").child(key).setValue(run).addOnCompleteListener{
//                        println("Added run to firebase")
//                }
                database.updateChildren(childFavAdd)
        }

        fun removeFavourite(userid: String, run: Run) {

                println("Removing favourite from DB Reference : $database")

                val childRemove : MutableMap<String, Any?> = HashMap()
                childRemove["/user-fav/$userid/${run.uid}"] = null

                database.updateChildren(childRemove)
        }

        fun favourite(userid: String, run: Run){
                var isIn = false
                var called = false
                database.child("user-fav").child(userid)
                        .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                        val localList = ArrayList<Run>()
                                        val children = snapshot.children
                                        children.forEach {
                                                val runfound = it.getValue(Run::class.java)
                                                //println(runfound!!.uid+" : "+run.uid)
                                                if (runfound!!.uid.equals(run.uid)) {
                                                        isIn = true
                                                }
                                        }
                                        if(!called){
                                                if (isIn) {
                                                        println("Removing Favourite")
                                                        removeFavourite(userid, run)
                                                } else {
                                                        println("Adding Favourite")
                                                        addFavourite(userid, run)
                                                }
                                                called = true;
                                        }
                                        database.child("user-runs").child(userid)
                                                .removeEventListener(this)

                                }
                        })

        }
}