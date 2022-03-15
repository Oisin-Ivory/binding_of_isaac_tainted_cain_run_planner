package org.wit.boitcrp.ui.itemselect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.adapters.ItemAdapterSelection
import org.wit.boitcrp.databinding.FragmentItemSelectBinding
import org.wit.boitcrp.ui.itemlist.ItemListFragment
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.managers.ItemManager


class ItemSelectFragment : Fragment() {
    lateinit var app: MainApp
    private lateinit var binding: FragmentItemSelectBinding
    private lateinit var items: List<Item>
    private var remove: Boolean = true
    private lateinit var displayItems: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val bundle = arguments
        app = activity?.application as MainApp
       // println("---------------------------------\nChecking--------------------------------")
       // println(bundle?.getParcelableArray("item_list"))
        if(bundle?.getParcelableArray("item_list") == null){
            //println("---------------------------------\nNo Items Passed--------------------------------")
            items = ItemManager.findAll()
            remove = false
        }else{
            //println("---------------------------------\nItems Passed---------------------------------")
            items = bundle.getParcelableArray("item_list")!!.toList() as List<Item>
            remove = true
        }
        displayItems = items
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemSelectBinding.inflate(inflater, container, false)
        val root = binding.root

        app = activity?.application as MainApp
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        if(remove)
            binding.recyclerView.adapter = ItemAdapterSelection(items, this)
        else
            //binding.recyclerView.adapter = ItemAdapterSelection(app.items.findAll(), this)

        setButtonListener(binding)
        return root
    }

    fun onItemClick(item: Item) {

        val result = item
        if(remove){
            setFragmentResult("item_to_remove", bundleOf("item" to result))
        }else{
            setFragmentResult("item_to_add", bundleOf("item" to result))
        }
        getActivity()?.onBackPressed();
    }

    fun setButtonListener(layout: FragmentItemSelectBinding) {
        layout.searchbtn.setOnClickListener {
            searchTerms()
        }
    }

    fun searchTerms(){
        val terms = binding.searchField.text.split(",")
        displayItems = searchItems(terms)
        loadItems()
    }

    fun searchItems(searchTerms : List<String>):List<Item>{
        val returnList = emptyList<Item>().toMutableList()

        for (item in items){
            var addToList = true;
            for(term in searchTerms){
                if(!item.toString().lowercase().contains(term.lowercase())){
                    addToList = false
                }
            }
            if(addToList) returnList.add(item)
        }
        return returnList
    }

    private fun loadItems() {
        showItems(displayItems)
    }

    fun showItems (items: List<Item>) {
        binding.recyclerView.adapter = ItemAdapterSelection(items, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }


}