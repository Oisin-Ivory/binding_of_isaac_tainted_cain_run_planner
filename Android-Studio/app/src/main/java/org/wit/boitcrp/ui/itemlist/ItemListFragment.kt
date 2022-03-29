package org.wit.boitcrp.ui.itemlist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.ItemAdapter
import org.wit.boitcrp.adapters.ItemListener
import org.wit.boitcrp.databinding.FragmentItemListBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import ie.wit.donationx.utils.SwipeToDeleteCallback
import ie.wit.donationx.utils.SwipeToEditCallback
import ie.wit.donationx.utils.hideLoader
import ie.wit.donationx.utils.showLoader
import org.wit.boitcrp.adapters.RunAdapter
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.ui.runlist.RunListFragmentDirections

class ItemListFragment : Fragment(), ItemListener {
    private lateinit var binding: FragmentItemListBinding
    private lateinit var itemListViewModel: ItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        itemListViewModel = ViewModelProvider(this).get(ItemListViewModel::class.java)
        binding = FragmentItemListBinding.inflate(inflater, container, false)

        val root = binding.root
        activity?.title = "Items"

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        itemListViewModel.observableItemList.observe(viewLifecycleOwner, Observer { items ->
            items?.let { render(items) }
        })

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerView.adapter as ItemAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                itemListViewModel.delete(
                    (viewHolder.itemView.tag as Item).id!!
                )
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(binding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val action = ItemListFragmentDirections.actionItemListFragmentToItemAddFragment(viewHolder.itemView.tag as Item)
                findNavController().navigate(action)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(binding.recyclerView)


        setButtonListener(binding!!)
        return root
    }

    fun setButtonListener(layout: FragmentItemListBinding) {
        layout.searchbtn.setOnClickListener {
            //searchTerms()
        }
    }

//    fun searchTerms(){
//        val terms = binding.searchField.text.split(",")
//        displayItems = app.items.searchItems(terms)
//        loadItems()
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_list, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println("_______________________________________________________________________")
        for (item in itemListViewModel.observableItemList.value!!){
            println(item.itemName)
        }
        println("_______________________________________________________________________")
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun render(itemList: List<Item>) {
        binding.recyclerView.adapter = ItemAdapter(itemList, this)
        if (itemList.isEmpty()) {
            println("Rendering empty list")
            binding.recyclerView.visibility = View.GONE
            binding.NoItemsImage.visibility = View.VISIBLE
            binding.NoItemsText.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.NoItemsImage.visibility = View.GONE
            binding.NoItemsText.visibility = View.GONE
        }
    }


    override fun onItemClick(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemFragment(item)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        itemListViewModel.load()
    }
}