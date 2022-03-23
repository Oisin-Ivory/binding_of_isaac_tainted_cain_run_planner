package org.wit.boitcrp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.boitcrp.databinding.CardItemBinding
import org.wit.boitcrp.models.Item
import android.content.res.Resources
import org.wit.boitcrp.ui.itemselect.ItemSelectFragment

interface ItemListenerSelection {
    fun onItemClick(item: Item)
}

class ItemAdapterSelection(private var items: List<Item>,
                           private val listener: ItemSelectFragment
) :
    RecyclerView.Adapter<ItemAdapterSelection.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = items[holder.adapterPosition]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int = items.size

    class MainHolder(private val binding : CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, listener: ItemSelectFragment) {
            binding.item = item



            binding.root.setOnClickListener { listener.onItemClick(item) }
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