package org.wit.boitcrp.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import org.wit.boitcrp.R
import org.wit.boitcrp.databinding.FragmentItemBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item

class ItemFragment : Fragment() {


    private lateinit var binding: FragmentItemBinding

    var item = Item()

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        super.onCreate(savedInstanceState)
        binding = FragmentItemBinding.inflate(inflater, container, false)
        val root = binding.root

        val bundle = arguments
        item = bundle?.getParcelable("item_to_use")!!
        activity?.title = item.itemName

        displayItemPickups()
        return root
    }

    fun displayItemPickups(){
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

    }

    fun Context.resIdByName(resIdName: String?, resType: String): Int {
        resIdName?.let {
            return resources.getIdentifier(it, resType, packageName)
        }
        throw Resources.NotFoundException()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)

        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(mitem: MenuItem): Boolean {

        print("--------------------------------------------------\n"+item)
        if(mitem.itemId == R.id.delete_item) {
            app.items.delete(item)
            val action = ItemFragmentDirections.actionItemFragmentToItemListFragment()
            findNavController().navigate(action)
            return super.onOptionsItemSelected(mitem)
        }
        if(mitem.itemId == R.id.edit_item) {
            val action = ItemFragmentDirections.actionItemFragmentToItemAddFragment(item)
            findNavController().navigate(action)
            return super.onOptionsItemSelected(mitem)
        }

        return NavigationUI.onNavDestinationSelected(mitem,
            requireView().findNavController()) || super.onOptionsItemSelected(mitem)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}