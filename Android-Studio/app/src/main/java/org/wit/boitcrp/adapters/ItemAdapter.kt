package org.wit.boitcrp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.boitcrp.databinding.CardItemBinding
import org.wit.boitcrp.models.Item
import android.content.res.Resources
import org.wit.boitcrp.ui.itemlist.ItemListFragment

interface ItemListener {
    fun onItemClick(item: Item)
}

class ItemAdapter(private var items: List<Item>,
                  private val listener: ItemListFragment
) :
    RecyclerView.Adapter<ItemAdapter.MainHolder>() {

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

        fun bind(item: Item, listener: ItemListener) {
            binding.itemName.text = item.itemName

            binding.pickupImage1.setImageResource(item.GetPickUpResString(0))
            binding.pickupImage2.setImageResource(item.GetPickUpResString(1))
            binding.pickupImage3.setImageResource(item.GetPickUpResString(2))
            binding.pickupImage4.setImageResource(item.GetPickUpResString(3))
            binding.pickupImage5.setImageResource(item.GetPickUpResString(4))
            binding.pickupImage6.setImageResource(item.GetPickUpResString(5))
            binding.pickupImage7.setImageResource(item.GetPickUpResString(6))
            binding.pickupImage8.setImageResource(item.GetPickUpResString(7))

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