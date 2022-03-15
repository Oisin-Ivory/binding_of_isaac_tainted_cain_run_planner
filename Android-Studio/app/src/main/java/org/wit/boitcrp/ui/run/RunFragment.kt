package org.wit.boitcrp.ui.run

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.ItemAdapterForRun
import org.wit.boitcrp.adapters.RunAdapter
import org.wit.boitcrp.databinding.FragmentRunBinding
import org.wit.boitcrp.ui.itemlist.ItemListFragment
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.models.managers.RunManager
import org.wit.boitcrp.ui.item.ItemFragmentArgs
import org.wit.boitcrp.ui.item.ItemFragmentViewModel

class RunFragment : Fragment() {


    private lateinit var binding: FragmentRunBinding
    private lateinit var runFragmentViewModel: RunFragmentViewModel
    private val args by navArgs<RunFragmentArgs>()

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
        binding = FragmentRunBinding.inflate(inflater, container, false)
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        val root = binding.root

        runFragmentViewModel = ViewModelProvider(this).get(RunFragmentViewModel::class.java)
        runFragmentViewModel.observableRun.observe(viewLifecycleOwner, Observer { render() })

        return root
    }

    fun render(){
        binding.run = runFragmentViewModel
    }

    fun onItemClick(item: Item) {
        val action = RunFragmentDirections.actionRunFragmentToItemFragment(item)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_run, menu)

        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(mitem: MenuItem): Boolean {

        if(mitem.itemId == R.id.delete_run) {
            RunManager.delete(runFragmentViewModel.observableRun.value!!)
            val action = RunFragmentDirections.actionRunFragmentToRunListFragment()
            findNavController().navigate(action)
            return super.onOptionsItemSelected(mitem)
        }
        if(mitem.itemId == R.id.edit_run) {
            val action = RunFragmentDirections.actionRunFragmentToRunAddFragment(runFragmentViewModel.observableRun.value!!)
            findNavController().navigate(action)
            return super.onOptionsItemSelected(mitem)
        }

        return NavigationUI.onNavDestinationSelected(mitem,
            requireView().findNavController()) || super.onOptionsItemSelected(mitem)
    }

    override fun onResume() {
        super.onResume()
        runFragmentViewModel.setRun(args.runToUse)
        binding.recyclerView.adapter = ItemAdapterForRun(runFragmentViewModel.observableRun.value?.runItems!!, this)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}