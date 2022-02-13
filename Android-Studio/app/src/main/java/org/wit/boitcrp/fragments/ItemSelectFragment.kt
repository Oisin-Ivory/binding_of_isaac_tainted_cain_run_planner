package org.wit.boitcrp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.ItemAdapterSelection
import org.wit.boitcrp.databinding.FragmentItemSelectBinding
import org.wit.boitcrp.databinding.FragmentRunListBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run


class ItemSelectFragment : Fragment() {
    lateinit var app: MainApp
    private lateinit var binding: FragmentItemSelectBinding
    private lateinit var items: List<Item>
    private var remove: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val bundle = arguments
        app = activity?.application as MainApp
       // println("---------------------------------\nChecking--------------------------------")
       // println(bundle?.getParcelableArray("item_list"))
        if(bundle?.getParcelableArray("item_list") == null){
            //println("---------------------------------\nNo Items Passed--------------------------------")
            items = app.items.findAll()
            remove = false
        }else{
            //println("---------------------------------\nItems Passed---------------------------------")
            items = bundle.getParcelableArray("item_list")!!.toList() as List<Item>
            remove = true
        }

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
            binding.recyclerView.adapter = ItemAdapterSelection(app.items.findAll(), this)


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




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }


}