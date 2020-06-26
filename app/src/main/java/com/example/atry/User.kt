package com.example.atry

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.databinding.FragmentUserBinding

class User : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentUserBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        binding.userViewModel = viewModel
        binding.setLifecycleOwner(this) // Allows to use LiveData to automatically update DataBinding layouts

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                Log.d("TAG: ", "captura onNextButtonClicked")
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })
        return binding.root
    }

    private fun navigationClicked () {
        Log.d("TAG: ", "Ingresa a navigationClicked")
        view?.findNavController()?.navigate(R.id.action_user_to_ingredients)
    }
}
