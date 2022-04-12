package org.wit.boitcrp.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.wit.boitcrp.R
import org.wit.boitcrp.databinding.CardRunBinding
import org.wit.boitcrp.firebase.FirebaseDBManager
import org.wit.boitcrp.ui.runlist.RunListFragment
import org.wit.boitcrp.models.Run


interface RunListener {
    fun onRunClick(run: Run)
    fun onRunFav(run: Run)
}

class RunAdapter(private var runs: List<Run>,
                 private val listener: RunListFragment,
                 private val readOnly: Boolean,
                 private val userid:String
) :
    RecyclerView.Adapter<RunAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRunBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly,userid)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val run = runs[holder.adapterPosition]
        holder.bind(run, listener,userid)
    }

    fun removeAt(position: Int) {
        val mutableRuns = runs.toMutableList()
        mutableRuns.removeAt(position)

        runs = mutableRuns
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = runs.size

    class MainHolder(private val binding : CardRunBinding,readOnly: Boolean,userid:String) :
        RecyclerView.ViewHolder(binding.root) {
        val readOnlyRow = readOnly
        fun bind(run: Run, listener: RunListener,userid:String) {
            binding.root.tag = run
            binding.run = run

            FirebaseDBManager.database.child("user-fav").child(userid).addValueEventListener(object :
                    ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var completed = false;
                        val children = snapshot.children
                        println("searching for favourite")
                        children.forEach {
                            val runfound = it.getValue(Run::class.java)
                            //println(runfound!!.uid+" : "+run.uid)
                            if(!completed) {
                                if (runfound!!.uid.equals(run.uid)) {
                                    binding.favourite.setImageResource(R.drawable.completion_bossrush_hard)
                                    completed = true
                                } else {
                                    binding.favourite.setImageResource(R.drawable.completion_bossrush)

                                }
                            }
                        }
                        FirebaseDBManager.database.child("user-runs").child(userid)
                            .removeEventListener(this)
                    }

                override fun onCancelled(error: DatabaseError) {

                }
            })

//            binding.runName.text = run.runName
//            if(run.runItems?.size!! > 0) {
//                binding.runItem0.text = run.runItems?.get(0)?.itemName
//                if (run.runItems?.size!! > 1) {
//                    binding.runItem1.text = run.runItems?.get(1)?.itemName
//                    if (run.runItems?.size!! > 2) {
//                        binding.runItem2.text = run.runItems?.get(2)?.itemName
//                    }
//                }
//            }

            binding.favourite.setOnClickListener{ listener.onRunFav(run) }
            binding.runName.setOnClickListener { listener.onRunClick(run) }
            binding.executePendingBindings()
        }

        fun Context.resIdByName(resIdName: String?, resType: String): Int {
            resIdName?.let {
                return resources.getIdentifier(it, resType, packageName)
            }
            throw Resources.NotFoundException()
        }
    }


}