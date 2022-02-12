package org.wit.boitcrp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.boitcrp.databinding.CardItemBinding
import org.wit.boitcrp.models.Item
import android.content.res.Resources
import org.wit.boitcrp.fragments.ItemSelectFragment

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
            binding.itemName.text = item.itemName

            val imageString = item.pickUps?.get(0)?.pickUpIcon?.split(".")
            val image = imageString?.get(0)
            val resID: Int = binding.root.context.resIdByName(image, "drawable")
            binding.pickupImage1.setImageResource(resID)

            val imageString1 = item.pickUps?.get(1)?.pickUpIcon?.split(".")
            val image1 = imageString1?.get(0)
            val resID1: Int = binding.root.context.resIdByName(image1, "drawable")
            binding.pickupImage2.setImageResource(resID1)

            val imageString2 = item.pickUps?.get(2)?.pickUpIcon?.split(".")
            val image2 = imageString2?.get(0)
            val resID2: Int = binding.root.context.resIdByName(image2, "drawable")
            binding.pickupImage3.setImageResource(resID2)

            val imageString3 = item.pickUps?.get(3)?.pickUpIcon?.split(".")
            val image3 = imageString3?.get(0)
            val resID3: Int = binding.root.context.resIdByName(image3, "drawable")
            binding.pickupImage4.setImageResource(resID3)

            val imageString4 = item.pickUps?.get(4)?.pickUpIcon?.split(".")
            val image4 = imageString4?.get(0)
            val resID4: Int = binding.root.context.resIdByName(image4, "drawable")
            binding.pickupImage5.setImageResource(resID4)

            val imageString5 = item.pickUps?.get(5)?.pickUpIcon?.split(".")
            val image5 = imageString5?.get(0)
            val resID5: Int = binding.root.context.resIdByName(image5, "drawable")
            binding.pickupImage6.setImageResource(resID5)

            val imageString6 = item.pickUps?.get(6)?.pickUpIcon?.split(".")
            val image6 = imageString6?.get(0)
            val resID6: Int = binding.root.context.resIdByName(image6, "drawable")
            binding.pickupImage7.setImageResource(resID6)

            val imageString7 = item.pickUps?.get(7)?.pickUpIcon?.split(".")
            val image7 = imageString7?.get(0)
            val resID7: Int = binding.root.context.resIdByName(image7, "drawable")
            binding.pickupImage8.setImageResource(resID7)


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