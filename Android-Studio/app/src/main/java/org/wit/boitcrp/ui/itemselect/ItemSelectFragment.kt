package org.wit.boitcrp.ui.itemselect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.adapters.ItemAdapterSelection
import org.wit.boitcrp.databinding.FragmentItemSelectBinding
import org.wit.boitcrp.ui.itemlist.ItemListFragment
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.managers.ItemManager
import org.wit.boitcrp.ui.item.ItemFragmentViewModel
import org.wit.boitcrp.ui.itemlist.ItemListViewModel
import org.wit.boitcrp.ui.runadd.RunAddFragmentArgs


class ItemSelectFragment : Fragment() {
    private var remove: Boolean = true
    private lateinit var binding: FragmentItemSelectBinding
    private lateinit var itemSelectViewModel: ItemSelectFragmentViewModel
    private val args by navArgs<ItemSelectFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemSelectBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        itemSelectViewModel = ViewModelProvider(this).get(ItemSelectFragmentViewModel::class.java)

        if(args.itemList == null){
            itemSelectViewModel.setItems(ItemManager.findAll())
            remove = false
        }else{
            itemSelectViewModel.setItems(args.itemList!!.toList())
            remove = true
        }

        itemSelectViewModel.observableItemList.observe(viewLifecycleOwner, Observer { items ->
            items?.let { render(items) }
        })
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
        if(remove)
            itemSelectViewModel.setItems(ItemManager.searchItems(terms,args.itemList!!.toMutableList()))
        else
            itemSelectViewModel.setItems(ItemManager.searchItems(terms))
    }

    fun searchItems(searchTerms : List<String>):List<Item>{
        val returnList = emptyList<Item>().toMutableList()

        for (item in itemSelectViewModel.observableItemList.value!!){
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

    private fun render(itemList: List<Item>) {
        binding.recyclerView.adapter = ItemAdapterSelection(itemList, this)
        if (itemList.isEmpty()) {
            println("Rendering empty list")
            binding.recyclerView.visibility = View.GONE
            //binding.NoItemsImage.visibility = View.VISIBLE
            //binding.NoItemsText.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            //binding.NoItemsImage.visibility = View.GONE
            //binding.NoItemsText.visibility = View.GONE
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }


}