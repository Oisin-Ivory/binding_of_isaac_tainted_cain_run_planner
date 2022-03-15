package org.wit.boitcrp.ui.run

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.ItemAdapterForRun
import org.wit.boitcrp.databinding.FragmentRunBinding
import org.wit.boitcrp.ui.itemlist.ItemListFragment
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run

class RunFragment : Fragment() {


    private lateinit var binding: FragmentRunBinding

    var run = Run()

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

        val bundle = arguments
        run = bundle?.getParcelable("run_to_use")!!
        activity?.title = run.runName
        binding.runName.text = run.runName
        binding.seedName.text = run.seed

        loadRuns()
        return root
    }


    private fun loadRuns() {
        showItems(run.FindAllItems())
    }

    fun showItems (items: List<Item>) {
        binding.recyclerView.adapter = ItemAdapterForRun(items, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
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
            app.runs.delete(run)
            val action = RunFragmentDirections.actionRunFragmentToRunListFragment()
            findNavController().navigate(action)
            return super.onOptionsItemSelected(mitem)
        }
        if(mitem.itemId == R.id.edit_run) {
            val action = RunFragmentDirections.actionRunFragmentToRunAddFragment(run)
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