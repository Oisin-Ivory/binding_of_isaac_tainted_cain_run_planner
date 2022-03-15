package org.wit.boitcrp.ui.itemadd

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import org.wit.boitcrp.R
import org.wit.boitcrp.databinding.FragmentItemAddBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.ui.itemlist.ItemListFragment

class ItemAddFragment : Fragment() {


    private lateinit var binding: FragmentItemAddBinding
    private lateinit var itemAddViewModel: ItemAddViewModel

    //var item = Item()
    //var edit = false
    lateinit var appRoot: View
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        super.onCreate(savedInstanceState)
        binding = FragmentItemAddBinding.inflate(inflater, container, false)
        val root = binding.root

        itemAddViewModel = ViewModelProvider(this).get(ItemAddViewModel::class.java)

        itemAddViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

//        val bundle = arguments
//        if(bundle?.getParcelable<Item>("item_to_edit") == null){
//            edit = false
//            activity?.title = "New Item"
//        }else{
//            println("-----------------------------------------\n"+savedInstanceState?.getParcelable<Item>("item_to_edit"));
//            edit = true
//            item = bundle?.getParcelable<Item>("item_to_edit")!!
//            activity?.title = item.itemName
//            binding.itemName.setText(item.itemName)
//            binding.btnAdd.setText(R.string.button_updatePrompt)
//        }

        appRoot = root
        initSpinners(false)
        setupFinishButton()
        return root
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.itemError), Toast.LENGTH_LONG).show()
        }
    }

    fun setupFinishButton(){
        binding.btnAdd.setOnClickListener() {
            var item = Item()
            item.itemName = binding.itemName.text.toString()
            item.pickUps?.set(0,
                app.pickups.findPickupName(binding.spinnerpickup1.getSelectedItem().toString())!!
            )
            item.pickUps?.set(1,
                app.pickups.findPickupName(binding.spinnerpickup2.getSelectedItem().toString())!!
            )
            item.pickUps?.set(2,
                app.pickups.findPickupName(binding.spinnerpickup3.getSelectedItem().toString())!!
            )
            item.pickUps?.set(3,
                app.pickups.findPickupName(binding.spinnerpickup4.getSelectedItem().toString())!!
            )
            item.pickUps?.set(4,
                app.pickups.findPickupName(binding.spinnerpickup5.getSelectedItem().toString())!!
            )
            item.pickUps?.set(5,
                app.pickups.findPickupName(binding.spinnerpickup6.getSelectedItem().toString())!!
            )
            item.pickUps?.set(6,
                app.pickups.findPickupName(binding.spinnerpickup7.getSelectedItem().toString())!!
            )
            item.pickUps?.set(7,
                app.pickups.findPickupName(binding.spinnerpickup8.getSelectedItem().toString())!!
            )

            if (item.itemName.isEmpty()) {
                Snackbar.make(it,R.string.input_namePrompt, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                itemAddViewModel.addItem(item);
            }
        }
    }

    fun initSpinners(edit: Boolean) {
        val spinners: List<Spinner>
        spinners = mutableListOf(
            binding.spinnerpickup1,
            binding.spinnerpickup2,
            binding.spinnerpickup3,
            binding.spinnerpickup4,
            binding.spinnerpickup5,
            binding.spinnerpickup6,
            binding.spinnerpickup7,
            binding.spinnerpickup8
        )

        for (spinner in spinners) {

            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                appRoot.getContext(), android.R.layout.simple_spinner_item, app.pickups.findAllNames()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(adapter)
        }

//        if (edit) {
//            for (i in 0..7) {
//                item.pickUps?.get(i)
//                    ?.let { app.pickups.getPickUpIndex(it) }?.let { spinners[i].setSelection(it) }
//            }
//        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_add, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}