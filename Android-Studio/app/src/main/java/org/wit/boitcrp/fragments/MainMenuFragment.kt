package org.wit.boitcrp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.boitcrp.R
import org.wit.boitcrp.activities.ItemActivity
import org.wit.boitcrp.adapters.ItemAdapter
import org.wit.boitcrp.databinding.FragmentItemListBinding
import org.wit.boitcrp.databinding.FragmentMainMenuBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.models.Item

class MainMenuFragment : Fragment() {
    lateinit var app: MainApp
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private var numItems : Int = 0
    private var numRuns : Int = 0
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


        updateRunItemDisplay()
        registerRefreshCallback()

        return root
    }

    private fun updateRunItemDisplay(){
        numItems = app.items.findAll().size
        numRuns = app.runs.findAll().size
        binding.itemsCountText.text = numItems.toString()
        binding.runsCountText.text = numRuns.toString()
    }


    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }*/

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { updateRunItemDisplay() }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ItemListFragment()
    }
}