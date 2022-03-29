package org.wit.boitcrp.ui.mainmenu

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ie.wit.donationx.utils.hideLoader
import ie.wit.donationx.utils.showLoader
import org.wit.boitcrp.databinding.FragmentMainMenuBinding
import org.wit.boitcrp.firebase.FirebaseDBManager
import org.wit.boitcrp.ui.itemlist.ItemListFragment
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Run
import org.wit.boitcrp.models.managers.ItemManager
import org.wit.boitcrp.ui.auth.LoggedInViewModel
import org.wit.boitcrp.ui.runlist.RunListFragmentViewModel

//import org.wit.boitcrp.models.managers.RunManager

class MainMenuFragment : Fragment() {
    lateinit var app: MainApp
    private lateinit var binding: FragmentMainMenuBinding

    private lateinit var mainMenuViewModel: MainMenuFragmentViewModel

    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
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
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val root = binding.root
        activity?.title = "Main Menu"
        mainMenuViewModel = ViewModelProvider(this).get(MainMenuFragmentViewModel::class.java)
        mainMenuViewModel.observableRunList.observe(viewLifecycleOwner, Observer {
                runs ->
            runs?.let { render() }
        })
        return root
    }

    fun render(){
        mainMenuViewModel.load()
        binding.runsCountText.text = mainMenuViewModel.runCount
        binding.itemsCountText.text = mainMenuViewModel.itemCount
    }
//    private fun updateRunItemDisplay(){
////        numItems = ItemManager.findAll().size
////        //numRuns = FirebaseDBManager.findAll().size
////        binding.itemsCountText.text = numItems.toString()
//
//        binding.runsCountText.text = mainMenuViewModel.observableRunList.value?.size.toString()
//        binding.runsCountText.text = mainMenuViewModel.observableRunList.value?.size.toString()
//    }

    override fun onResume() {
        super.onResume()
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                mainMenuViewModel.liveFirebaseUser.value = firebaseUser
                mainMenuViewModel.load()
            }
        })
    }


    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }*/


    companion object {
        @JvmStatic
        fun newInstance() =
            ItemListFragment()
    }
}