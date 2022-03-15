package org.wit.boitcrp.ui.runadd

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.ItemAdapterForAddRun
import org.wit.boitcrp.databinding.FragmentRunAddBinding
import org.wit.boitcrp.ui.itemlist.ItemListFragment
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.models.managers.RunManager
import org.wit.boitcrp.ui.itemadd.ItemAddViewModel

class RunAddFragment : Fragment() {
    private lateinit var binding: FragmentRunAddBinding

    private lateinit var runAddViewModel: RunAddFragmentViewModel
    var run = Run()
//    var edit = false
    lateinit var appRoot: View
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        setFragmentResultListener("item_to_add") { _, bundle ->
            val content = bundle.getParcelable<Item>("item")
            if (content != null) {
                run.addItem(content)
            }
        }

        setFragmentResultListener("item_to_remove") { _, bundle ->
            val content = bundle.getParcelable<Item>("item")
            if (content != null) {
                run.removeItem(content.id)
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        super.onCreate(savedInstanceState)
        binding = FragmentRunAddBinding.inflate(inflater, container, false)
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        val root = binding.root

        runAddViewModel = ViewModelProvider(this).get(RunAddFragmentViewModel::class.java)

        runAddViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

//        val bundle = arguments
//        if(bundle?.getParcelable<Run>("run_to_edit") == null){
//            edit = false
//            activity?.title = "New Run"
//        }else{
//            edit = true
//            run = bundle?.getParcelable<Run>("run_to_edit")!!
//            activity?.title = run.runName
//            binding.runSeed.setText(run.seed)
//            binding.runName.setText(run.runName)
//            binding.btnAdd.setText(R.string.button_updatePrompt)
//
//        }

        run.runItems?.let { showItems(it) }
        appRoot = root
        setupAddRemoveButtons()
        setupFinishButton()
        return root
    }

    fun showItems (items: List<Item>) {
        binding.recyclerView.adapter = ItemAdapterForAddRun(items, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.itemError), Toast.LENGTH_LONG).show()
        }
    }

    fun setupFinishButton(){
        binding.btnAdd.setOnClickListener() {
            run.runName = binding.runName.text.toString()
            run.seed = binding.runSeed.text.toString()
            if (run.runName!!.isEmpty()) {
                Snackbar.make(it, R.string.input_namePrompt, Snackbar.LENGTH_LONG)
                    .show()
            } else {
//                if (edit) {
//                    RunManager.update(run.copy())
//                } else {
//                    RunManager.create(run.copy())
//
//                }
            }
            val action = RunAddFragmentDirections.actionRunAddFragmentToRunListFragment()
            findNavController().navigate(action)
        }
    }

    fun setupAddRemoveButtons(){
        binding.btnAddItem.setOnClickListener(){
            val action = RunAddFragmentDirections.actionRunAddFragmentToItemSelectFragment(null)
            findNavController().navigate(action)
        }

        binding.btnRemoveItem.setOnClickListener(){

            val action = RunAddFragmentDirections.actionRunAddFragmentToItemSelectFragment(run.runItems!!.toTypedArray())
            findNavController().navigate(action)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_run_add, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    fun onItemClick(item: Item) {

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}