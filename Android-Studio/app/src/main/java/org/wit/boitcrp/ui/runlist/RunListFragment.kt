package org.wit.boitcrp.ui.runlist

//import org.wit.boitcrp.models.managers.RunManager

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.donationx.utils.*
import org.wit.boitcrp.R
import org.wit.boitcrp.adapters.RunAdapter
import org.wit.boitcrp.adapters.RunListener
import org.wit.boitcrp.databinding.FragmentRunListBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.ui.auth.LoggedInViewModel
import org.wit.boitcrp.ui.itemlist.ItemListFragment


class RunListFragment : Fragment(), RunListener {
    lateinit var app: MainApp
    private lateinit var binding: FragmentRunListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var runListViewModel:RunListFragmentViewModel
    lateinit var loader : AlertDialog
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    val paths = arrayOf("My Runs","All Runs", "Favourites")
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
        loader = createLoader(requireActivity())

        runListViewModel = ViewModelProvider(this).get(RunListFragmentViewModel::class.java)
        runListViewModel.loadAll()

        //displayRuns = app.runs.findAll()
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))

        runListViewModel.observableRunList.observe(viewLifecycleOwner, Observer {
                runs ->
            runs?.let { render(runs)
            hideLoader(loader)
            checkSwipeRefresh()
            }
        })

        val spinner = binding.displayRunType

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            app.appContext, R.layout.spinner_style, paths
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(spinner.selectedItem == "All Runs"){
                    runListViewModel.loadAll()
                }else if (spinner.selectedItem == "My Runs"){
                    runListViewModel.load()
                }else{
                    runListViewModel.loadFavourites()
                }
            }

        }

        //loadRuns()
        //registerRefreshCallback()

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Run")
                val adapter = binding.recyclerView.adapter as RunAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                println(runListViewModel.liveFirebaseUser.value)
                runListViewModel.delete(
                    runListViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as Run).uid!!
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(binding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val action = RunListFragmentDirections.actionRunListFragmentToRunAddFragment(viewHolder.itemView.tag as Run)
                findNavController().navigate(action)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(binding.recyclerView)


        binding.searchbtn.setOnClickListener{
            searchRuns()
        }
        return root
    }

    fun searchRuns(){
        val terms = binding.searchField.text.split(",")
        runListViewModel.setRuns(searchRuns(terms));
    }

    fun searchRuns(searchTerms : List<String>):List<Run>{

        val runs = runListViewModel.observableRunList.value

        val returnList = emptyList<Run>().toMutableList()

        for (run in runs!!){
            var addToList = true;
            for(term in searchTerms){
                if(!run.toString().lowercase().contains(term.lowercase())){
                    addToList = false
                }
            }
            if(addToList) returnList.add(run)
        }
        return returnList
    }
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

    override fun onRunFav(run: Run) {
        runListViewModel.favourite(run)
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
        if(runListViewModel.liveFirebaseUser.value==null){
            return
        }
        println("____________runList______________\n"+runList)
        println(runListViewModel.readOnly.value)
        println(runListViewModel.liveFirebaseUser.value!!.uid)
        binding.recyclerView.adapter = RunAdapter(runList, this,runListViewModel.readOnly.value!!,runListViewModel.liveFirebaseUser.value!!.uid)
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

    private fun setSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Runs")
            if(runListViewModel.readOnly.value!!)
                runListViewModel.loadAll()
            else
                runListViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing)
            binding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Runs")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                runListViewModel.liveFirebaseUser.value = firebaseUser
                runListViewModel.load()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}