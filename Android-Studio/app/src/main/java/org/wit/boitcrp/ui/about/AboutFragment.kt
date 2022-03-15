package org.wit.boitcrp.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.boitcrp.databinding.FragmentAboutBinding
import org.wit.boitcrp.main.MainApp
import org.wit.boitcrp.ui.itemlist.ItemListFragment

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

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
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemListFragment()
    }
}