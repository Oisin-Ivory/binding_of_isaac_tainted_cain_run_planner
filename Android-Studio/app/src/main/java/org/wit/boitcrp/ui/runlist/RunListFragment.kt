package org.wit.boitcrp.ui.runlist

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
import org.wit.boitcrp.adapters.RunAdapter
import org.wit.boitcrp.adapters.RunListener
import org.wit.boitcrp.databinding.FragmentRunListBinding
import org.wit.boitcrp.ui.itemlist.ItemListFragment
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item
import org.wit.boitcrp.models.Run
//import org.wit.boitcrp.models.managers.RunManager
import org.wit.boitcrp.ui.itemlist.ItemListViewModel

class RunListFragment : Fragment(), RunListener {
    lateinit var app: MainApp
    private lateinit var binding: FragmentRunListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var runListViewModel:RunListFragmentViewModel
    //private lateinit var displayRuns: List<Run>

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
        binding = FragmentRunListBinding.inflate(inflater, container, false)
        val root = binding.root
        activity?.title = "Runs"

        //displayRuns = app.runs.findAll()
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))

        runListViewModel = ViewModelProvider(this).get(RunListFragmentViewModel::class.java)
        runListViewModel.observableRunList.observe(viewLifecycleOwner, Observer {
                runs ->
            runs?.let { render(runs) }
        })
        //loadRuns()
        //registerRefreshCallback()

        binding.searchbtn.setOnClickListener(){
            //searchRuns()
        }
        return root
    }

//    fun searchRuns(){
//        val terms = binding.searchField.text.split(",")
//        val displayItems = app.runs.searchRuns(terms)
//        showRuns(displayItems)
//    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_run_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


//    override fun onOptionsItemSelected(m_item: MenuItem): Boolean {
//        when (m_item.itemId) {
//            R.id.runAddFragment -> {
//                val action = RunListFragmentDirections.actionRunListFragmentToRunAddFragment(null)
//                findNavController().navigate(action)
//            }
//
//            R.id.mainMenuFragment -> {
//                val action = RunListFragmentDirections.actionRunListFragmentToMainMenuFragment()
//                findNavController().navigate(action)
//            }
//        }
//        return super.onOptionsItemSelected(m_item)
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println("------------------------------------------------------------")
        println(item.itemId)
        println( requireView().findNavController())
        println("------------------------------------------------------------")


        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onRunClick(run: Run) {
        val action = RunListFragmentDirections.actionRunListFragmentToRunFragment(run)
        findNavController().navigate(action)
    }

//    private fun registerRefreshCallback() {
//        refreshIntentLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//            { showRuns(app.runs.findAll()) }
//    }
//
//    private fun loadRuns() {
//        showRuns(app.runs.findAll())
//    }
//
//    fun showRuns (runs: List<Run>) {
//        binding.recyclerView.adapter = RunAdapter(runs, this)
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//    }

    private fun render(runList: List<Run>){
        binding.recyclerView.adapter = RunAdapter(runList, this)
        if(runList.isEmpty()){
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}