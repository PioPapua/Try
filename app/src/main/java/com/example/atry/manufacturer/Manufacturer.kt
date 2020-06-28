package com.example.atry.manufacturer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.R
import com.example.atry.databinding.FragmentManufacturerBinding

class Manufacturer : Fragment() {
    private lateinit var viewModel: ManufacturerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentManufacturerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_manufacturer,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(ManufacturerViewModel::class.java)
        binding.manufacturerViewModel = viewModel
        binding.setLifecycleOwner(this) // Allows to use LiveData to automatically update DataBinding layouts

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })
        return binding.root
    }

    private fun navigationClicked () {
        view?.findNavController()?.navigate(R.id.action_manufacturer_to_user)
    }
}
