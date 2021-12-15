package org.wit.boitcrp.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.boitcrp.models.Run


interface RunListener {
    fun onRunClick(run: Run)
}

class RunAdapter constructor(private var runs: List<Run>,
                             private val listener: RunListener) :
    RecyclerView.Adapter<RunAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRunBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val run = runs[holder.adapterPosition]
        holder.bind(run, listener)
    }

    override fun getItemCount(): Int = runs.size

    class MainHolder(private val binding : CardRunBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(run: Run, listener: ItemListener) {
            binding.runName.text = item.itemName
            binding.root.setOnClickListener { listener.onItemClick(item) }
        }

        fun Context.resIdByName(resIdName: String?, resType: String): Int {
            resIdName?.let {
                return resources.getIdentifier(it, resType, packageName)
            }
            throw Resources.NotFoundException()
        }
    }
}