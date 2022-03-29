package org.wit.boitcrp.ui.item

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import org.wit.boitcrp.R
import org.wit.boitcrp.databinding.FragmentItemBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.managers.ItemManager
import org.wit.boitcrp.ui.itemlist.ItemListFragment

class ItemFragment : Fragment() {


    private lateinit var binding: FragmentItemBinding
    private lateinit var itemFragmentViewModel: ItemFragmentViewModel
    private val args by navArgs<ItemFragmentArgs>()

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

        itemFragmentViewModel = ViewModelProvider(this).get(ItemFragmentViewModel::class.java)
        itemFragmentViewModel.observableItem.observe(viewLifecycleOwner, Observer { render() })

        return root
    }


    fun render(){
        binding.item = itemFragmentViewModel
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

        return NavigationUI.onNavDestinationSelected(mitem,
            requireView().findNavController()) || super.onOptionsItemSelected(mitem)
    }

    override fun onResume() {
        super.onResume()
        itemFragmentViewModel.setItem(args.itemToUse)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}