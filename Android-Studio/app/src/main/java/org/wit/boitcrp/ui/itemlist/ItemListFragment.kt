package org.wit.boitcrp.ui.itemlist

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
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.ItemAdapter
import org.wit.boitcrp.adapters.ItemListener
import org.wit.boitcrp.databinding.FragmentItemListBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item

class ItemListFragment : Fragment(), ItemListener {
    lateinit var app: MainApp
    private lateinit var binding: FragmentItemListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var itemListViewModel: ItemListViewModel
    //private lateinit var displayItems: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        val root = binding.root
        activity?.title = "Items"

            //displayItems = app.items.findAll()
            //binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
            //binding.recyclerView.adapter = ItemAdapter(app.items.findAll(), this)
            //loadItems()
            //registerRefreshCallback()
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        itemListViewModel = ViewModelProvider(this).get(ItemListViewModel::class.java)
        itemListViewModel.observableItemList.observe(viewLifecycleOwner, Observer {
                items ->
            items?.let { render(items) }
        })

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
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(itemList: List<Item>){
        binding.recyclerView.adapter = ItemAdapter(itemList, this)
        if(itemList.isEmpty()){
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

//    private fun registerRefreshCallback() {
//        refreshIntentLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//            { showItems(app.items.findAll()) }
//    }

//    private fun loadItems() {
//        showItems(displayItems)
//    }

//    fun showItems (items: List<Item>) {
//        binding.recyclerView.adapter = ItemAdapter(items, this)
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//    }
    override fun onResume() {
    super.onResume()
    itemListViewModel.load()
}

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}